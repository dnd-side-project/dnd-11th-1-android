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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.materip.feature_home.ui.FormScreen
import com.materip.feature_home.ui.HomeScreen
import com.materip.feature_home.ui.NavigateToPostScreen
import com.materip.feature_home.ui.NotificationScreen
import com.materip.feature_home.ui.PostBoardScreen
import com.materip.feature_home.ui.ProfileScreen
import com.materip.feature_home.ui.ReviewScreen
import com.materip.feature_home.viewModel.HomeViewModel
import com.materip.matetrip.component.BackButtonTopAppBar
import com.materip.matetrip.component.BackButtonWithTitleTopAppBar
import com.materip.matetrip.component.MateTripTopAppBar
import com.materip.feature_login.navigation.login
import com.materip.feature_login.navigation.navigateToTest
import com.materip.feature_login.navigation.test
import com.materip.feature_onboarding.navigation.inputUserInfo
import com.materip.feature_onboarding.navigation.navigateToInputUserInfo
import com.materip.feature_onboarding.navigation.navigateToSelectFoodPreference
import com.materip.feature_onboarding.navigation.navigateToSelectTripInterest
import com.materip.feature_onboarding.navigation.navigateToSelectTripStyle
import com.materip.feature_onboarding.navigation.selectFoodPreference
import com.materip.feature_onboarding.navigation.selectTripInterest
import com.materip.feature_onboarding.navigation.selectTripStyle

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
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
            .background(Color.White)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // TODO: 로그인 및 온보딩 네비게이션
        login(navOnBoarding = navController::navigateToInputUserInfo)
        test()
        inputUserInfo(navSelectTripInterest = navController::navigateToSelectTripInterest)
        selectTripInterest(onBackClick = navController::navigateToBack, onNextClick = navController::navigateToSelectTripStyle)
        selectTripStyle(onBackClick = navController::navigateToBack, onNextClick = navController::navigateToSelectFoodPreference)
        selectFoodPreference(onBackClick = navController::navigateToBack)

        // 홈
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToPostDetail = { boardId ->
                    navController.navigate(Screen.NavigateToPost.route + "/$boardId")
                }
            )
        }

        // 홈_알림_디폴트
        composable(Screen.Notification.route) {
            NotificationScreen()
        }

        // 홈_게시글 작성_디폴트
        composable(Screen.Post.route) {
            PostBoardScreen()
        }

        // 홈_게시글 진입
        composable(
            route = Screen.NavigateToPost.route + "/{boardId}",
            arguments = listOf(navArgument("boardId") { type = NavType.IntType })
        ) { backStackEntry ->
            val boardId = backStackEntry.arguments?.getInt("boardId") ?: 0
            NavigateToPostScreen(
                boardId = boardId,
                onNavigateToForm = { navController.navigate(Screen.Form.route + "/$boardId") },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) }
            )
        }

        // 게시글_동행 신청
        composable(
            route = Screen.Form.route + "/{boardId}",
            arguments = listOf(navArgument("boardId") { type = NavType.IntType })
        ) { backStackEntry ->
            val boardId = backStackEntry.arguments?.getInt("boardId") ?: 0
            // TODO: 보낸 신청서 내역으로 이동
            FormScreen(
                boardId = boardId,
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) }
            )
        }

        // 게시글_프로필 자세히 보기
        composable(Screen.Profile.route) {
            ProfileScreen()
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
    val viewModel: HomeViewModel = hiltViewModel()

    when (currentRoute) {
        Screen.Home.route -> {
            MateTripTopAppBar(
                onNotificationClick = { navController.navigate(Screen.Notification.route) }
            )
        }

        Screen.Post.route -> {
            BackButtonWithTitleTopAppBar(
                screenTitle = "동행 모집하기",
                onNavigateUp = { navController.navigateUp() },
                onPostClick = {
                    val boardIdDto = viewModel.createPost(viewModel.toBoardRequestDto())
                    navController.navigate(Screen.NavigateToPost.route + "/${boardIdDto.boardId}")
                }
            )
        }

        Screen.Form.route -> {
            BackButtonTopAppBar(
                screenTitle = "동행 신청서 작성",
                onNavigateUp = { navController.navigateUp() }
            )
        }

        else -> {
            BackButtonTopAppBar(
                screenTitle = "",
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}

fun NavController.navigateToBack() = navigateUp()
