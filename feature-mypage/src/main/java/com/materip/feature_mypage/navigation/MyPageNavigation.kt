package com.materip.feature_mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.materip.feature_mypage.screen.EditProfileRoute
import com.materip.feature_mypage.screen.MyPageRoute
import com.materip.feature_mypage.screen.PreviewRoute
import com.materip.feature_mypage.screen.ProfileDescriptionRoute
import com.materip.feature_mypage.screen.Quiz100Route
import com.materip.feature_mypage.screen.ReviewDescriptionRoute
import com.materip.feature_mypage.screen.ReviewListRoute
import com.materip.feature_mypage.screen.ReviewRoute
import com.materip.feature_mypage.screen.SendApplicationRoute

fun NavController.navigateToMyPage() = navigate(MyPageRoute.MyPageRoute.name)
fun NavController.navigateToEditProfile() = navigate(MyPageRoute.EditProfileRoute.name)
fun NavController.navigateToProfileDescription() = navigate(MyPageRoute.ProfileDescriptionRoute.name)
fun NavController.navigateToQuiz100() = navigate(MyPageRoute.Quiz100Route.name)
fun NavController.navigateToPreview() = navigate(MyPageRoute.PreviewRoute.name)
fun NavController.navigateToReview() = navigate(MyPageRoute.ReviewRoute.name)
fun NavController.navigateToReviewList() = navigate(MyPageRoute.ReviewListRoute.name)
fun NavController.navigateToReviewDescription() = navigate(MyPageRoute.ReviewDescriptionRoute.name)
fun NavController.navigateToSendApplication() = navigate(MyPageRoute.SendApplicationRoute.name)

fun NavGraphBuilder.myPageGraph(
    navBack: () -> Unit,
    navEditProfile: () -> Unit,
    navProfileDescription: () -> Unit,
    navQuiz100: () -> Unit,
    navPreview: () -> Unit,
    navSendApplication: () -> Unit,
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
        composable(route = MyPageRoute.SendApplicationRoute.name){
            SendApplicationRoute()
        }
    }
}