package com.materip.matetrip.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
    startDestination: String = Screen.SignIn.route
) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentRoute = currentDestination?.route ?: Screen.Home.route

    Scaffold(
        topBar = {
            MateTripTopAppBar(
//                currentRoute = currentRoute,
//                onNavigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = { /* BottomBar() */ }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
//            composable(Screen.SignIn.route) {
//                SignInScreen(
//                    onSignInSuccess = { navController.navigate(Screen.Home.route) },
//                )
//            }
//            composable(Screen.OnBoarding.route) {
//                OnBoardingScreen(
//                    onComplete = { navController.navigate(Screen.Home.route) }
//                )
//            }
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
}

