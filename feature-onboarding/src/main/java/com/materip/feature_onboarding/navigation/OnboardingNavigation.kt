package com.materip.feature_onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.materip.feature_onboarding.screens.InputUserInfoRoute
import com.materip.feature_onboarding.screens.SelectFoodStyleRoute
import com.materip.feature_onboarding.screens.SelectTripInterestRoute
import com.materip.feature_onboarding.screens.SelectTripStyleRoute

fun NavController.navigateToInputUserInfo() = navigate(OnboardingRoute.InputUserInfoRoute.name)
fun NavController.navigateToSelectTripInterest() = navigate(OnboardingRoute.SelectTripInterestRoute.name)
fun NavController.navigateToSelectTripStyle() = navigate(OnboardingRoute.SelectTripStyleRoute.name)
fun NavController.navigateToSelectFoodPreference() = navigate(OnboardingRoute.SelectFoodPreferenceRoute.name)

fun NavGraphBuilder.inputUserInfo(){
    composable(OnboardingRoute.InputUserInfoRoute.name){
        InputUserInfoRoute()
    }
}
fun NavGraphBuilder.selectTripInterest(
    onBackClick: () -> Unit,
){
    composable(OnboardingRoute.SelectTripInterestRoute.name){
        SelectTripInterestRoute(onBackClick = onBackClick)
    }
}
fun NavGraphBuilder.selectTripStyle(
    onBackClick: () -> Unit
){
    composable(OnboardingRoute.SelectTripStyleRoute.name){
        SelectTripStyleRoute(onBackClick = onBackClick)
    }
}
fun NavGraphBuilder.selectFoodPreference(){
    composable(OnboardingRoute.SelectFoodPreferenceRoute.name){
        SelectFoodStyleRoute()
    }
}