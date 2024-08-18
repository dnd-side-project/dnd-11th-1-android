package com.materip.feature_mypage.view_models.MyPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_common.ErrorState
import com.materip.core_common.Result
import com.materip.core_common.asResult
import com.materip.core_model.request.UpdateProfileRequestDto
import com.materip.core_repository.repository.profile_repository.ProfileRepository
import com.materip.core_repository.useCase.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
): ViewModel() {
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
        val result = profileRepository.getProfile()
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
                    profileImg = data.profileImageUrl,
                    nickname = data.nickname,
                    description = data.description,
                    birthYear = data.birthYear,
                    gender = data.gender,
                    travelStyles = data.travelStyles,
                    travelPreferences = data.travelPreferences,
                    foodPreferences = data.foodPreferences,
                    snsLink = data.socialMediaUrl,
                    images = emptyList(), /** 이미지 리스트를 받아와야 함 */
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
        newSnsLink: String?,
        newImages: List<String>
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
            if (result.error != null){
                when (result.error!!.status) {
                    401 -> notFoundTokenError.update { true }
                    404 -> invalidTokenError.update { true }
                    else -> generalError.update { Pair(true, result.error!!.message) }
                }
            }
        }
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