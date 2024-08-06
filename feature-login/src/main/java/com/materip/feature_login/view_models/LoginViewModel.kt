package com.materip.feature_login.view_models

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.materip.core_common.ErrorState
import com.materip.core_model.request.LoginRequestDto
import com.materip.core_repository.repository.login_repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val _isLogin = MutableStateFlow<Boolean>(false)
    val isLogin get() = _isLogin.asStateFlow()

    private val generalErrorState = MutableStateFlow<Pair<Boolean, String>>(Pair(false, ""))
    val errorState : StateFlow<ErrorState> = generalErrorState.mapLatest{
        ErrorState.NoAuthError(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ErrorState.Loading
    )

    //login 함수
    fun doLogin(context: Context){
        val loginCallBack: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                generalErrorState.update{Pair(true, "KAKAO LOGIN FAILED")}
            } else if (token != null){
                postToken(token.accessToken)
            }
        }
        //카카오톡 로그인
        if(UserApiClient.instance.isKakaoTalkLoginAvailable(context)){
            UserApiClient.instance.loginWithKakaoTalk(context){ token, error ->
                if (error != null){
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled){
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = loginCallBack)
                } else if (token != null){
                    postToken(token.accessToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = loginCallBack)
        }
    }

    private fun postToken(kakaoToken: String){
        viewModelScope.launch{
            loginRepository.saveKakaoAccessToken(kakaoToken)
            val requestDto = LoginRequestDto(provider = "KAKAO", accessToken = kakaoToken)
            val result = loginRepository.loginKakao(requestDto)
            Log.d("API TEST", "result : ${result}")
            if (result.error != null) {
                generalErrorState.update { Pair(true, result.error!!.message) }
                return@launch
            }
            loginRepository.saveAuthToken(result.data!!.accessToken)
            loginRepository.saveRefreshToken(result.data!!.refreshToken)
            _isLogin.update{ true }
        }
    }
}