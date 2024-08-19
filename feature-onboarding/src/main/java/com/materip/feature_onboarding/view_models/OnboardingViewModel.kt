package com.materip.feature_onboarding.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_common.ErrorState
import com.materip.core_model.request.SaveOnboardingRequestDto
import com.materip.core_model.ui_model.FoodPreference
import com.materip.core_model.ui_model.SelectInterests
import com.materip.core_model.ui_model.SelectStyles
import com.materip.core_model.ui_model.UserInfo
import com.materip.core_repository.repository.onboarding_repository.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository
): ViewModel(){

    val isDone = MutableStateFlow<Boolean>(false)
    private val invalidTokenError = MutableStateFlow<Boolean>(false)
    private val notFoundTokenError= MutableStateFlow<Boolean>(false)
    private val generalError= MutableStateFlow<Pair<Boolean, String?>>(Pair(false, null))
    val errorState = combine(invalidTokenError, notFoundTokenError, generalError){ invalidToken, notFoundToken, general ->
        if(invalidToken) {
            ErrorState.AuthError(invalidToken, false, Pair(false, null))
        } else if(notFoundToken) {
            ErrorState.AuthError(false, notFoundToken, Pair(false, null))
        } else {
            ErrorState.AuthError(false, false, general)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ErrorState.Loading
    )

    fun postOnboarding(
        userInfo: String?,
        tripInterests: String?,
        tripStyles: String?,
        foodPreferences: List<FoodPreference>
    ){
        if (userInfo.isNullOrEmpty() || tripInterests.isNullOrEmpty() || tripStyles.isNullOrEmpty() || foodPreferences.isEmpty()){
            generalError.update{ Pair(true, "데이터 인식 오류")}
        } else {
            val userInfo = Json.decodeFromString<UserInfo>(userInfo)
            val tripInterests = Json.decodeFromString<SelectInterests>(tripInterests)
            val tripStyles = Json.decodeFromString<SelectStyles>(tripStyles)
            val travelPreferences = tripInterests.tripInterest.map{it.name}
            val travelStyles = tripStyles.tripStyle.map{it.name}

            val requestDto = SaveOnboardingRequestDto(
                birthYear = userInfo.birthYear.toInt(),
                gender = userInfo.gender,
                travelPreferences = travelPreferences,
                travelStyles = travelStyles,
                foodPreferences = foodPreferences.map{it.name}
            )
            viewModelScope.launch{
                val result = onboardingRepository.saveOnboardingResult(requestDto)
                if (result.error != null){
                    when(result.error!!.status){
                        401 -> invalidTokenError.update{true}
                        404 -> notFoundTokenError.update{true}
                        else -> generalError.update{Pair(true, result.error!!.message)}
                    }
                    return@launch
                }
                isDone.update{true}
            }
        }
    }
}