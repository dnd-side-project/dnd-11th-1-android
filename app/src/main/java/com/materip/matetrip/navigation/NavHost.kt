package com.materip.matetrip.navigation

import android.os.Build
import androidx.annotation.RequiresApi
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

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
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
            navReceivedApplication = {/** 받은 신청서 화면으로 이동 */}
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

        // 마이페이지_동행 후기 작성
        composable(Screen.Review.route) {
            ReviewScreen()
        }
    }
}

fun NavController.navigateToBack() = navigateUp()