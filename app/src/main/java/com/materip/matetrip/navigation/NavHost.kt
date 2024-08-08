package com.materip.matetrip.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.materip.feature_login.navigation.login
import com.materip.feature_login.navigation.navigateToTest
import com.materip.feature_login.navigation.test
import com.materip.feature_onboarding.navigation.inputUserInfo
import com.materip.feature_onboarding.navigation.navigateToInputUserInfo
import com.materip.feature_onboarding.navigation.selectFoodPreference
import com.materip.feature_onboarding.navigation.selectTripInterest
import com.materip.feature_onboarding.navigation.selectTripStyle

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        /** feature-login */
        login(navOnBoarding = navController::navigateToInputUserInfo)
        test()
        inputUserInfo()
        selectTripStyle(onBackClick = navController::navigateToBack)
        selectTripInterest(onBackClick = navController::navigateToBack)
        selectFoodPreference()
    }
}

fun NavController.navigateToBack() = navigateUp()