package com.materip.feature_mypage.screen.MyPage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_model.ui_model.SendApplicationClass
import com.materip.matetrip.component.CircleImageView
import com.materip.matetrip.component.ConfirmationDialog
import com.materip.matetrip.component.CustomButton
import com.materip.matetrip.component.NormalTag
import com.materip.matetrip.component.NormalTopBar
import com.materip.matetrip.icon.Icons
import com.materip.matetrip.theme.MatetripColor

@Composable
fun SendApplicationRoute(
    navBack: () -> Unit,
    navProfileDescription: () -> Unit
){
    SendApplicationScreen(
        navBack = navBack,
        navProfileDescription = navProfileDescription
    )
}

@Composable
fun SendApplicationScreen(
    navBack: () -> Unit,
    navProfileDescription: () -> Unit
){
    val scrollState = rememberScrollState()
    val dummyData = SendApplicationClass(
        nickname = "닉네임",
        age = "20대 초반",
        gender = "여자",
        tags = listOf("맛집탐방","인생샷","액티비티"),
        profileUrl = "",
        application = "안녕하세요! 저는 000이에요 저는 ~~~하는 사람이고 이런 여행 스타일을 가지고 있어요.\n\n음식취향은 해산물을 좋아해서 같이 여행 가게 된다면 해산물 먹으러 가고 싶어요!",
        myNickname = "동행자",
        snsLink = "http://www.kakaotalk"
    )
    val alterText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Black)){
            append("동행 수락 후 카카오톡 오픈채팅")
        }
        withStyle(style = SpanStyle(color = MatetripColor.gray_06)){
            append("에서 대화해 보세요!\n상대방의 동의 없는 링크 공유는")
        }
        withStyle(style = SpanStyle(color = Color.Black)){
            append("서비스 이용 제재의 사유")
        }
        withStyle(style = SpanStyle(color = MatetripColor.gray_06)){
            append("가 됩니다.")
        }
    }
    var isCancellable by remember{mutableStateOf(true)}
    var isOpen by remember{mutableStateOf(false)}
    if(isOpen){
        ConfirmationDialog(
            dialogMsg = "취소된 동행 신청은 복구가 불가능해요.\n동행 신청을 취소하시나요?",
            onOkClick = {
                /** 동행 취소 신청 api */
                isOpen = false
                isCancellable = false
            },
            onDismissRequest = {isOpen = false}
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
            .padding(bottom = 50.dp)
    ){
        NormalTopBar(
            title = "보낸 신청서",
            titleFontWeight = FontWeight(700),
            onBackClick = navBack,
            onClick = {/* 미사용 */}
        )
        Spacer(Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ){
            Text(
                text = "동행을 수락하기 전,\n동행자의 정보를 확인해 보세요!",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(700),
            )
            Spacer(Modifier.height(22.dp))
            ProfileView(
                nickname = dummyData.nickname,
                age = dummyData.age,
                gender = dummyData.gender,
                imageUrl = dummyData.profileUrl,
                tags = dummyData.tags,
                navProfileDescription = navProfileDescription
            )
            Spacer(Modifier.height(14.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(
                        color = MatetripColor.Blue_03,
                        shape = RoundedCornerShape(size = 10.dp)
                    )
                    .padding(12.dp),
            ){
                Text(
                    text = "From. ${dummyData.myNickname}",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500),
                    color = MatetripColor.Gray_11
                )
                Spacer(Modifier.height(18.dp))
                Text(
                    text = dummyData.application,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400),
                    color = MatetripColor.Gray_10
                )
            }
            Spacer(Modifier.height(14.dp))
            Text(
                text = alterText,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(400)
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(size = 10.dp))
                    .border(
                        width = 1.dp,
                        color = MatetripColor.Blue_03,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(vertical = 12.dp, horizontal = 10.dp)
            ){
                Text(
                    modifier = Modifier.clickable{
                        /** sns link 클릭 시 이동? */
                    },
                    text = dummyData.snsLink,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400)
                )
            }
            Spacer(Modifier.height(40.dp))
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(size = 10.dp),
                btnText = if(isCancellable) "동행 신청 취소" else "취소 완료",
                textColor = if(isCancellable) Color.White else MatetripColor.gray_06,
                fontSize = 14.sp,
                btnColor = if(isCancellable) Color.Black else MatetripColor.Blue_03,
                isEnabled = isCancellable,
                onClick = {isOpen = true}
            )
        }
    }
}

@Composable
private fun ProfileView(
    nickname: String,
    age: String,
    gender: String,
    imageUrl: String,
    tags: List<String>,
    navProfileDescription: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MatetripColor.Blue_03,
                shape = RoundedCornerShape(size = 10.dp)
            )
            .padding(12.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            CircleImageView(
                size = 42.dp,
                imageUrl = imageUrl
            )
            Spacer(Modifier.width(10.dp))
            Column{
                Text(
                    text = nickname,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500),
                    color = MatetripColor.Gray_11
                )
                Text(
                    text = "${age} · ${gender}",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400),
                    color = MatetripColor.gray_06
                )
            }
            Spacer(Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "프로필 보기",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400),
                    color = MatetripColor.Gray_11
                )
                Spacer(Modifier.width(4.dp))
                IconButton(
                    modifier = Modifier.size(12.dp),
                    onClick = navProfileDescription
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(Icons.navigate_next_icon),
                        contentDescription = "Navigation Btn"
                    )
                }
            }
        }
        Spacer(Modifier.height(14.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ){
            tags.forEach{tag ->
                NormalTag(
                    modifier = Modifier,
                    tagName = tag,
                    shape = RoundedCornerShape(size = 5.dp),
                    textStyle = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontWeight = FontWeight(500),
                    ),
                    color = MatetripColor.Gray_02
                )
            }
        }
    }
}

@Composable
@Preview
private fun SendApplicationUITest(){
    SendApplicationScreen(
        navBack = {},
        navProfileDescription = {}
    )
}