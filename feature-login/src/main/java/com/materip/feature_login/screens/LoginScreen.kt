package com.materip.feature_login.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.materip.core_common.ErrorState
import com.materip.core_designsystem.R
import com.materip.core_designsystem.component.KakaoButton
import com.materip.feature_login.view_models.LoginViewModel

@Composable
fun LoginRoute(
    navOnBoarding: () -> Unit,
    navHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val errState = viewModel.errorState.collectAsStateWithLifecycle()

    LoginScreen(
        isLogin = viewModel.isLogin.collectAsStateWithLifecycle().value,
        isOnboardingCompleted = viewModel.isOnboardingCompleted.collectAsStateWithLifecycle().value,
        errState = errState.value,
        doLogin = {viewModel.doLogin(context)},
        navOnBoarding = navOnBoarding,
        navHome = navHome
    )
}

@Composable
fun LoginScreen(
    isLogin: Boolean,
    isOnboardingCompleted: Boolean,
    errState: ErrorState,
    doLogin: () -> Unit,
    navOnBoarding: () -> Unit,
    navHome: () -> Unit,
){
    LaunchedEffect(isLogin, isOnboardingCompleted){
        if (isLogin){
            if(isOnboardingCompleted){
                navHome()
            } else {
                navOnBoarding()
            }
        }
    }
    if (errState is ErrorState.NoAuthError && errState.generalError.first){
        /** Error 발생 >> Error 관련 dialog 사용하면 좋을 듯 */
        Text(
            text = errState.generalError.second!!
        )
    } else {
        LoginMainContent(doLogin = doLogin)
    }
}
@Composable
private fun LoginMainContent(
    doLogin: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp, vertical = 60.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(Modifier.height(54.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = "MATE",
                fontSize = 36.sp,
                fontFamily = FontFamily(Font(R.font.helvetica_black)),
                fontWeight = FontWeight(900)
            )
            Spacer(Modifier.width(8.dp))
            Image(
                painter = painterResource(R.drawable.ic_app_main_logo),
                contentDescription = "APP ICON" 
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "TRIP",
                fontSize = 36.sp,
                fontFamily = FontFamily(Font(R.font.helvetica_black)),
                fontWeight = FontWeight(900)
            )
        }
        KakaoButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            onClick = doLogin
        )
    }
}
@Preview
@Composable
private fun LoginUITest(){
    LoginScreen(
        doLogin = {},
        errState = ErrorState.Loading,
        isLogin = false,
        isOnboardingCompleted = false,
        navOnBoarding = {},
        navHome = {}
    )
}