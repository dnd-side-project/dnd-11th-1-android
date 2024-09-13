package com.materip.feature_mypage.view_models.MyPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_common.ErrorState
import com.materip.core_common.Result
import com.materip.core_common.asResult
import com.materip.core_model.response.GetProfileDetailsResponseDto
import com.materip.core_repository.repository.profile_repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileDescriptionViewModel @Inject constructor(
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
    val uiState: StateFlow<ProfileDescriptionUiState> = errorState.map{
        if(it is ErrorState.AuthError && it.isInvalid()) throw Exception("")
        val result = profileRepository.getProfileDetails()
        if (result.error != null){
            when(result.error!!.status){
                404 -> notFoundTokenError.update{true}
                else -> generalError.update{Pair(true, result.error!!.message)}
            }
            throw Exception("")
        }
        result.data
    }.asResult().map{result ->
        Log.d("MATETRIP API TEST", "map result : ${result}")
        when(result){
            Result.Loading -> ProfileDescriptionUiState.Loading
            is Result.Success -> ProfileDescriptionUiState.Success(user = result.data!!)
            is Result.Error -> ProfileDescriptionUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ProfileDescriptionUiState.Loading
    )
}

sealed interface ProfileDescriptionUiState{
    data object Loading: ProfileDescriptionUiState
    data class Success(
        val user: GetProfileDetailsResponseDto
    ): ProfileDescriptionUiState
    data object Error: ProfileDescriptionUiState
}