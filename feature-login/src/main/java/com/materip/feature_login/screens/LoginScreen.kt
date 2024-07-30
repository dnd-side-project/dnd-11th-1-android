package com.materip.feature_login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.CustomButton

@Composable
fun LoginRoute(){
    LoginScreen()
}

@Composable
fun LoginScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        CustomButton(
            containerColor = Color.Yellow,
            shape = RoundedCornerShape(size = 10.dp),
            text = "카카오 로그인",
            textColor = Color.Black,
            fontSize = 16.sp,
            isEnabled = true,
            onClick = {/** 로그인 */}
        )
    }
}

@Preview
@Composable
private fun UITest(){
    LoginScreen()
}