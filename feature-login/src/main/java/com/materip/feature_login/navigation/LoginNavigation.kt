package com.materip.feature_login.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.materip.feature_login.LoginRoute

fun NavController.navigateToLogin() = navigate(LoginRoute.LoginRoute.name)

fun NavGraphBuilder.login(){
    composable(route = LoginRoute.LoginRoute.name){
        LoginRoute()
    }
}