package com.materip.matetrip.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.materip.core_model.navigation.MyPageRoute
import com.materip.feature_home3.ui.FormScreen
import com.materip.feature_home3.ui.HomeScreen
import com.materip.feature_home3.ui.NavigateToPostScreen
import com.materip.feature_home3.ui.NotificationScreen
import com.materip.feature_home3.ui.PostBoardScreen
import com.materip.feature_home3.ui.ProfileScreen
import com.materip.feature_home3.viewModel.PostBoardViewModel
import com.materip.feature_home3.viewModel.ProfileViewModel
import com.materip.feature_login.navigation.login
import com.materip.feature_login.navigation.navigateToLogin
import com.materip.feature_mypage.navigation.myPageGraph
import com.materip.feature_mypage.navigation.navigateToAccountDeletionNotice
import com.materip.feature_mypage.navigation.navigateToAccountInfo
import com.materip.feature_mypage.navigation.navigateToAlarmSetting
import com.materip.feature_mypage.navigation.navigateToDeleteAccount
import com.materip.feature_mypage.navigation.navigateToEditProfile
import com.materip.feature_mypage.navigation.navigateToGetAuthCode
import com.materip.feature_mypage.navigation.navigateToLogout
import com.materip.feature_mypage.navigation.navigateToPreview
import com.materip.feature_mypage.navigation.navigateToProfileDescription
import com.materip.feature_mypage.navigation.navigateToQuiz100
import com.materip.feature_mypage.navigation.navigateToReceivedApplication
import com.materip.feature_mypage.navigation.navigateToReviewDescription
import com.materip.feature_mypage.navigation.navigateToReviewEvaluation
import com.materip.feature_mypage.navigation.navigateToReviewList
import com.materip.feature_mypage.navigation.navigateToSMSVerification
import com.materip.feature_mypage.navigation.navigateToSendApplication
import com.materip.feature_mypage.navigation.navigateToSetting
import com.materip.feature_mypage.navigation.navigateToWriteReview
import com.materip.feature_mypage.navigation.settingGraph
import com.materip.feature_onboarding.navigation.inputUserInfo
import com.materip.feature_onboarding.navigation.navigateToInputUserInfo
import com.materip.feature_onboarding.navigation.navigateToSelectFoodPreference
import com.materip.feature_onboarding.navigation.navigateToSelectTripInterest
import com.materip.feature_onboarding.navigation.navigateToSelectTripStyle
import com.materip.feature_onboarding.navigation.selectFoodPreference
import com.materip.feature_onboarding.navigation.selectTripInterest
import com.materip.feature_onboarding.navigation.selectTripStyle

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String,
    postBoardViewModel: PostBoardViewModel,
) {
    val navPost = remember<(Int) -> Unit>{ { navController.navigate("${Screen.NavigateToPost.route}/${it}") }}
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        /** feature-login */
        login(
            navOnBoarding = navController::navigateToInputUserInfo,
            navHome = { navController.navigate(Screen.Home.route) }
        )
        inputUserInfo(navSelectTripInterest = navController::navigateToSelectTripInterest)
        selectTripInterest(
            onBackClick = navController::navigateToBack,
            onNextClick = navController::navigateToSelectTripStyle
        )
        selectTripStyle(
            onBackClick = navController::navigateToBack,
            onNextClick = navController::navigateToSelectFoodPreference
        )
        selectFoodPreference(
            onBackClick = navController::navigateToBack,
            navHome = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route)
                }
            }
        )

        /** setting graph */
        settingGraph(
            navHome = { navController.navigate(Screen.Home.route) },
            navLogin = navController::navigateToLogin,
            navSetting = navController::navigateToSetting,
            navAccountInfo = navController::navigateToAccountInfo,
            navAlarmSetting = navController::navigateToAlarmSetting,
            navGetAuthCode = navController::navigateToGetAuthCode,
            navSmsVerification = navController::navigateToSMSVerification,
            navLogout = navController::navigateToLogout,
            navDeleteAccount = navController::navigateToDeleteAccount,
            navAccountDeletionNotice = navController::navigateToAccountDeletionNotice,
            navBack = navController::navigateToBack,
        )

        /** my-page graph */
        myPageGraph(
            navBack = navController::navigateToBack,
            navEditProfile = navController::navigateToEditProfile,
            navPreview = navController::navigateToPreview,
            navProfileDescription = navController::navigateToProfileDescription,
            navQuiz100 = navController::navigateToQuiz100,
            navSendApplication = navController::navigateToSendApplication,
            navReviewEvaluation = navController::navigateToReviewEvaluation,
            navReviewList = navController::navigateToReviewList,
            navReviewDescription = navController::navigateToReviewDescription,
            navReviewWrite = navController::navigateToWriteReview,
            navReceivedApplication = navController::navigateToReceivedApplication,
            navPostBoard = navPost
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
        composable(Screen.Post.route) { backStackEntry ->
            val lifecycle = backStackEntry.lifecycle
            DisposableEffect(lifecycle) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_CREATE) {
                        postBoardViewModel.resetState()
                    }
                }
                lifecycle.addObserver(observer)
                onDispose {
                    lifecycle.removeObserver(observer)
                }
            }
            PostBoardScreen(
                viewModel = postBoardViewModel
            )
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
                onNavigateToUserProfile = { navController.navigate(Screen.Profile.route + "/$boardId") },
                onNavigateUp = navController::navigateToBack
            )
        }

        // 게시글_동행 신청
        composable(Screen.Form.route + "/{boardId}") {
            FormScreen(
                boardId = it.arguments?.getInt("boardId") ?: 0,
                onNavigateUp = { navController.navigateUp() }
            )
        }

        // 게시글_프로필 자세히 보기 (로그인한 유저가 다른 유저의 프로필을 보는 경우)
        composable(
            route = Screen.Profile.route + "/{boardId}",
            arguments = listOf(navArgument("boardId") { type = NavType.IntType })
        ) { backStackEntry ->
            val profileViewModel: ProfileViewModel = hiltViewModel(backStackEntry)
            ProfileScreen(
                navBack = navController::navigateToBack,
                navEvaluation = { navController.navigate(MyPageRoute.ReviewEvaluationRoute.name) },
                navReviewDescription = { reviewId ->
                    navController.navigate("${MyPageRoute.ReviewDescriptionRoute.name}/${reviewId}")
                },
                profileViewModel = profileViewModel
            )
        }
    }
}

fun NavController.navigateToBack() = navigateUp()