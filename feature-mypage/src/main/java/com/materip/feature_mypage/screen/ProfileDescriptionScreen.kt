package com.materip.feature_mypage.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.R
import com.materip.core_model.ui_model.FoodPreference
import com.materip.core_model.ui_model.TempHumanDescClass
import com.materip.core_model.ui_model.TravelInterest
import com.materip.core_model.ui_model.TravelStyle
import com.materip.matetrip.component.CircleImageView
import com.materip.matetrip.component.ImageLoadView
import com.materip.matetrip.component.NormalTopBar
import com.materip.matetrip.component.ProfileTag
import com.materip.matetrip.icon.Badges
import com.materip.matetrip.icon.Icons
import com.materip.matetrip.theme.MatetripColor

@Composable
fun ProfileDescriptionRoute(
    navBack: () -> Unit
){
    ProfileDescriptionScreen(navBack = navBack)
}

@Composable
fun ProfileDescriptionScreen(
    navBack: () -> Unit,
){
    val scrollState = rememberScrollState()
    val dummyData = TempHumanDescClass(
        nickname = "닉네임닉네임",
        level = 4,
        profileUrl = "",
        interests = listOf(
            TravelInterest.PLANNED.name,
            TravelInterest.PUBLIC_MONEY.name,
            TravelInterest.LOOKING_FOR.name,
            TravelInterest.QUICKLY.name,
        ),
        styles = listOf(
            TravelStyle.RESTAURANT_TOUR.name,
            TravelStyle.CAFE_TOUR.name,
            TravelStyle.LIFE_SHOT.name,
            TravelStyle.ACTIVITY.name,
            TravelStyle.DRIVE.name,
            TravelStyle.SHOPPING.name,
        ),
        foodPreferences = listOf(
            FoodPreference.MEAT.name,
            FoodPreference.SEAFOOD.name,
            FoodPreference.RICE.name,
            FoodPreference.COFFEE.name,
            FoodPreference.DESSERT.name,
            FoodPreference.FAST_FOOD.name,
        ),
        age = "20대 초반",
        gender = "여자",
        introduction = "고즈넉한 분위기를 좋아합니다\nMBTI F인 분과 함께 힐링하고 싶어요~",
        authenticatorType = "카카오·SMS",
        images = listOf()
    )
    val levelInfo = when(dummyData.level){
        1 -> Triple(MatetripColor.level_1_color, Badges.level_1_badge,"새싹 메이트")
        2 -> Triple(MatetripColor.level_2_color, Badges.level_2_badge,"우수 메이트")
        3 -> Triple(MatetripColor.level_3_color, Badges.level_3_badge,"열정 메이트")
        else -> Triple(MatetripColor.level_4_color, Badges.level_4_badge,"베테랑 메이트")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
    ){
        NormalTopBar(
            title = "",
            navIcon = Icons.close_icon,
            onBackClick = {},
            onClick = navBack
        )
        Spacer(Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ){
                CircleImageView(
                    size = 60.dp,
                    imageUrl = ""
                )
                Spacer(Modifier.width(13.dp))
                Column{
                    Row(
                        modifier = Modifier.height(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = levelInfo.third,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                            fontWeight = FontWeight(500),
                            color = levelInfo.first
                        )
                    }
                    Row{
                        Text(
                            text = dummyData.nickname,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                            fontWeight = FontWeight(700),
                            color = MatetripColor.Gray_11
                        )
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(levelInfo.second),
                            contentDescription = "Level Badge"
                        )
                    }
                    Text(
                        text = "${dummyData.age} · ${dummyData.gender}",
                        fontSize = 12.sp,
                        color = MatetripColor.gray_06,
                        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                        fontWeight = FontWeight(500)
                    )
                }
                Spacer(Modifier.weight(1f))
                Row(
                    modifier = Modifier.width(60.dp)
                        .height(60.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    IconButton(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape),
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
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape),
                        onClick = { /** message 연결 */ },
                    ) {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(Badges.sms_badge),
                            contentDescription = "Message Badge"
                        )
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MatetripColor.Blue_04, shape = RoundedCornerShape(size = 10.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = dummyData.introduction,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500),
                    color = MatetripColor.Gray_11,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(Modifier.height(30.dp))
            BasicInfo(
                nickname = dummyData.nickname,
                gender = dummyData.gender,
                age = dummyData.age,
                authenticatorType = dummyData.authenticatorType
            )
            Spacer(Modifier.height(40.dp))
            TravelInterestInfo(interests = dummyData.interests)
            Spacer(Modifier.height(40.dp))
            TravelStyleInfo(styles = dummyData.styles)
            Spacer(Modifier.height(40.dp))
            FoodPreferenceInfo(foodPreferences = dummyData.foodPreferences)
            Spacer(Modifier.height(40.dp))
            MyImageInfo(images = dummyData.images)
        }
    }
}

@Composable
private fun BasicInfo(
    nickname: String,
    gender: String,
    age: String,
    authenticatorType: String,
){
    val titleStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight(500),
        color = MatetripColor.gray_06
    )
    val contentStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight(500),
        color = MatetripColor.Gray_10
    )
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "기본정보",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
            fontWeight = FontWeight(700),
        )
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(
                modifier = Modifier.width(70.dp),
                text = "닉네임",
                style = titleStyle
            )
            Spacer(Modifier.width(20.dp))
            Text(
                text = nickname,
                style = contentStyle
            )
        }
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(
                modifier = Modifier.width(70.dp),
                text = "성별",
                style = titleStyle
            )
            Spacer(Modifier.width(20.dp))
            Text(
                text = gender,
                style = contentStyle
            )
        }
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(
                modifier = Modifier.width(70.dp),
                text = "나이",
                style = titleStyle
            )
            Spacer(Modifier.width(20.dp))
            Text(
                text = age,
                style = contentStyle
            )
        }
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(
                modifier = Modifier.width(70.dp),
                text = "인증방식",
                style = titleStyle
            )
            Spacer(Modifier.width(20.dp))
            Text(
                text = authenticatorType,
                style = contentStyle
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TravelInterestInfo(interests: List<String>){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "여행성향",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
            fontWeight = FontWeight(700)
        )
        Spacer(Modifier.height(12.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ){
            interests.forEach{
                ProfileTag(tagName = it)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TravelStyleInfo(styles: List<String>){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "여행스타일",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
            fontWeight = FontWeight(700)
        )
        Spacer(Modifier.height(12.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ){
            styles.forEach{
                ProfileTag(tagName = it)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FoodPreferenceInfo(foodPreferences: List<String>){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "음식취향",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
            fontWeight = FontWeight(700)
        )
        Spacer(Modifier.height(12.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ){
            foodPreferences.forEach{
                ProfileTag(tagName = it)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MyImageInfo(images: List<String>){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "이미지 (${images.size})",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
            fontWeight = FontWeight(700)
        )
        Spacer(Modifier.height(12.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ){
            images.forEach{
                ImageLoadView(
                    backgroundColor = MatetripColor.Blue_04,
                    shape = RoundedCornerShape(size = 10.dp),
                    size = 100.dp,
                    imageUrl = it,
                    onCloseClick = {/*미사용*/}
                )
            }
        }
    }
}

@Composable
@Preview
private fun ProfileDescriptionUITest(){
    ProfileDescriptionScreen(navBack = {})
}