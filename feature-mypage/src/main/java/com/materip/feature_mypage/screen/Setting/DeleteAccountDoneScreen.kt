package com.materip.feature_mypage.screen.Setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.component.MateTripButton
import com.materip.core_designsystem.theme.customFontFamily

@Composable
fun DeleteAccountDoneRoute(navLogin: () -> Unit){
    DeleteAccountDoneScreen(navLogin = navLogin)
}

@Composable
fun DeleteAccountDoneScreen(navLogin: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp)
            .padding(horizontal = 20.dp)
    ){
        Spacer(Modifier.weight(1f))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(com.materip.core_designsystem.R.drawable.ic_app_main_logo),
                contentDescription = "App Main Logo"
            )
            Spacer(Modifier.height(30.dp))
            Text(
                text = "메이트립 탈퇴가 완료되었습니다.",
                fontSize = 18.sp,
                fontFamily = customFontFamily.notoSansKr,
                fontWeight = FontWeight(500),
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "메이트립을 이용해 주셔서 감사합니다.",
                fontSize = 14.sp,
                fontFamily = customFontFamily.notoSansKr,
                fontWeight = FontWeight(400),
            )
        }
        Spacer(Modifier.weight(2f))
        MateTripButton(
            modifier = Modifier.fillMaxWidth().height(54.dp),
            onClick = navLogin,
            buttonText = "확인",
            enabled = true,
        )
    }
}

@Preview
@Composable
fun DeleteAccountDoneUITest(){
    DeleteAccountDoneScreen(navLogin = {})
}