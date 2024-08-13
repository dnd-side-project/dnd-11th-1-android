package com.materip.feature_login.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_repository.repository.login_repository.LoginRepository
import com.materip.core_repository.repository.test_repository.TestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val testRepository: TestRepository
): ViewModel(){
    private var _accessToken = MutableStateFlow<String?>(null)
    val accessToken get() = _accessToken.asStateFlow()
    private var _refreshToken = MutableStateFlow<String?>(null)
    val refreshToken get() = _refreshToken.asStateFlow()

    init{
        initAccessToken()
        initRefreshToken()
        doTest()
    }

    private fun initAccessToken(){
        runBlocking{
            _accessToken.update {
                loginRepository.getAuthToken().firstOrNull()
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

    private fun doTest(){
        viewModelScope.launch{
            val result = testRepository.getTest()
            Log.d("API TEST", "RESULT : ${result}")
        }
    }
}