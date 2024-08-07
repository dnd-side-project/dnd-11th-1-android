package com.materip.feature_login.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.materip.feature_login.view_models.TestViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun ScreenTest(
//    viewModel: TestViewModel = hiltViewModel()
){
//    val accessToken = viewModel.accessToken.collectAsStateWithLifecycle()
//    val refreshToken = viewModel.refreshToken.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Login Success"
        )
//        Text(
//            text = "token : ${accessToken.value} / refresh ${refreshToken.value}"
//        )
    }
}