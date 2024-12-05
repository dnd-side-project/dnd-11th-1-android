package com.materip.feature_mypage.view_models.Setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_common.ErrorState
import com.materip.core_common.Result
import com.materip.core_common.asResult
import com.materip.core_model.response.GetProfileResponseDto
import com.materip.core_repository.repository.login_repository.LoginRepository
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
import javax.inject.Inject

@HiltViewModel
class AccountInfoViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val loginRepository: LoginRepository
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

    val uiState: StateFlow<AccountInfoUiState> = errorState.map{
        if(it is ErrorState.AuthError && it.isInvalid()) throw Exception("Error")
        val result = profileRepository.getProfile()
        if (result.error != null){ throw Exception("${result.error!!.status}:${result.error!!.message}") }
        result.data!!
    }.asResult().map{ result ->
        when(result){
            Result.Loading -> AccountInfoUiState.Loading
            is Result.Error -> {
                val (status, message) = result.exception.message!!.split(":")
                when(status){
                    "401" -> invalidTokenError.update{true}
                    "404" -> notFoundTokenError.update{true}
                    else -> generalError.update{Pair(true, message)}
                }
                AccountInfoUiState.Error
            }
            is Result.Success -> AccountInfoUiState.Success(result.data)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AccountInfoUiState.Loading
    )

    fun logout(onSuccess: () -> Unit){
        viewModelScope.launch{
            loginRepository.deleteAuthToken()
            loginRepository.deleteRefreshToken()
            onSuccess()
        }
    }
}

sealed interface AccountInfoUiState{
    data object Loading: AccountInfoUiState
    data object Error: AccountInfoUiState
    data class Success(
        val userInfo: GetProfileResponseDto
    ): AccountInfoUiState
}