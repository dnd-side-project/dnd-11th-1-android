package com.materip.feature_mypage.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.matetrip.component.CircleImageView
import com.materip.matetrip.component.CustomButton
import com.materip.matetrip.component.ProfileTag
import com.materip.matetrip.icon.Badges
import com.materip.matetrip.theme.MatetripColor
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.graphics.Color
import com.materip.matetrip.component.LevelInfoDialog
import com.materip.matetrip.icon.Icons

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileMainContent(
    profileInfo: String,
    profileTags: List<String>,
    navEditProfile: () -> Unit,
    navProfileDescription: () -> Unit,
    navQuiz100: () -> Unit,
    navPreview: () -> Unit
){
    val scrollState = rememberScrollState()
    val currentLevel = 1
    var isLevelInfoOpen by remember{mutableStateOf(false)}

    if(isLevelInfoOpen){
        LevelInfoDialog(
            currentLevel = currentLevel,
            onDismissRequest = {isLevelInfoOpen = false}
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(
                    width = 1.dp,
                    color = MatetripColor.Blue_03,
                    shape = RoundedCornerShape(size = 10.dp)
                )
                .padding(16.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ){
                CircleImageView(
                    size = 60.dp,
                    imageUrl = "" /** image profile 받아야 함 */
                )
                Spacer(Modifier.width(13.dp))
                Column{
                    Row(
                        modifier = Modifier.height(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "새싹 메이트", /** 등급 text */
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                            fontWeight = FontWeight(500),
                            color = MatetripColor.level_1_color /** 등급에 따른 색 변경 */
                        )
                        Spacer(Modifier.width(4.dp))
                        IconButton(
                            modifier = Modifier.size(16.dp),
                            onClick = {isLevelInfoOpen = true}
                        ) {
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                painter = painterResource(Badges.question_badge),
                                contentDescription = "question badge"
                            )
                        }
                    }
                    Row{
                        Text(
                            text = "닉네임닉네임", /** 유저 정보 중 닉네임 */
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                            fontWeight = FontWeight(700),
                            color = MatetripColor.Gray_11
                        )
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(Badges.level_1_badge),
                            contentDescription = "Level Badge"
                        )
                    }
                    Text(
                        text = "20대 초반·여자",
                        fontSize = 12.sp,
                        color = MatetripColor.gray_06,
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontWeight = FontWeight(500)
                    )
                }
                Spacer(Modifier.width(43.dp))
                OutlinedButton(
                    modifier = Modifier.height(28.dp),
                    contentPadding = PaddingValues(horizontal = 5.dp),
                    shape = RoundedCornerShape(size = 100.dp),
                    border = BorderStroke(width = 1.dp, color = MatetripColor.Primary),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                    ),
                    onClick = navEditProfile
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Icon(
                            modifier = Modifier.size(14.dp),
                            painter = painterResource(Icons.review_icon),
                            tint = MatetripColor.Primary,
                            contentDescription = "Edit Button"
                        )
                        Spacer(Modifier.width(3.dp))
                        Text(
                            text = "수정",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                            fontWeight = FontWeight(500),
                            color = MatetripColor.Primary
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.width(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                IconButton(
                    modifier = Modifier.size(20.dp),
                    onClick = { /** kakao 연결 */ },
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(Badges.kakaotalk_badge),
                        contentDescription = "Kakao Badge"
                    )
                }
                Spacer(Modifier.width(4.dp))
                IconButton(
                    modifier = Modifier.size(20.dp),
                    onClick = { /** message 연결 */ },
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(Badges.sms_badge),
                        contentDescription = "Message Badge"
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MatetripColor.Blue_04, shape = RoundedCornerShape(10.dp))
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                if (profileInfo.isEmpty()){
                    Text(
                        text = "프로필을 수정해서 나를 표현해 보세요",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontWeight = FontWeight(500),
                        color = MatetripColor.gray_06
                    )
                    Text(
                        text = "(상세하게 표현할수록 신뢰도가 쌓여요",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontWeight = FontWeight(500),
                        color = MatetripColor.gray_06
                    )
                } else {
                    Text(
                        text = "고즈넉한 분위기를 좋아합니다\nMBTI F인 분과 함께 힐링하고 싶어요", /** introduction  */
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontWeight = FontWeight(400),
                    )
                }
            }
            Spacer(Modifier.height(20.dp))
            FlowRow(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ){
                profileTags.forEach{
                    ProfileTag(it)
                }
            }
            Spacer(Modifier.height(16.dp))
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp),
                shape = RoundedCornerShape(size = 8.dp),
                btnText = "자세히 보기",
                textColor = MatetripColor.Gray_08,
                fontSize = 12.sp,
                btnColor = MatetripColor.Gray_02,
                onClick = navProfileDescription
            )
        }
        Spacer(Modifier.height(20.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp),
            onClick = navQuiz100,
            shape = RoundedCornerShape(size = 10.dp),
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Badges.challenge_badge),
                contentDescription = "100 Quiz"
            )
        }
        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "백문백답 챌린지",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(700),
                color = MatetripColor.Gray_11
            )
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = navQuiz100
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(com.materip.core_designsystem.R.drawable.navigate_next_24px),
                    contentDescription = "Navigation Button"
                )
            }
        }
        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "동행 후기",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(700),
                color = MatetripColor.Gray_11
            )
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = navPreview
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(com.materip.core_designsystem.R.drawable.navigate_next_24px),
                    contentDescription = "Navigation Button"
                )
            }
        }
    }
}