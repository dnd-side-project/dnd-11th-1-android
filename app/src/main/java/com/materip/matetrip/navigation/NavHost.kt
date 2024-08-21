package com.materip.matetrip.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.materip.feature_home3.ui.FormScreen
import com.materip.feature_home3.ui.HomeScreen
import com.materip.feature_home3.ui.NavigateToPostScreen
import com.materip.feature_home3.ui.NotificationScreen
import com.materip.feature_home3.ui.PostBoardScreen
import com.materip.feature_home3.ui.ProfileScreen
import com.materip.feature_home3.ui.ReviewScreen
import com.materip.feature_login.navigation.login
import com.materip.feature_mypage.navigation.myPageGraph
import com.materip.feature_mypage.navigation.navigateToEditProfile
import com.materip.feature_mypage.navigation.navigateToPreview
import com.materip.feature_mypage.navigation.navigateToProfileDescription
import com.materip.feature_mypage.navigation.navigateToQuiz100
import com.materip.feature_mypage.navigation.navigateToReview
import com.materip.feature_mypage.navigation.navigateToReviewDescription
import com.materip.feature_mypage.navigation.navigateToReviewList
import com.materip.feature_mypage.navigation.navigateToSendApplication
import com.materip.feature_mypage.navigation.settingGraph
import com.materip.feature_onboarding.navigation.inputUserInfo
import com.materip.feature_onboarding.navigation.navigateToInputUserInfo
import com.materip.feature_onboarding.navigation.navigateToSelectFoodPreference
import com.materip.feature_onboarding.navigation.navigateToSelectTripInterest
import com.materip.feature_onboarding.navigation.navigateToSelectTripStyle
import com.materip.feature_onboarding.navigation.selectFoodPreference
import com.materip.feature_onboarding.navigation.selectTripInterest
import com.materip.feature_onboarding.navigation.selectTripStyle
import com.materip.matetrip.component.BackButtonTopAppBar
import com.materip.matetrip.component.BackButtonWithTitleTopAppBar
import com.materip.matetrip.component.MateTripTopAppBar

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
            navOnBoarding = navController::navigateToInputUserInfo,
            navHome = { navController.navigate(Screen.Home.route) }
        )
        inputUserInfo(navSelectTripInterest = navController::navigateToSelectTripInterest)
        selectTripInterest(onBackClick = navController::navigateToBack, onNextClick = navController::navigateToSelectTripStyle)
        selectTripStyle(onBackClick = navController::navigateToBack, onNextClick = navController::navigateToSelectFoodPreference)
        selectFoodPreference(
            onBackClick = navController::navigateToBack,
            navHome = { navController.navigate(Screen.Home.route) }
        )

        /** setting graph */
        settingGraph()

        /** my-page graph */
        myPageGraph(
            navBack = navController::navigateToBack,
            navEditProfile = navController::navigateToEditProfile,
            navPreview = navController::navigateToPreview,
            navProfileDescription = navController::navigateToProfileDescription,
            navQuiz100 = navController::navigateToQuiz100,
            navSendApplication = navController::navigateToSendApplication,
            navReview = navController::navigateToReview,
            navReviewList = navController::navigateToReviewList,
            navReviewDescription = navController::navigateToReviewDescription
        )

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
                onNavigateToUserProfile = { navController.navigate(Screen.Profile.route) }
            )
        }

        // 게시글_동행 신청
        composable(Screen.Form.route + "/{boardId}") {
            FormScreen(
                boardId = it.arguments?.getInt("boardId") ?: 0,
                onNavigateUp = { navController.navigateUp() }
            )
        }

        // 게시글_프로필 자세히 보기
        composable(Screen.Profile.route) {
            ProfileScreen()
        }

        // 마이페이지_동행 후기 작성
        composable(Screen.Review.route) {
            ReviewScreen()
        }
    }
}

fun NavController.navigateToBack() = navigateUp()