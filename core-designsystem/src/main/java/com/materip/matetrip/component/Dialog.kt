package com.materip.core_designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.materip.core_designsystem.MatetripGrade
import com.materip.core_designsystem.R
import com.materip.core_designsystem.icon.Badges
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.core_model.ui_model.Grade
import com.materip.core_model.ui_model.GradeTag
import kotlinx.coroutines.delay

@Composable
fun SelectableDialog(
    value: String?,
    onValueChange: (String) -> Unit,
    options: List<String>,
    onDismissRequest: () -> Unit,
){
    val configuration = LocalConfiguration.current
    val dialogWidth = configuration.screenWidthDp.dp - 40.dp
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        LazyColumn(
            modifier = Modifier
                .width(dialogWidth)
                .heightIn(max = 405.dp)
                .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
        ){
            items(options){option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .padding(start = 10.dp, end = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = option,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                        fontWeight = FontWeight(400),
                        color = MateTripColors.Gray_11
                    )
                    IconButton(
                        modifier = Modifier.size(18.dp),
                        onClick = {
                            if(options.indexOf(option) != 0){
                                onValueChange(option)
                                onDismissRequest()
                            }
                        }
                    ) {
                        Image(
                            painter = painterResource(if(value == option) Icons.selected_radio_btn else Icons.not_selected_radio_btn),
                            contentDescription = "Radio Button"
                        )
                    }
                }
                if(options.indexOf(option) != options.lastIndex){HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = MateTripColors.divider_color)}
            }
        }
    }
}

