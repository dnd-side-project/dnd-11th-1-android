package com.materip.feature_mypage.view_models.Setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_common.ErrorState
import com.materip.core_model.request.DeleteAccountRequestDto
import com.materip.core_repository.repository.login_repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): ViewModel() {
    val isDone = MutableStateFlow<Boolean>(false)
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

    fun deleteAccount(reason: String?, onSuccess: () -> Unit) {
        viewModelScope.launch{
            if (reason == null) {
                generalError.update{Pair(true, "탈퇴 이유를 찾을 수 없습니다.")}
                return@launch
            }
            val requestDto = DeleteAccountRequestDto(reason)
            val result = loginRepository.deleteAccount(requestDto)
            if(result.error != null){
                when(result.error!!.status){
                    401 -> invalidTokenError.update{true}
                    404 -> notFoundTokenError.update{true}
                    else -> generalError.update{Pair(true, result.error!!.message)}
                }
                return@launch
            }
            loginRepository.deleteAuthToken()
            loginRepository.deleteRefreshToken()
            onSuccess()
        }
    }
}