package com.materip.feature_login.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_repository.repository.login_repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): ViewModel(){
    private var _accessToken = MutableStateFlow<String?>(null)
    val accessToken get() = _accessToken.asStateFlow()
    private var _refreshToken = MutableStateFlow<String?>(null)
    val refreshToken get() = _refreshToken.asStateFlow()

    init{
        initAccessToken()
        initRefreshToken()
    }

    private fun initAccessToken(){
        viewModelScope.launch{
            loginRepository.getAuthToken().collectLatest{
                _accessToken.update{it}
            }
        }
    }

    private fun initRefreshToken(){
        viewModelScope.launch{
            loginRepository.getRefreshToken().collectLatest{
                _refreshToken.update{it}
            }
        }
    }
}