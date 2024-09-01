package com.materip.feature_mypage.screen.Setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.component.MateTripButton
import com.materip.core_designsystem.component.NormalTopBar
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.core_designsystem.theme.customFontFamily
import com.materip.feature_mypage.R

@Composable
fun DeleteAccountRoute(
    navHome: () -> Unit,
    navAccountDeletionNotice: ()-> Unit,
    navBack: () -> Unit,
){
    DeleteAccountScreen(
        navHome = navHome,
        navAccountDeletionNotice = navAccountDeletionNotice,
        navBack = navBack,
    )
}

@Composable
fun DeleteAccountScreen(
    navHome: () -> Unit,
    navAccountDeletionNotice: () -> Unit,
    navBack: () -> Unit,
){
    var selectedOptionIdx by remember{mutableStateOf<Int?>(null)}
    var optionDetail by remember{mutableStateOf("")}
    var isGoneEnabled = remember{ derivedStateOf {
        (selectedOptionIdx != null && selectedOptionIdx != 5) || (selectedOptionIdx == 5 && optionDetail.isNotEmpty())
    }}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(bottom = 40.dp)
    ){
        NormalTopBar(
            title = "탈퇴하기",
            titleFontWeight = FontWeight(700),
            onBackClick = navBack,
            onClick = {/* 미사용 */}
        )
        Spacer(Modifier.height(20.dp))
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.End
            ){
                Image(
                    modifier = Modifier.offset(x = 95.dp),
                    painter = painterResource(com.materip.core_designsystem.R.drawable.ic_background_logo_with_delete_account),
                    contentDescription = "Background Logo"
                )
            }
            Column(
                modifier = Modifier.fillMaxSize()
            ){
                Text(
                    text = "메이트님과 동행할 수 없다니\n너무 아쉬워요...",
                    fontSize = 18.sp,
                    fontFamily = customFontFamily.notoSansKr,
                    fontWeight = FontWeight(700),
                    color = MateTripColors.Gray_11
                )
                Spacer(Modifier.height(11.dp))
                Text(
                    text = "탈퇴하시려는 이유를 알려주세요.",
                    fontSize = 14.sp,
                    fontFamily = customFontFamily.notoSansKr,
                    fontWeight = FontWeight(400),
                    color = MateTripColors.Gray_07
                )
                Spacer(Modifier.height(26.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .clickable { selectedOptionIdx = 1 },
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(if(selectedOptionIdx == 1) Icons.selected_radio_btn else Icons.not_selected_radio_btn),
                        contentDescription = "Option 1",
                        tint = if(selectedOptionIdx == 1) MateTripColors.Primary else MateTripColors.Blue_02
                    )
                    Spacer(Modifier.width(15.dp))
                    Text(
                        text = "원하는 메이트가 없어요",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = customFontFamily.notoSansKr,
                            fontWeight = FontWeight(500),
                            color = MateTripColors.Gray_10,
                            textAlign = TextAlign.Justify,
                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .clickable { selectedOptionIdx = 2 },
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(if(selectedOptionIdx == 2) Icons.selected_radio_btn else Icons.not_selected_radio_btn),
                        contentDescription = "Option 2",
                        tint = if(selectedOptionIdx == 2) MateTripColors.Primary else MateTripColors.Blue_02   )
                    Spacer(Modifier.width(15.dp))
                    Text(
                        text = "비매너 메이트를 만났어요",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = customFontFamily.notoSansKr,
                            fontWeight = FontWeight(500),
                            color = MateTripColors.Gray_10,
                            textAlign = TextAlign.Justify,
                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .clickable { selectedOptionIdx = 3 },
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(if(selectedOptionIdx == 3) Icons.selected_radio_btn else Icons.not_selected_radio_btn),
                        contentDescription = "Option 3",
                        tint = if(selectedOptionIdx == 3) MateTripColors.Primary else MateTripColors.Blue_02  )
                    Spacer(Modifier.width(15.dp))
                    Text(
                        text = "원하는 기능이 없거나 이용이 불편해요",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = customFontFamily.notoSansKr,
                            fontWeight = FontWeight(500),
                            color = MateTripColors.Gray_10,
                            textAlign = TextAlign.Justify,
                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .clickable { selectedOptionIdx = 4 },
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(if(selectedOptionIdx == 4) Icons.selected_radio_btn else Icons.not_selected_radio_btn),
                        contentDescription = "Option 4",
                        tint = if(selectedOptionIdx == 4) MateTripColors.Primary else MateTripColors.Blue_02   )
                    Spacer(Modifier.width(15.dp))
                    Text(
                        text = "앱을 잘 사용하지 않아요",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = customFontFamily.notoSansKr,
                            fontWeight = FontWeight(500),
                            color = MateTripColors.Gray_10,
                            textAlign = TextAlign.Justify,
                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .clickable { selectedOptionIdx = 5 },
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(if(selectedOptionIdx == 5) Icons.selected_radio_btn else Icons.not_selected_radio_btn),
                        contentDescription = "Option 5",
                        tint = if(selectedOptionIdx == 5) MateTripColors.Primary else MateTripColors.Blue_02
                    )
                    Spacer(Modifier.width(15.dp))
                    Text(
                        text = "기타",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = customFontFamily.notoSansKr,
                            fontWeight = FontWeight(500),
                            color = MateTripColors.Gray_10,
                            textAlign = TextAlign.Justify,
                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                        )
                    )
                }
                Spacer(Modifier.height(10.dp))
                if(selectedOptionIdx == 5){
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        value = optionDetail,
                        onValueChange = {if(optionDetail.length <= 112){optionDetail = it}},
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MateTripColors.Blue_03,
                            unfocusedBorderColor = MateTripColors.Blue_03
                        ),
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = customFontFamily.notoSansKr,
                            fontWeight = FontWeight(400),
                            color = MateTripColors.Gray_05
                        ),
                        placeholder = {
                            Text(
                                text = "내용을 입력해주세요. (110자)",
                                fontSize = 14.sp,
                                fontFamily = customFontFamily.notoSansKr,
                                fontWeight = FontWeight(400),
                                color = MateTripColors.Gray_05
                            )
                        },
                        shape = RoundedCornerShape(size = 10.dp)
                    )
                } else {
                    Spacer(Modifier.weight(1f))
                }
                Spacer(Modifier.height(48.dp))
                MateTripButton(
                    modifier = Modifier.fillMaxWidth()
                        .height(54.dp),
                    onClick = navHome,
                    enabled = true,
                    buttonText = "계속 이용하기"
                )
                Spacer(Modifier.height(13.dp))
                MateTripButton(
                    modifier = Modifier.fillMaxWidth()
                        .height(54.dp),
                    onClick = navAccountDeletionNotice,
                    enabled = isGoneEnabled.value,
                    buttonText = "떠날래요"
                )
            }
        }
    }
}

@Composable
@Preview
private fun DeleteAccountUITest(){
    DeleteAccountScreen(
        navHome = {},
        navAccountDeletionNotice = {},
        navBack = {}
    )
}