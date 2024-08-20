package com.materip.feature_mypage.screen.Setting

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.component.NormalTopBar
import com.materip.core_designsystem.component.OnAndOffButton
import com.materip.core_designsystem.theme.MateTripColors

@Composable
fun AlarmSettingRoute(){
    AlarmSettingScreen()
}

@Composable
fun AlarmSettingScreen(){
    var isReceiveApplication by remember{mutableStateOf(false)}
    var isAcceptApplication by remember{mutableStateOf(false)}
    var isAcceptReview by remember{mutableStateOf(false)}
    var isWriteReview by remember{mutableStateOf(false)}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
    ){
        NormalTopBar(
            title = "알림 설정",
            titleFontWeight = FontWeight(700),
            onBackClick = { /** 뒤로가기 navigation */ },
            onClick = {}
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text = "동행 신청",
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(400),
            color = MateTripColors.Gray_11
        )
        Spacer(Modifier.height(10.dp))
        ReceiveApplication(
            isClicked = isReceiveApplication,
            onClick = {isReceiveApplication = !isReceiveApplication}
        )
        Spacer(Modifier.height(24.dp))
        AcceptApplication(
            isClicked = isAcceptApplication,
            onClick = {isAcceptApplication = !isAcceptApplication}
        )
        Spacer(Modifier.height(40.dp))
        Text(
            text = "동행 후기",
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(400),
            color = MateTripColors.Gray_11
        )
        Spacer(Modifier.height(10.dp))
        ReceiveReview(
            isClicked = isAcceptReview,
            onClick = {isAcceptReview = !isAcceptReview}
        )
        Spacer(Modifier.height(24.dp))
        WriteReview(
            isClicked = isWriteReview,
            onClick = {isWriteReview = !isWriteReview}
        )
    }
}

@Composable
private fun ReceiveApplication(
    isClicked: Boolean,
    onClick: () -> Unit,
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column{
            Text(
                text = "동행 신청 수신",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(400),
                color = MateTripColors.Gray_11
            )
            Text(
                text = "동행 신청을 받을 때 카카오 앱 푸시 알림",
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(400),
                color = MateTripColors.Gray_06

            )
        }
        OnAndOffButton(
            modifier = Modifier.width(56.dp).height(32.dp),
            isClicked = isClicked,
            onClick = onClick
        )
    }
}

@Composable
private fun AcceptApplication(
    isClicked: Boolean,
    onClick: () -> Unit,
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column{
            Text(
                text = "동행 신청 수신",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(400),
                color = MateTripColors.Gray_11
            )
            Text(
                text = "동행 신청을 받을 때 카카오 앱 푸시 알림",
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(400),
                color = MateTripColors.Gray_06

            )
        }
        OnAndOffButton(
            modifier = Modifier.width(56.dp).height(32.dp),
            isClicked = isClicked,
            onClick = onClick
        )
    }
}

@Composable
private fun ReceiveReview(
    isClicked: Boolean,
    onClick: () -> Unit,
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column{
            Text(
                text = "동행 신청 수신",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(400),
                color = MateTripColors.Gray_11
            )
            Text(
                text = "동행 신청을 받을 때 카카오 앱 푸시 알림",
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(400),
                color = MateTripColors.Gray_06

            )
        }
        OnAndOffButton(
            modifier = Modifier.width(56.dp).height(32.dp),
            isClicked = isClicked,
            onClick = onClick
        )
    }
}

@Composable
private fun WriteReview(
    isClicked: Boolean,
    onClick: () -> Unit,
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column{
            Text(
                text = "동행 신청 수신",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(400),
                color = MateTripColors.Gray_11
            )
            Text(
                text = "동행 신청을 받을 때 카카오 앱 푸시 알림",
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(400),
                color = MateTripColors.Gray_06
            )
        }
        OnAndOffButton(
            modifier = Modifier.width(56.dp).height(32.dp),
            isClicked = isClicked,
            onClick = onClick
        )
    }
}


@Preview
@Composable
private fun AlarmSettingUITest(){
    AlarmSettingScreen()
}