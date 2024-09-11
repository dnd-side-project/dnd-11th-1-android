package com.materip.matetrip

import androidx.lifecycle.ViewModel
import com.materip.core_model.navigation.LoginRoute
import com.materip.core_model.navigation.MyPageRoute
import com.materip.core_model.navigation.OnboardingRoute
import com.materip.core_repository.repository.login_repository.LoginRepository
import com.materip.core_repository.repository.onboarding_repository.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val onboardingRepository: OnboardingRepository
): ViewModel() {
    private val authToken = MutableStateFlow<String?>(null)
    private val isOnboardingCompleted = MutableStateFlow<Boolean>(false)

    init{
        runBlocking{
            authToken.update { loginRepository.getAuthToken().firstOrNull() }
            isOnboardingCompleted.update{
                val result = onboardingRepository.isOnboardingCompleted()
                if (result.error != null) {
                    return@update false
                }
                result.data!!
            }
        }
    }

    fun getDestination(): String {
        return if (authToken.value == null) {
            LoginRoute.LoginRoute.name
        } else {
            if (isOnboardingCompleted.value) MyPageRoute.MyPageGraph.name // Screen.Home.route
            else OnboardingRoute.InputUserInfoRoute.name
        }
    }
}

