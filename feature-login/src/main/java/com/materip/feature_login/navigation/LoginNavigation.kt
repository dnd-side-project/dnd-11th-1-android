package com.materip.feature_login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.materip.feature_login.screens.LoginRoute
import com.materip.feature_login.screens.ScreenTest

fun NavController.navigateToLogin() = navigate(LoginRoute.LoginRoute.name)
fun NavController.navigateToTest() = navigate("TEST_SCREEN")

fun NavGraphBuilder.login(
    navOnBoarding: () -> Unit
){
    composable(route = LoginRoute.LoginRoute.name){
        LoginRoute(
            navOnBoarding = navOnBoarding
        )
    }
}

fun NavGraphBuilder.test(){
    composable(route = "TEST_SCREEN"){
        ScreenTest()
    }
}