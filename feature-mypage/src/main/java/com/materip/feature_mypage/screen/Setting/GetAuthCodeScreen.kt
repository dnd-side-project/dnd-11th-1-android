package com.materip.feature_mypage.screen.Setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.R
import com.materip.core_model.ui_model.InputKeyboardType
import com.materip.matetrip.component.ConfirmationDialog
import com.materip.matetrip.component.CustomButton
import com.materip.matetrip.component.NormalTopBar
import com.materip.matetrip.component.SelectableDialog
import com.materip.matetrip.component.UnderlinedTextField
import com.materip.matetrip.component.WarningDialog
import com.materip.matetrip.icon.Icons
import com.materip.matetrip.theme.MatetripColor

@Composable
fun GetAuthCodeRoute(){
    GetAuthCodeScreen()
}

@Composable
fun GetAuthCodeScreen(){
    var authCode by remember{ mutableStateOf("") }
    var isEnabled = remember{derivedStateOf{authCode.isNotEmpty()} }
    var retryCode by remember{mutableStateOf(false)}
    var isWarning by remember{mutableStateOf(false)}
    var isDone by remember{mutableStateOf(false)}
    if(retryCode){
        ConfirmationDialog(
            dialogMsg = "입력하신 전화번호로 인증코드를\n재발송 하시겠습니까?",
            onOkClick = { /** 코드 다시 보내기 api */ },
            onDismissRequest = { retryCode = false }
        )
    }
    if (isWarning){
        WarningDialog(onDismissRequest = {isWarning = false})
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
    ) {
        NormalTopBar(
            title = "SMS 인증",
            onBackClick = { /** 뒤로 가기 */ },
            onClick = {/* 미사용 */}
        )
        Spacer(Modifier.height(20.dp))
        if(isDone){
            DoneAuthContent()
        } else {
            AuthProcessContent(
                authCode = authCode,
                onUpdateAuthCode = {authCode = it},
                isEnabled = isEnabled.value
            )
        }
    }
}

@Composable
private fun AuthProcessContent(
    authCode: String,
    onUpdateAuthCode: (String) -> Unit,
    isEnabled: Boolean,
){
    Spacer(Modifier.height(100.dp))
    Text(
        text = "휴대폰 번호를 입력해주세요",
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight(500)
    )
    Spacer(Modifier.height(6.dp))
    Text(
        text = "2차 인증을 위해 필요합니다.",
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight(400),
        color = Color(0x99000000)
    )
    Spacer(Modifier.height(26.dp))
    UnderlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = authCode,
        onValueChange = onUpdateAuthCode,
        placeholder = "인증번호 입력",
        fontSize = 28.sp,
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontWeight = FontWeight(400),
        textColor = Color(0x26000000),
        underlineColor = Color.Black,
        inputType = InputKeyboardType.PHONE
    )
    Spacer(Modifier.height(20.dp))
    CustomButton(
        shape = RoundedCornerShape(size = 10.dp),
        btnText = "확인",
        fontSize = 14.sp,
        textColor = Color.White,
        btnColor = if(isEnabled) Color.Black else MatetripColor.loading_color,
        isEnabled = isEnabled,
        onClick = { /** 인증 번호 확인 */ }
    )
    Spacer(Modifier.height(20.dp))
    Text(
        modifier = Modifier.clickable{/** 인증 번호 재발송 */},
        text = "인증번호 재발송",
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontWeight = FontWeight(400),
        color = Color(0x99000000)
    )
}

@Composable
private fun DoneAuthContent(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(Modifier.height(20.dp))
        Icon(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape),
            painter = painterResource(Icons.check_icon),
            contentDescription = "Done Icon"
        )
        Text(
            text = "SMS 2차 인증이 완료되었습니다.",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
            fontWeight = FontWeight(500)
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = "메이트님의 신뢰도가 한 단계 더 높아졌어요 :>",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
            fontWeight = FontWeight(400),
            color = Color(0x99000000)
        )
        Spacer(Modifier.weight(1f))
        CustomButton(
            shape = RoundedCornerShape(size = 10.dp),
            btnText = "확인",
            fontSize = 14.sp,
            btnColor = Color.White,
            onClick = { /** 뒤로가기 navigation */}
        )
    }
}

@Preview
@Composable
private fun GetAuthCodeUiTest(){
    GetAuthCodeScreen()
}