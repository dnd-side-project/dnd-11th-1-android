package com.materip.feature_mypage.view_models.MyPage

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_common.ErrorState
import com.materip.core_common.transformToFile
import com.materip.core_model.request.PostReviewRequestDto
import com.materip.core_model.ui_model.CompanionType
import com.materip.core_model.ui_model.PersonalityType
import com.materip.core_model.ui_model.RecommendationStatus
import com.materip.core_model.ui_model.SatisfactionLevel
import com.materip.core_model.ui_model.TravelPreferenceForReview
import com.materip.core_model.ui_model.TravelStyleForReview
import com.materip.core_repository.repository.image_repository.ImageRepository
import com.materip.core_repository.repository.review_repository.ReviewRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class WriteReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val imageRepository: ImageRepository
): ViewModel() {

    private val id = MutableStateFlow<Int?>(null)
    private val _isDone = MutableStateFlow(false)
    val isDone get() = _isDone.asStateFlow()

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
    fun setId(boardId: Int?){
        if (boardId == null) {
            generalError.update{Pair(true, "해당 글을 찾을 수 없습니다.")}
            return
        }
        id.update{boardId}
    }

    fun writeReview(
        satisfactionLevel: SatisfactionLevel,
        recommendationStatus: RecommendationStatus,
        companionType: CompanionType,
        personalityType: List<PersonalityType>,
        travelPreference: List<TravelPreferenceForReview>,
        travelStyle: List<TravelStyleForReview>,
        detailContent: String,
        imageUrl: List<String>
    ){
        viewModelScope.launch{
            val request = PostReviewRequestDto(
                accompanyBoardId = id.value!!,
                satisfactionLevel = satisfactionLevel.name,
                recommendationStatus = recommendationStatus.name,
                companionType = companionType.name,
                personalityType = personalityType.map{it.name},
                travelPreference = travelPreference.map{it.name},
                travelStyle = travelStyle.map{it.name},
                detailContent = detailContent,
                reviewImageUrls = imageUrl
            )
            val result = reviewRepository.postReview(requestDto = request)
            if (result.error != null){
                when(result.error!!.status){
                    401 -> invalidTokenError.update{true}
                    404 -> notFoundTokenError.update{true}
                    else -> generalError.update{Pair(true, result.error!!.message)}
                }
                return@launch
            }
            _isDone.update{true}
        }
    }

    suspend fun uploadImageToS3(context: Context, uri: Uri?) : String {
        return viewModelScope.async {
            if (uri == null) {
                generalError.update { Pair(true, "파일 경로를 찾을 수 없습니다.") }
                return@async ""
            }
            val file = transformToFile(context, uri)
            if (file == null) {
                generalError.update { Pair(true, "파일 경로를 찾을 수 없습니다.") }
                return@async ""
            }
            val result = imageRepository.postImage(file)
            if (result.error != null) {
                when (result.error!!.status) {
                    401 -> notFoundTokenError.update { true }
                    404 -> invalidTokenError.update { true }
                    else -> generalError.update { Pair(true, result.error!!.message) }
                }
                return@async ""
            }
            return@async result.data!!.path
        }.await()
    }
}