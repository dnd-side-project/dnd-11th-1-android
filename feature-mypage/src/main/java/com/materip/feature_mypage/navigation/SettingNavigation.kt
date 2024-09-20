package com.materip.feature_mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.materip.feature_mypage.screen.Setting.AccountDeletionNoticeRoute
import com.materip.feature_mypage.screen.Setting.AccountInfoRoute
import com.materip.feature_mypage.screen.Setting.AlarmSettingRoute
import com.materip.feature_mypage.screen.Setting.DeleteAccountDoneRoute
import com.materip.feature_mypage.screen.Setting.DeleteAccountRoute
import com.materip.feature_mypage.screen.Setting.GetAuthCodeRoute
import com.materip.feature_mypage.screen.Setting.LogoutRoute
import com.materip.feature_mypage.screen.Setting.SettingRoute
import com.materip.feature_mypage.screen.Setting.SmsVerificationRoute
import com.materip.core_model.navigation.SettingRoute

fun NavController.navigateToSettingGraph() = navigate(SettingRoute.SettingGraph.name){
    launchSingleTop = true
    popUpTo("home"){inclusive = false}
}
fun NavController.navigateToSetting() = navigate(SettingRoute.SettingRoute.name){
    launchSingleTop = true
    popUpTo("home"){inclusive = false}
}
fun NavController.navigateToAccountInfo() = navigate(SettingRoute.AccountInfoRoute.name)
fun NavController.navigateToAlarmSetting() = navigate(SettingRoute.AlarmSettingRoute.name)
fun NavController.navigateToGetAuthCode() = navigate(SettingRoute.GetAuthCodeRoute.name)
fun NavController.navigateToSMSVerification() = navigate(SettingRoute.SMSVerificationRoute.name)
fun NavController.navigateToAccountDeletionNotice() = navigate(SettingRoute.AccountDeletionNoticeRoute.name)
fun NavController.navigateToDeleteAccountDone() = navigate(SettingRoute.DeleteAccountDoneRoute.name)
fun NavController.navigateToDeleteAccount() = navigate(SettingRoute.DeleteAccountRoute.name)
fun NavController.navigateToLogout() = navigate(SettingRoute.LogoutRoute.name)

fun NavGraphBuilder.settingGraph(
    navHome: ()-> Unit,
    navLogin: () -> Unit,
    navSetting: () -> Unit,
    navAccountInfo: () -> Unit,
    navAlarmSetting: () -> Unit,
    navGetAuthCode: () -> Unit,
    navSmsVerification: () -> Unit,
    navLogout: () -> Unit,
    navDeleteAccount: () -> Unit,
    navAccountDeletionNotice: () -> Unit,
    navBack: () -> Unit
){
    navigation(
        startDestination = SettingRoute.SettingRoute.name,
        route = SettingRoute.SettingGraph.name
    ){
        composable(SettingRoute.SettingRoute.name){
            SettingRoute(
                navAccountInfo = navAccountInfo,
                navAlarmSetting = navAlarmSetting
            )
        }
        composable(SettingRoute.AlarmSettingRoute.name){
            AlarmSettingRoute()
        }
        composable(SettingRoute.GetAuthCodeRoute.name){
            GetAuthCodeRoute(
                navSetting = navSetting,
                navBack = navBack
            )
        }
        composable(SettingRoute.AccountInfoRoute.name){
            AccountInfoRoute(
                navSmsVerification = navSmsVerification,
                navBack = navBack,
                navLogout = navLogout,
                navDeleteAccount = navDeleteAccount
            )
        }
        composable(SettingRoute.SMSVerificationRoute.name){
            SmsVerificationRoute(
                navGetAuthCode = navGetAuthCode,
                navSetting = navSetting,
            )
        }
        composable(SettingRoute.AccountDeletionNoticeRoute.name){
            AccountDeletionNoticeRoute(
                navDeleteAccount = navDeleteAccount,
                navSetting = navSetting
            )
        }
        composable(SettingRoute.DeleteAccountDoneRoute.name){
            DeleteAccountDoneRoute(
                navLogin = navLogin
            )
        }
        composable(SettingRoute.DeleteAccountRoute.name){
            DeleteAccountRoute(
                navHome = navHome,
                navAccountDeletionNotice = navAccountDeletionNotice,
                navBack = navBack
            )
        }
        composable(SettingRoute.LogoutRoute.name){
            LogoutRoute(
                navLogin = navLogin
            )
        }
    }
}