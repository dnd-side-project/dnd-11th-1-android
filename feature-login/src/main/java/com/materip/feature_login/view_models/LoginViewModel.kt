package com.materip.feature_login.view_models

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.materip.core_repository.repository.login_repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
    private val generalError = MutableStateFlow<Pair<Boolean, String?>>(Pair(false,null))

    //login 함수
    fun doLogin(context: Context){
        val loginCallBack: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                generalError.update{Pair(true, "KAKAO LOGIN FAILED")}
            } else if (token != null){
                viewModelScope.launch{loginRepository.saveAuthToken(token.accessToken)}
                _isLogin.update{ true }
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
                    viewModelScope.launch{loginRepository.saveAuthToken(token.accessToken)}
                    _isLogin.update{true}
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = loginCallBack)
        }
    }
}