package com.materip.matetrip.toast

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.materip.core_designsystem.icon.Badges

@Composable
fun CommonToastView(
    message: String
){
    val customToast = CustomToast(LocalContext.current)
    customToast.MakeCommonToastView(message = message, icon = Badges.warning_badge)
}