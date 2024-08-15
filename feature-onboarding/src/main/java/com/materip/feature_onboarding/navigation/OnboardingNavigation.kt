package com.materip.feature_onboarding.navigation

import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.materip.feature_onboarding.screens.InputUserInfoRoute
import com.materip.feature_onboarding.screens.SelectFoodStyleRoute
import com.materip.feature_onboarding.screens.SelectTripInterestRoute
import com.materip.feature_onboarding.screens.SelectTripStyleRoute

fun NavController.navigateToInputUserInfo() = navigate(OnboardingRoute.InputUserInfoRoute.name)
fun NavController.navigateToSelectTripInterest(userInfo: String) = navigate("${OnboardingRoute.SelectTripInterestRoute.name}/${userInfo}")
fun NavController.navigateToSelectTripStyle(userInfo: String?, tripInterests: String) = navigate("${OnboardingRoute.SelectTripStyleRoute.name}/${userInfo}/${tripInterests}")
fun NavController.navigateToSelectFoodPreference(userInfo: String?, tripInterests: String?, tripStyles: String) =
    navigate("${OnboardingRoute.SelectFoodPreferenceRoute.name}/${userInfo}/${tripInterests}/${tripStyles}")

fun NavGraphBuilder.inputUserInfo(
    navSelectTripInterest: (userInfo: String) -> Unit,
){
    composable(OnboardingRoute.InputUserInfoRoute.name){
        InputUserInfoRoute(navSelectTripInterest = navSelectTripInterest)
    }
}
fun NavGraphBuilder.selectTripInterest(
    onBackClick: () -> Unit,
    onNextClick: (userInfoJson: String?, tripInterestsJson: String) -> Unit,
){
    composable(
        route = "${OnboardingRoute.SelectTripInterestRoute.name}/{userInfo}",
        arguments = listOf(navArgument("userInfo"){type = NavType.StringType})
    ){
        val userInfo = it.arguments?.getString("userInfo")
        SelectTripInterestRoute(
            userInfo = userInfo,
            onBackClick = onBackClick,
            onNextClick = onNextClick
        )
    }
}
fun NavGraphBuilder.selectTripStyle(
    onBackClick: () -> Unit,
    onNextClick: (userInfo: String?, tripInterests: String?, tripStyles: String) -> Unit,
){
    composable(
        route = "${OnboardingRoute.SelectTripStyleRoute.name}/{userInfo}/{tripInterests}",
        arguments = listOf(
            navArgument(name = "userInfo"){type = NavType.StringType},
            navArgument(name = "tripInterests"){type = NavType.StringType}
        )
    ){
        val userInfo = it.arguments?.getString("userInfo")
        val tripInterests = it.arguments?.getString("tripInterests")
        SelectTripStyleRoute(
            userInfo = userInfo,
            tripInterests = tripInterests,
            onBackClick = onBackClick,
            onNextClick = onNextClick
        )
    }
}
fun NavGraphBuilder.selectFoodPreference(
    onBackClick: () -> Unit,
    navMyPage: () -> Unit,
){
    composable(
        route = "${OnboardingRoute.SelectFoodPreferenceRoute.name}/{userInfo}/{tripInterests}/{tripStyles}",
        arguments = listOf(
            navArgument(name = "userInfo"){type = NavType.StringType},
            navArgument(name = "tripInterests"){type = NavType.StringType},
            navArgument(name = "tripStyles"){type = NavType.StringType},
        )
    ){
        val userInfo = it.arguments?.getString("userInfo")
        val tripInterests = it.arguments?.getString("tripInterests")
        val tripStyles = it.arguments?.getString("tripStyles")
        SelectFoodStyleRoute(
            userInfo = userInfo,
            tripInterests = tripInterests,
            tripStyles = tripStyles,
            onBackClick = onBackClick,
            onNextClick = {
                /** test용 mypage */
                navMyPage
                /** 홈화면으로 navigation */
            }
        )
    }
}