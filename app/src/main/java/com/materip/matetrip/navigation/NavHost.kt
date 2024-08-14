package com.materip.matetrip.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.materip.feature_login.navigation.login
import com.materip.feature_login.navigation.navigateToTest
import com.materip.feature_login.navigation.test
import com.materip.feature_mypage.navigation.myPageGraph
import com.materip.feature_mypage.navigation.navigateToEditProfile
import com.materip.feature_mypage.navigation.navigateToPreview
import com.materip.feature_mypage.navigation.navigateToProfileDescription
import com.materip.feature_mypage.navigation.navigateToQuiz100
import com.materip.feature_mypage.navigation.navigateToReview
import com.materip.feature_mypage.navigation.navigateToReviewDescription
import com.materip.feature_mypage.navigation.navigateToReviewList
import com.materip.feature_mypage.navigation.navigateToSendApplication
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
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        /** feature-login */
        login(navOnBoarding = navController::navigateToInputUserInfo)
        test()
        inputUserInfo(navSelectTripInterest = navController::navigateToSelectTripInterest)
        selectTripInterest(onBackClick = navController::navigateToBack, onNextClick = navController::navigateToSelectTripStyle)
        selectTripStyle(onBackClick = navController::navigateToBack, onNextClick = navController::navigateToSelectFoodPreference)
        selectFoodPreference(onBackClick = navController::navigateToBack)

        /** my-page module */
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
    }
}

fun NavController.navigateToBack() = navigateUp()