@Composable
fun LevelInfoDialog(
    currentLevel: Grade,
    onDismissRequest: () -> Unit,
){
    val nextLevelInfo = when(currentLevel.gradeName){
        GradeTag.ROOKIE.name -> MatetripGrade.level_2
        GradeTag.ELITE.name -> MatetripGrade.level_3
        else -> MatetripGrade.level_4
    }
    val nextLevelText = buildAnnotatedString {
        if(currentLevel.gradeName != GradeTag.VETERAN.name) {
            withStyle(style = SpanStyle(color = nextLevelInfo.color as Color)){
                append(nextLevelInfo.gradeKoName)
            }
            withStyle(style = SpanStyle(color = Color.Black)){
                append("까지 한 발자국 남았어요")
            }
        } else {
            withStyle(style = SpanStyle(color = Color.Black)){
                append("메이트님은 저희가 선정한\n")
            }
            withStyle(style = SpanStyle(color = MateTripColors.level_4_color)){
                append("가장 신뢰도 높은 메이트")
            }
            withStyle(style = SpanStyle(color = Color.Black)){
                append("에요!")
            }
        }
    }
    val nextLevelDescText = buildAnnotatedString {
        if (currentLevel.gradeName != GradeTag.VETERAN.name){
            withStyle(style = SpanStyle(color = MateTripColors.Primary)){
                append("동행 후기")
            }
            withStyle(style = SpanStyle(color = MateTripColors.Gray_06)){
                append("를 작성하고 다음 레벨로 진급해서\n더욱")
            }
            withStyle(style = SpanStyle(color = MateTripColors.Primary)){
                append("신뢰도 있는 메이트")
            }
            withStyle(style = SpanStyle(color = MateTripColors.Gray_06)){
                append("가 되어 보세요 :)")
            }
        } else {
            withStyle(style = SpanStyle(color = MateTripColors.Gray_06)){
                append("자부심을 가져도 좋아요 :)")
            }
        }
    }
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .heightIn(400.dp)
                .width(284.dp)
                .background(color = Color.White, shape = RoundedCornerShape(size = 10.dp))
                .padding(horizontal = 14.dp)
                .padding(top = 14.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(Badges.question_badge),
                    contentDescription = "Question Mark"
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "메이트 레벨",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                        fontWeight = FontWeight(500),
                        color = MateTripColors.Gray_08,
                        textAlign = TextAlign.Justify,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
            }
            Spacer(Modifier.height(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(
                    modifier = Modifier.size(38.dp),
                    painter = painterResource(currentLevel.badge),
                    contentDescription = "Level Badge"
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = currentLevel.gradeKoName,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500),
                    color = currentLevel.color as Color
                )
            }
            Spacer(Modifier.height(20.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = nextLevelText,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                fontWeight = FontWeight(700),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = nextLevelDescText,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                fontWeight = FontWeight(500),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(34.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ){
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Badges.level_1_badge),
                        contentDescription = "Level 1 Badge"
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(
                        modifier = Modifier.fillMaxHeight()
                    ){
                        Text(
                            text = "새싹 메이트",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                            fontWeight = FontWeight(500),
                            color = MateTripColors.level_1_color
                        )
                        Text(
                            text = "일반 회원",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                            fontWeight = FontWeight(400),
                            color = MateTripColors.Gray_07
                        )
                    }
                }
                VerticalDivider(Modifier.fillMaxHeight(), color = MateTripColors.divider_color)
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Badges.level_2_badge),
                        contentDescription = "Level 1 Badge"
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(
                        modifier = Modifier.fillMaxHeight()
                    ){
                        Text(
                            text = "우수 메이트",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                            fontWeight = FontWeight(500),
                            color = MateTripColors.level_2_color
                        )
                        Text(
                            text = "후기 1개 이상",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                            fontWeight = FontWeight(400),
                            color = MateTripColors.Gray_07
                        )
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                HorizontalDivider(Modifier.weight(1f), color = MateTripColors.divider_color)
                Spacer(Modifier.width(32.dp))
                HorizontalDivider(Modifier.weight(1f), color = MateTripColors.divider_color)
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ){
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Badges.level_3_badge),
                        contentDescription = "Level 3 Badge"
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(
                        modifier = Modifier.fillMaxHeight()
                    ){
                        Text(
                            text = "열정 메이트",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                            fontWeight = FontWeight(500),
                            color = MateTripColors.level_3_color
                        )
                        Text(
                            text = "후기 3개 이상",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                            fontWeight = FontWeight(400),
                            color = MateTripColors.Gray_07
                        )
                    }
                }
                VerticalDivider(Modifier.fillMaxHeight(), color = MateTripColors.divider_color)
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Badges.level_4_badge),
                        contentDescription = "Level 4 Badge"
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(
                        modifier = Modifier.fillMaxHeight()
                    ){
                        Text(
                            text = "베테랑 메이트",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                            fontWeight = FontWeight(500),
                            color = MateTripColors.level_4_color
                        )
                        Text(
                            text = "후기 5개 이상",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                            fontWeight = FontWeight(400),
                            color = MateTripColors.Gray_07
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ConfirmationDialog(
    dialogMsg: String,
    onOkClick: () -> Unit,
    onDismissRequest: () -> Unit
){
    val configuration = LocalConfiguration.current
    val dialogWidth = configuration.screenWidthDp.dp - 40.dp
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .width(dialogWidth)
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                .padding(top = 30.dp, bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 33.dp),
                text = dialogMsg,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                fontWeight = FontWeight(500)
            )
            Spacer(Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(horizontal = 12.dp)
            ){
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = MateTripColors.divider_color,
                    ),
                    shape = RoundedCornerShape(size = 10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = "아니오",
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                            fontWeight = FontWeight(500),
                            color = MateTripColors.no_text_color,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(Modifier.width(10.dp))
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    onClick = {
                        onOkClick()
                        onDismissRequest()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = Color.Black,
                    ),
                    shape = RoundedCornerShape(size = 10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = "예",
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                            fontWeight = FontWeight(500),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WarningDialog(
    onDismissRequest: () -> Unit
){
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MateTripColors.Red_01)
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center
        ){
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape),
                painter = painterResource(Badges.warning_badge),
                contentDescription = "Warning Icon"
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = "인증코드 인증에 실패했습니다.\n인증코드를 다시 한 번 확인해 주세요.",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                fontWeight = FontWeight(400),
                color = Color.White
            )
        }
    }
    LaunchedEffect(Unit){
        delay(1500)
        onDismissRequest()
    }
}

@Preview
@Composable
private fun DialogUITest(){
    var isOpen by remember{mutableStateOf(false)}
    val yearOptions  = (1950..2024).toMutableList().map{it.toString()}.toMutableList().apply{
        this.add(0, "출생연도 선택")
    }.toList()
    val options = listOf("성별 선택", "남성", "여성")
    var value by remember{mutableStateOf(options[0])}
    Button(
        onClick = {isOpen = !isOpen}
    ){
        Text(value)
    }
    if(isOpen){
//        SelectableDialog(
//            value = value,
//            onValueChange = {value = it},
//            options = options,
//            onDismissRequest = {isOpen = false}
//        )
        ConfirmationDialog(
            dialogMsg = "정말 로그아웃 하시나요?",
            onOkClick = { isOpen = false },
            onDismissRequest = {isOpen = false}
        )
    }
}