package com.materip.matetrip.toast

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.materip.core_common.ErrorState
import com.materip.core_designsystem.icon.Badges
import kotlinx.coroutines.delay

@Composable
fun ErrorView(
    errState: ErrorState,
    navBack: () -> Unit,
){
    val message = when(errState){
        is ErrorState.AuthError -> {
            if (errState.invalidTokenError){
                "인증되지 않은 사용자입니다."
            } else if (errState.notFoundTokenError){
                "존재하지 않는 사용자입니다."
            } else {
                errState.generalError.second!!
            }
        }
        is ErrorState.NoAuthError -> errState.generalError.second!!
        else -> "오류가 아닙니다"
    }

    val customToast = CustomToast(LocalContext.current)
    customToast.MakeErrorText(message = message, icon = Badges.warning_badge)

    LaunchedEffect(Unit){
        delay(2_000)
        navBack()
    }
}