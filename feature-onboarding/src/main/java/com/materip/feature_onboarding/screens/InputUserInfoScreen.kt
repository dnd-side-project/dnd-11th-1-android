package com.materip.feature_onboarding.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.materip.core_model.ui_model.Gender
import com.materip.core_model.ui_model.InputKeyboardType
import com.materip.core_model.ui_model.UserInfo
import com.materip.matetrip.component.MateTripButton
import com.materip.matetrip.component.OnboardingElevatedCard
import com.materip.matetrip.component.ProgressIndicator
import com.materip.matetrip.component.UnderlinedTextField
import com.materip.matetrip.theme.MateTripTypographySet
import com.materip.matetrip.theme.OnboardingMessage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun InputUserInfoRoute(
    navSelectTripInterest: (userInfo: String) -> Unit,
){
    InputUserInfoScreen(
        onNextClick = { birthYear, gender ->
            val userInfoJson = Json.encodeToString(UserInfo(birthYear, gender.name))
            navSelectTripInterest(userInfoJson)
        }
    )
}

@Composable
fun InputUserInfoScreen(
    onNextClick: (birthYear: String, selectedGender: Gender) -> Unit,
){
    var birthYear by remember{mutableStateOf("")}
    var selectedGender by remember{mutableStateOf<Gender?>(null)}
    var isEnabled = remember{
        derivedStateOf {birthYear.isNotEmpty() && selectedGender != null}
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 30.dp)
            .padding(bottom = 40.dp)
    ){
        Spacer(Modifier.height(10.dp))
        ProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            currentProgress = 0.25f
        )
        Spacer(Modifier.height(10.dp))
        Spacer(Modifier.height(64.dp))
        Text(
            text = OnboardingMessage.MESSAGE_1.title,
            fontSize = MateTripTypographySet.onboardingTitle.fontSize,
            fontFamily = MateTripTypographySet.onboardingTitle.fontFamily,
            fontWeight = MateTripTypographySet.onboardingTitle.fontWeight,
            color = MateTripTypographySet.onboardingTitle.color
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = OnboardingMessage.MESSAGE_1.message,
            fontSize = MateTripTypographySet.onboardingMessage.fontSize,
            fontWeight = MateTripTypographySet.onboardingMessage.fontWeight,
            fontFamily = MateTripTypographySet.onboardingMessage.fontFamily,
            color = MateTripTypographySet.onboardingTitle.color
        )
        Spacer(Modifier.height(34.dp))
        Text(
            text = "출생연도",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(500),
            color = Color.Black
        )
        Spacer(Modifier.height(2.dp))
        UnderlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = birthYear,
            onValueChange = {birthYear = it},
            placeholder = "2024",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.roboto_medium)),
            fontWeight = FontWeight(500),
            textColor = Color.Black,
            underlineColor = Color(0xFF939094), /** gray_06 으로 치환 */
            inputType = InputKeyboardType.NUMBER
        )
        Spacer(Modifier.height(30.dp))
        Text(
            text = "성별",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(500),
            color = Color.Black
        )
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
        ){
            OnboardingElevatedCard(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                text = "남성",
                icon = if(selectedGender == Gender.MALE) com.materip.core_designsystem.R.drawable.ic_app_logo_male_active else com.materip.core_designsystem.R.drawable.ic_app_logo_male_inactive,
                isSelected = selectedGender == Gender.MALE,
                onClick = {selectedGender = Gender.MALE}
            )
            Spacer(Modifier.width(10.dp))
            OnboardingElevatedCard(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                text = "여성",
                icon = if(selectedGender == Gender.FEMALE) com.materip.core_designsystem.R.drawable.ic_app_logo_male_active else com.materip.core_designsystem.R.drawable.ic_app_logo_female_inactive,
                isSelected = selectedGender == Gender.FEMALE,
                onClick = {selectedGender = Gender.FEMALE}
            )
        }
        Spacer(Modifier.weight(1f))
        MateTripButton(
            onClick = { onNextClick(birthYear, selectedGender!!) },
            buttonText = "다음",
            enabled = isEnabled.value
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InputUserInfoUiTest(){
    InputUserInfoScreen(onNextClick = {birthYear, selectedGender ->
        
    })
}