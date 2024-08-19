package com.materip.feature_mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.materip.feature_mypage.screen.Setting.AccountInfoRoute
import com.materip.feature_mypage.screen.Setting.AlarmSettingRoute
import com.materip.feature_mypage.screen.Setting.GetAuthCodeRoute
import com.materip.feature_mypage.screen.Setting.SettingRoute
import com.materip.feature_mypage.screen.Setting.SmsVerificationRoute

fun NavController.navigateToSettingGraph() = navigate(SettingRoute.SettingGraph.name)
fun NavController.navigateToSetting() = navigate(SettingRoute.SettingScreen.name)
fun NavController.navigateToAccountInfo() = navigate(SettingRoute.AccountInfoScreen.name)
fun NavController.navigateToAlarmSetting() = navigate(SettingRoute.AlarmSettingScreen.name)
fun NavController.navigateToGetAuthCode() = navigate(SettingRoute.GetAuthCodeScreen.name)
fun NavController.navigateToSMSVerification() = navigate(SettingRoute.SMSVerificationScreen.name)

fun NavGraphBuilder.setting(){
    navigation(
        startDestination = SettingRoute.SettingScreen.name,
        route = SettingRoute.SettingGraph.name
    ){
        composable(SettingRoute.SettingScreen.name){
            SettingRoute()
        }
        composable(SettingRoute.AlarmSettingScreen.name){
            AlarmSettingRoute()
        }
        composable(SettingRoute.GetAuthCodeScreen.name){
            GetAuthCodeRoute()
        }
        composable(SettingRoute.AccountInfoScreen.name){
            AccountInfoRoute()
        }
        composable(SettingRoute.SMSVerificationScreen.name){
            SmsVerificationRoute()
        }
    }
}