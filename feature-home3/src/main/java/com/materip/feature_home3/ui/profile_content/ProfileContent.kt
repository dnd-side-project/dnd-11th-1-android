package com.materip.feature_home3.ui.profile_content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.materip.core_common.toDisplayAgeString
import com.materip.core_common.toDisplayString
import com.materip.core_designsystem.MatetripGrade
import com.materip.core_designsystem.R
import com.materip.core_designsystem.component.CircleImageView
import com.materip.core_designsystem.component.ImageLoadView
import com.materip.core_designsystem.component.ProfileTag
import com.materip.core_designsystem.icon.Badges
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.feature_home3.ui.component.BasicInfo
import com.materip.feature_home3.viewModel.ProfileViewModel

@Composable
fun ProfileContent(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profileDetails by viewModel.profileDetails.collectAsState()

    profileDetails?.let { userInfo ->
        val scrollState = rememberScrollState()
        val levelInfo = when (userInfo.grade) {
            1.toString() -> MatetripGrade.level_1
            2.toString() -> MatetripGrade.level_2
            3.toString() -> MatetripGrade.level_3
            else -> MatetripGrade.level_4
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = scrollState)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    CircleImageView(
                        size = 60.dp,
                        imageUrl = userInfo.profileImageUrl ?: ""
                    )
                    Spacer(Modifier.width(13.dp))
                    Column {
                        Row(
                            modifier = Modifier.height(18.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = levelInfo.gradeKoName,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                                    fontWeight = FontWeight(500),
                                    color = levelInfo.color as Color,
                                    textAlign = TextAlign.Justify,
                                    platformStyle = PlatformTextStyle(
                                        includeFontPadding = false
                                    )
                                )
                            )
                        }
                        Row {
                            Text(
                                text = userInfo.nickname,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                                fontWeight = FontWeight(700),
                                color = MateTripColors.Gray_11
                            )
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(levelInfo.badge),
                                contentDescription = "Level Badge"
                            )
                        }
                        Text(
                            text = "${userInfo.birthYear.toDisplayAgeString()} · ${userInfo.gender}",
                            fontSize = 12.sp,
                            color = MateTripColors.Gray_06,
                            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                            fontWeight = FontWeight(500)
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Row(
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
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
                        /** message 연결 */
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
                        .background(
                            color = MateTripColors.Blue_04,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (userInfo.description.isNullOrEmpty()) {
                        Column {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "프로필을 수정해서 나를 표현해 보세요",
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                                fontWeight = FontWeight(500),
                                color = MateTripColors.Gray_06,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "(상세하게 표현할수록 신뢰도가 쌓여요)",
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                                fontWeight = FontWeight(500),
                                color = MateTripColors.Gray_06,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Text(
                            text = "${userInfo.description}",
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                            fontWeight = FontWeight(400),
                        )
                    }
                }
                Spacer(Modifier.height(30.dp))
                BasicInfo(
                    nickname = userInfo.nickname,
                    gender = userInfo.gender.toDisplayString(),
                    age = userInfo.birthYear.toDisplayAgeString(),
                    authenticatorType = userInfo.provider
                )
                Spacer(Modifier.height(40.dp))
                TravelInterestInfo(interests = userInfo.travelPreferences)
                Spacer(Modifier.height(40.dp))
                TravelStyleInfo(styles = userInfo.travelStyles)
                Spacer(Modifier.height(40.dp))
                FoodPreferenceInfo(foodPreferences = userInfo.foodPreferences)
                Spacer(Modifier.height(40.dp))
                MyImageInfo(images = userInfo.userImageUrls)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TravelInterestInfo(interests: List<String>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
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
        ) {
            interests.forEach {
                ProfileTag(tagName = it)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TravelStyleInfo(styles: List<String>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
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
        ) {
            styles.forEach {
                ProfileTag(tagName = it)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FoodPreferenceInfo(foodPreferences: List<String>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
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
        ) {
            foodPreferences.forEach {
                ProfileTag(tagName = it)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MyImageInfo(images: List<String>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
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
        ) {
            images.forEach {
                ImageLoadView(
                    backgroundColor = MateTripColors.Blue_04,
                    shape = RoundedCornerShape(size = 10.dp),
                    size = 100.dp,
                    imageUrl = it,
                    onCloseClick = {/*미사용*/ }
                )
            }
        }
    }
}