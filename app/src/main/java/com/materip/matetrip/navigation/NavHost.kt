package com.materip.matetrip.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.materip.feature_login.navigation.login
import com.materip.feature_login.navigation.navigateToTest
import com.materip.feature_login.navigation.test

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
        login(
            navOnBoarding = navController::navigateToTest
        )
        test()
    }
}