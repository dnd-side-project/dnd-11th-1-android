package com.materip.feature_mypage.screen.Setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.materip.feature_mypage.R
import com.materip.matetrip.icon.Icons
import com.materip.matetrip.theme.MatetripColor

@Composable
fun SettingRoute(){
    SettingScreen()
}

@Composable
fun SettingScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "설정",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(900)
            )
        }
        Spacer(Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(Icons.my_page_icon),
                contentDescription = "Account Info IC",
                tint = MatetripColor.Primary
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = "계정 정보",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(500)
            )
            Spacer(Modifier.weight(1f))
            IconButton(
                modifier= Modifier.size(18.dp),
                onClick = { /** 계정 정보로 navigation */ }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(Icons.navigate_next_icon),
                    contentDescription = "Next Navigation"
                )
            }
        }
        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(Icons.notification_icon),
                contentDescription = "Alarm Setting IC",
                tint = MatetripColor.Primary
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = "알림 설정",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(500)
            )
            Spacer(Modifier.weight(1f))
            IconButton(
                modifier= Modifier.size(18.dp),
                onClick = { /** 알림 설정으로 navigation */ }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(Icons.navigate_next_icon),
                    contentDescription = "Next Navigation"
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingUITest(){
    SettingScreen()
}