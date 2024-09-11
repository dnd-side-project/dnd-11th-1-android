package com.materip.matetrip.navigation

import androidx.annotation.StringRes
import com.materip.matetrip.R
import com.materip.core_designsystem.icon.Logo
import com.materip.core_designsystem.icon.Icons
import com.materip.core_model.navigation.SettingRoute

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: Int? = null) {
    data object SignIn : Screen("sign_in", R.string.sign_in)
    data object OnBoarding : Screen(OnboardingRoute.InputUserInfoRoute.name, R.string.onboarding, Icons.arrow_back_icon)
    data object Home : Screen("home", R.string.home, Logo.splash_icon_02)
    data object NavigateToPost : Screen("navigateToPost", R.string.navigate_to_post, Icons.arrow_back_icon)
    data object Post : Screen("post", R.string.post, Icons.arrow_back_icon)
    data object Form : Screen("form", R.string.form, Icons.arrow_back_icon)
    data object Profile : Screen("profile", R.string.profile, Icons.arrow_back_icon)
    data object Notification : Screen("notification", R.string.notification, Icons.arrow_back_icon)
    data object MyPage : Screen(MyPageRoute.MyPageGraph.name, R.string.my_page, Icons.arrow_back_icon)
    data object Setting : Screen(SettingRoute.SettingGraph.name, R.string.setting, Icons.arrow_back_icon)

    companion object {
        fun fromRoute(route: String?): Screen {
            return when (route) {
                SignIn.route -> SignIn
                OnBoarding.route -> OnBoarding
                Home.route -> Home
                NavigateToPost.route -> NavigateToPost
                Post.route -> Post
                Form.route -> Form
                Profile.route -> Profile
                Notification.route -> Notification
                MyPage.route -> MyPage
                Setting.route -> Setting
                else -> Home
            }
        }
    }
}