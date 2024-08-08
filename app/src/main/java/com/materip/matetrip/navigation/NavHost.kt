package com.materip.matetrip.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.materip.matetrip.component.BackButtonTopAppBar
import com.materip.matetrip.component.BackButtonWithTitleTopAppBar
import com.materip.matetrip.component.MateTripTopAppBar
import com.materip.matetrip.ui.FormScreen
import com.materip.matetrip.ui.HomeScreen
import com.materip.matetrip.ui.NotificationScreen
import com.materip.matetrip.ui.PostScreen
import com.materip.matetrip.ui.ProfileScreen
import com.materip.matetrip.ui.ReviewScreen

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Post.route) {
            PostScreen()
        }
        composable(Screen.Form.route) {
            FormScreen()
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
        composable(Screen.Notification.route) {
            NotificationScreen()
        }
        composable(Screen.Review.route) {
            ReviewScreen()
        }
    }
}

@Composable
fun GetTopBar(currentRoute: String?, navController: NavHostController) {
    when (currentRoute) {
        Screen.Home.route -> { MateTripTopAppBar() }
        Screen.OnBoarding.route -> {
            BackButtonTopAppBar(onNavigateUp = { navController.navigateUp() } )
        }
        else -> {
            BackButtonWithTitleTopAppBar(
                screenTitle = "동행 모집하기",
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}

