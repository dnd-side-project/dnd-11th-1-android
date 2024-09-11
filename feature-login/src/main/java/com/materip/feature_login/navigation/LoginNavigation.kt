package com.materip.feature_login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.materip.feature_login.screens.LoginRoute
import com.materip.core_model.navigation.LoginRoute

fun NavController.navigateToLogin() = navigate(LoginRoute.LoginRoute.name)

fun NavGraphBuilder.login(
    navOnBoarding: () -> Unit,
    navHome: () -> Unit,
){
    composable(route = LoginRoute.LoginRoute.name){
        LoginRoute(
            navOnBoarding = navOnBoarding,
            navHome = navHome
        )
    }
}