package com.materip.feature_mypage.view_models.MyPage

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_common.ErrorState
import com.materip.core_common.Result
import com.materip.core_common.asResult
import com.materip.core_model.request.UpdateMyImagesRequestDto
import com.materip.core_model.request.UpdateProfileRequestDto
import com.materip.core_model.ui_model.DefaultImageDto
import com.materip.core_repository.repository.image_repository.ImageRepository
import com.materip.core_repository.repository.profile_repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val imageRepository: ImageRepository
): ViewModel() {
    private val paths = mutableStateListOf<DefaultImageDto>()
    private val invalidTokenError = MutableStateFlow<Boolean>(false)
    private val notFoundTokenError = MutableStateFlow<Boolean>(false)
    private val generalError = MutableStateFlow<Pair<Boolean, String?>>(Pair(false, null))
    val errorState: StateFlow<ErrorState> = combine(invalidTokenError, notFoundTokenError, generalError){ invalidToken, notFoundToken, general ->
        ErrorState.AuthError(
            invalidToken,
            notFoundToken,
            general
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ErrorState.Loading
    )
    val uiState: StateFlow<EditProfileUiState> = errorState.map {
        if (it is ErrorState.AuthError && it.isInvalid()) throw Exception("Error")
        val result = profileRepository.getProfileDetails()
        if (result.error != null) {
            when (result.error!!.status) {
                401 -> notFoundTokenError.update { true }
                404 -> invalidTokenError.update { true }
                else -> generalError.update { Pair(true, result.error!!.message) }
            }
        }
        result.data!!
    }.asResult().map{ result ->
        when(result) {
            Result.Loading -> EditProfileUiState.Loading
            is Result.Success -> {
                val data = result.data
                EditProfileUiState.Success(
                    profileImg = data.profileImageUrl ?: "",
                    nickname = data.nickname,
                    description = data.description,
                    birthYear = data.birthYear,
                    gender = data.gender,
                    travelStyles = data.travelStyles,
                    travelPreferences = data.travelPreferences,
                    foodPreferences = data.foodPreferences,
                    snsLink = data.socialMediaUrl,
                    images = data.userImageUrls
                )
            }
            is Result.Error -> EditProfileUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = EditProfileUiState.Loading
    )

    fun updateProfile(
        newProfileImg: String,
        newNickname: String,
        newDescription: String,
        newBirthYear: Int,
        newGender: String,
        newTravelPreferences: List<String>,
        newTravelStyles: List<String>,
        newFoodPreferences: List<String>,
        newSnsLink: String?
    ){
        val requestDto = UpdateProfileRequestDto(
            profileImageUrl = newProfileImg,
            nickname = newNickname,
            description = newDescription,
            birthYear = newBirthYear,
            gender = newGender,
            travelStyles = newTravelStyles,
            foodPreferences = newFoodPreferences,
            socialMediaLink = newSnsLink ?: "",
            travelPreferences = newTravelPreferences
        )
        viewModelScope.launch{
            val result = profileRepository.updateProfile(requestDto)
            val imageUpdateResult = profileRepository.updateMyImages(UpdateMyImagesRequestDto(imageUrls = paths.map{it.path}))
            if (result.error != null || imageUpdateResult.error != null){
                when (result.error!!.status) {
                    401 -> notFoundTokenError.update { true }
                    404 -> invalidTokenError.update { true }
                    else -> generalError.update { Pair(true, result.error!!.message) }
                }
            }
        }
    }

    fun saveImageToS3(context: Context, path: Uri){
        val file = transformToFile(context, path)
        if (file == null) {
            generalError.update { Pair(true, "파일 경로를 찾을 수 없습ㄴ디ㅏ.") }
            return
        }
        /** S3에 업로드 >> 서버에 저장 메소드 */
        viewModelScope.launch{
            val result = imageRepository.postImage(file)
            if (result.error != null) {
                when(result.error!!.status){
                    401 -> notFoundTokenError.update{true}
                    404 -> invalidTokenError.update{true}
                    else -> generalError.update{Pair(true, result.error!!.message)}
                }
            }
            paths.add(result.data!!)
        }
    }
    fun resetImages(){
        viewModelScope.launch{
            paths.forEach{
                imageRepository.deleteImage(it)
            }
        }
    }
    private fun transformToFile(
        context: Context,
        uri: Uri
    ): File? {
        val contentResolver = context.contentResolver

        val filePath = (context.applicationInfo.dataDir + File.separator + System.currentTimeMillis())
        val file = File(filePath)

        try{
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val outputStream = FileOutputStream(file)

            val buf = ByteArray(1024)
            var len: Int
            while(inputStream.read(buf).also{len = it} > 0) outputStream.write(buf, 0, len)
            outputStream.close()
            inputStream.close()
        } catch (ignore: Exception){
            return null
        }
        return file
    }
}

sealed interface EditProfileUiState{
    data object Loading: EditProfileUiState
    data class Success(
        val profileImg: String,
        val nickname: String,
        val description: String?,
        val birthYear: Int,
        val gender: String,
        val travelStyles: List<String>,
        val travelPreferences: List<String>,
        val foodPreferences: List<String>,
        val snsLink: String?,
        val images: List<String>,
    ): EditProfileUiState
    data object Error: EditProfileUiState
}