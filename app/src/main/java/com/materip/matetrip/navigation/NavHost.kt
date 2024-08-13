package com.materip.matetrip.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.materip.feature_home.ui.FormScreen
import com.materip.feature_home.ui.HomeScreen
import com.materip.feature_home.ui.NavigateToPostScreen
import com.materip.feature_home.ui.NotificationScreen
import com.materip.feature_home.ui.PostBoardScreen
import com.materip.feature_home.ui.ProfileScreen
import com.materip.feature_home.ui.ReviewScreen
import com.materip.feature_home.viewModel.HomeHiltViewModel
import com.materip.matetrip.component.BackButtonTopAppBar
import com.materip.matetrip.component.BackButtonWithTitleTopAppBar
import com.materip.matetrip.component.MateTripTopAppBar


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.NavigateToPost.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // 로그인

        // 온보딩

        // 홈
        composable(Screen.Home.route) {
            HomeScreen()
        }

        // 홈_게시글 작성_디폴트
        composable(Screen.Post.route) {
            PostBoardScreen()
        }

        // 홈_게시글 진입
        composable(Screen.NavigateToPost.route) {
             NavigateToPostScreen()
        }

        // 게시글_동행 신청
        composable(Screen.Form.route) {
            FormScreen()
        }

        // 게시글_프로필 자세히 보기
        composable(Screen.Profile.route) {
            ProfileScreen()
        }

        // 홈_알림_디폴트
        composable(Screen.Notification.route) {
            NotificationScreen()
        }

        // 동행 후기 작성
        composable(Screen.Review.route) {
            ReviewScreen()
        }
    }
}

@Composable
fun GetTopBar(
    currentRoute: String?,
    navController: NavHostController
) {
    val viewModel: HomeHiltViewModel = hiltViewModel()

    when (currentRoute) {
        Screen.Home.route -> { MateTripTopAppBar() }
        Screen.OnBoarding.route -> {
            BackButtonTopAppBar(onNavigateUp = { navController.navigateUp() } )
        }
        Screen.Post.route -> {
            BackButtonWithTitleTopAppBar(
                screenTitle = "동행 모집하기",
                onNavigateUp = { navController.navigateUp() },
                onPostClick =  { viewModel.createPost(viewModel.toBoardRequestDto()) }
            )
        }
        else -> {
            BackButtonTopAppBar(onNavigateUp = { navController.navigateUp() } )
        }
    }
}

