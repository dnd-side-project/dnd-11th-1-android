package com.materip.matetrip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_repository.repository.login_repository.LoginRepository
import com.materip.feature_login.navigation.LoginRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): ViewModel() {
    private val authToken = MutableStateFlow<String?>(null)

    init{
        viewModelScope.launch{
            loginRepository.getAuthToken().collectLatest{
                authToken.update{it}
            }
        }
    }

    fun getDestination() = if (authToken.value == null) LoginRoute.LoginRoute.name else "TEST_SCREEN"
}