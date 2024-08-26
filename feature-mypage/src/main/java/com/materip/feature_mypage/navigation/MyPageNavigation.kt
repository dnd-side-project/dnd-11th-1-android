package com.materip.feature_mypage.navigation

import androidx.compose.runtime.internal.composableLambda
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.materip.feature_mypage.screen.MyPage.EditProfileRoute
import com.materip.feature_mypage.screen.MyPage.MyPageRoute
import com.materip.feature_mypage.screen.MyPage.PreviewRoute
import com.materip.feature_mypage.screen.MyPage.ProfileDescriptionRoute
import com.materip.feature_mypage.screen.MyPage.Quiz100Route
import com.materip.feature_mypage.screen.MyPage.ReviewDescriptionRoute
import com.materip.feature_mypage.screen.MyPage.ReviewListRoute
import com.materip.feature_mypage.screen.MyPage.ReviewRoute
import com.materip.feature_mypage.screen.MyPage.SendApplicationRoute
import com.materip.feature_mypage.screen.MyPage.WriteReviewRoute

fun NavController.navigateToMyPageGraph() = navigate(MyPageRoute.MyPageGraph.name)
fun NavController.navigateToMyPage() = navigate(MyPageRoute.MyPageRoute.name){
    this.launchSingleTop = true
    this.popUpTo(graph.startDestinationId){
        inclusive = true
    }
}
fun NavController.navigateToEditProfile() = navigate(MyPageRoute.EditProfileRoute.name)
fun NavController.navigateToProfileDescription() = navigate(MyPageRoute.ProfileDescriptionRoute.name)
fun NavController.navigateToQuiz100() = navigate(MyPageRoute.Quiz100Route.name)
fun NavController.navigateToPreview() = navigate(MyPageRoute.PreviewRoute.name)
fun NavController.navigateToReview() = navigate(MyPageRoute.ReviewRoute.name)
fun NavController.navigateToReviewList() = navigate(MyPageRoute.ReviewListRoute.name)
fun NavController.navigateToReviewDescription() = navigate(MyPageRoute.ReviewDescriptionRoute.name)
fun NavController.navigateToSendApplication(applicationId: Int) = navigate("${MyPageRoute.SendApplicationRoute.name}/${applicationId}")
fun NavController.navigateToWriteReview(boardId: Int) = navigate("${MyPageRoute.WriteReviewRoute.name}/${boardId}")

fun NavGraphBuilder.myPageGraph(
    navBack: () -> Unit,
    navEditProfile: () -> Unit,
    navProfileDescription: () -> Unit,
    navQuiz100: () -> Unit,
    navPreview: () -> Unit,
    navSendApplication: (Int) -> Unit,
    navReview: () -> Unit,
    navReviewDescription: () -> Unit,
    navReviewList: () -> Unit,
){
    navigation(
        startDestination = MyPageRoute.MyPageRoute.name,
        route = MyPageRoute.MyPageGraph.name
    ){
        composable(route = MyPageRoute.MyPageRoute.name){
            MyPageRoute(
                navEditProfile = navEditProfile,
                navProfileDescription = navProfileDescription,
                navQuiz100 = navQuiz100,
                navPreview = navPreview,
                navSendApplication = navSendApplication
            )
        }
        composable(route = MyPageRoute.EditProfileRoute.name){
            EditProfileRoute(navBack = navBack)
        }
        composable(route = MyPageRoute.ProfileDescriptionRoute.name){
            ProfileDescriptionRoute(navBack = navBack)
        }
        composable(route = MyPageRoute.Quiz100Route.name){
            Quiz100Route(navBack = navBack)
        }
        composable(route = MyPageRoute.PreviewRoute.name){
            PreviewRoute(
                navBack = navBack,
                navReview = navReview,
                navReviewList = navReviewList,
                navReviewDescription = navReviewDescription
            )
        }
        composable(route = MyPageRoute.ReviewRoute.name){
            ReviewRoute(navBack = navBack)
        }
        composable(route = MyPageRoute.ReviewListRoute.name){
            ReviewListRoute(
                navBack = navBack,
                navReviewDescription = navReviewDescription
            )
        }
        composable(route = MyPageRoute.ReviewDescriptionRoute.name){
            ReviewDescriptionRoute(
                navBack = navBack
            )
        }
        composable(
            route = "${MyPageRoute.SendApplicationRoute.name}/{applicationId}",
            arguments = listOf(navArgument("applicationId"){type = NavType.IntType})
        ){
            val id = it.arguments?.getInt("applicationId")
            SendApplicationRoute(
                id = id,
                navBack = navBack,
                navPostDescription = {/** 게시글 상세로 navigation */}
            )
        }
        composable(
            route = "${MyPageRoute.WriteReviewRoute.name}/{boardId}",
            arguments = listOf(navArgument("boardId"){type = NavType.IntType})
        ){
            val id = it.arguments?.getInt("boardId")
            WriteReviewRoute(
                id = id,
                navBack = navBack
            )
        }
    }
}