package com.materip.feature_mypage.screen.Setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import com.materip.core_designsystem.component.CustomButton
import com.materip.core_designsystem.component.NormalTopBar
import com.materip.core_designsystem.component.UnderlinedTextField
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.core_model.ui_model.InputKeyboardType

@Composable
fun SmsVerificationRoute(
    navGetAuthCode: () -> Unit,
    navSetting: () -> Unit
){
    SmsVerificationScreen(
        navGetAuthCode = navGetAuthCode,
        navSetting = navSetting
    )
}

@Composable
fun SmsVerificationScreen(
    navGetAuthCode: () -> Unit,
    navSetting: () -> Unit
){
    var phoneNumber by remember{mutableStateOf("")}
    var isEnabled = remember{derivedStateOf{phoneNumber.isNotEmpty()}}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
    ) {
        NormalTopBar(
            title = "SMS 인증",
            onBackClick = navSetting,
            onClick = {/* 미사용 */}
        )
        Spacer(Modifier.height(120.dp))
        Text(
            text = "휴대폰 번호를 입력해주세요",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(500)
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = "2차 인증을 위해 필요합니다.",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(400),
            color = Color(0x99000000)
        )
        Spacer(Modifier.height(26.dp))
        UnderlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = phoneNumber,
            onValueChange = {phoneNumber = it},
            placeholder = "010-1234-5678",
            fontSize = 28.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.roboto_medium)),
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
            btnColor = if(isEnabled.value) Color.Black else MateTripColors.loading_color,
            isEnabled = isEnabled.value,
            onClick = {
                /** SMS 인증 신청 api */
                navGetAuthCode()
            }
        )
    }
}

@Preview
@Composable
private fun SmsVerificationUITest(){
    SmsVerificationScreen(
        navSetting = {},
        navGetAuthCode = {}
    )
}