package com.materip.feature_mypage.screen.Setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.component.MateTripButton
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.R

@Composable
fun LogoutRoute(navLogin: () -> Unit){ LogoutScreen(navLogin = navLogin) }

@Composable
fun LogoutScreen(
    navLogin: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(bottom = 60.dp, top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Icon(
            modifier = Modifier.size(54.dp),
            painter = painterResource(Icons.check_icon),
            contentDescription = "Logout Check Button"
        )
        Spacer(Modifier.height(30.dp))
        Text(
            text = "로그아웃이 완료되었습니다.",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
            fontWeight = FontWeight(500),
        )
        Spacer(Modifier.height(12.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "다시 서비스를 이용 하시려면 카카오 로그인을 이용해 주세요",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
            fontWeight = FontWeight(400),
            color = Color(0x99000000)
        )
        Spacer(Modifier.weight(1f))
        MateTripButton(
            modifier = Modifier.fillMaxWidth().height(54.dp),
            enabled = true,
            onClick = { navLogin() },
            buttonText = "확인"
        )
    }
}

@Composable
@Preview
private fun LogoutUITest(){
    LogoutScreen(navLogin = {})
}