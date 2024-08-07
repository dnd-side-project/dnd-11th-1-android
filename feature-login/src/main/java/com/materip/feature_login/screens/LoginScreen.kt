package com.materip.feature_login

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.materip.core_common.ErrorState
import com.materip.feature_login.view_models.LoginViewModel
import com.materip.matetrip.component.KakaoButton

@Composable
fun LoginRoute(
    navOnBoarding: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val errState = viewModel.errorState.collectAsStateWithLifecycle()

    LoginScreen(
        isLogin = viewModel.isLogin.collectAsStateWithLifecycle().value,
        errState = errState.value,
        doLogin = {viewModel.doLogin(context)},
        navOnBoarding = navOnBoarding
    )
}

@Composable
fun LoginScreen(
    isLogin: Boolean,
    errState: ErrorState,
    doLogin: () -> Unit,
    navOnBoarding: () -> Unit,
){
    if (errState is ErrorState.NoAuthError && errState.generalError.first){
        /** Error 발생 >> Error 관련 dialog 사용하면 좋을 듯 */
        Text(
            text = errState.generalError.second!!
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(horizontal = 35.dp, vertical = 60.dp),
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
                    fontSize = 36.sp
                )
                Image(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "APP ICON" /** app icon으로 대체 */
                )
                Text(
                    text = "TRIP",
                    fontSize = 36.sp
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
    LaunchedEffect(isLogin){
        if (isLogin){
            navOnBoarding()
        }
    }
}

@Preview
@Composable
private fun UITest(){
    LoginScreen(
        doLogin = {},
        errState = ErrorState.Loading,
        isLogin = false,
        navOnBoarding = {}
    )
}