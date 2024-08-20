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
fun NavController.navigateToSetting() = navigate(SettingRoute.SettingRoute.name)
fun NavController.navigateToAccountInfo() = navigate(SettingRoute.AccountInfoRoute.name)
fun NavController.navigateToAlarmSetting() = navigate(SettingRoute.AlarmSettingRoute.name)
fun NavController.navigateToGetAuthCode() = navigate(SettingRoute.GetAuthCodeRoute.name)
fun NavController.navigateToSMSVerification() = navigate(SettingRoute.SMSVerificationRoute.name)

fun NavGraphBuilder.setting(){
    navigation(
        startDestination = SettingRoute.SettingRoute.name,
        route = SettingRoute.SettingGraph.name
    ){
        composable(SettingRoute.SettingRoute.name){
            SettingRoute()
        }
        composable(SettingRoute.AlarmSettingRoute.name){
            AlarmSettingRoute()
        }
        composable(SettingRoute.GetAuthCodeRoute.name){
            GetAuthCodeRoute()
        }
        composable(SettingRoute.AccountInfoRoute.name){
            AccountInfoRoute()
        }
        composable(SettingRoute.SMSVerificationRoute.name){
            SmsVerificationRoute()
        }
    }
}