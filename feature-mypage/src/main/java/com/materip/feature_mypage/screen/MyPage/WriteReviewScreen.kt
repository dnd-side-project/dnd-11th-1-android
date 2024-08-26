package com.materip.feature_mypage.screen.MyPage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.materip.core_designsystem.component.CustomButton
import com.materip.core_designsystem.component.CustomClickableTag
import com.materip.core_designsystem.component.ImageLoadView
import com.materip.core_designsystem.component.NormalTag
import com.materip.core_designsystem.component.NormalTopBar
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.theme.MateTripColors

@Composable
fun WriteReviewRoute(
    id: Int?,
    navBack: () -> Unit
){


    WriteReviewScreen(
        navBack = navBack
    )
}

@Composable
fun WriteReviewScreen(
    navBack: () -> Unit,
){
    val scrollState = rememberScrollState()
    var isDone by remember{mutableStateOf(false)}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
            .padding(bottom = 35.dp)
    ){
        NormalTopBar(
            title = "동행 후기 작성",
            titleFontWeight = FontWeight(700),
            onBackClick = navBack,
            onClick = { /* 미사용 */ }
        )
        Spacer(Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ){
            TravelSatisfiedSurvey()
            Spacer(Modifier.height(60.dp))
            RecommendNextSurvey()
            Spacer(Modifier.height(60.dp))
            SelectTravelType()
            Spacer(Modifier.height(60.dp))
            SelectReviewTag()
            Spacer(Modifier.height(60.dp))
            WriteReviewDetails()
            Spacer(Modifier.height(60.dp))
            SharePictures(
                pictures = SnapshotStateList(),
                onDeleteImage = {},
                onCameraClick = {}
            )
            Spacer(Modifier.height(60.dp))
            CustomButton(
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(size = 8.dp),
                btnText = "작성완료",
                fontSize = 14.sp,
                btnColor = if(isDone) MateTripColors.ActivatedColor else MateTripColors.InactiveColor,
                textColor = MateTripColors.Gray_06,
                onClick = { /*TODO*/ }
            )
        }
    }
}

@Composable
private fun TravelSatisfiedSurvey(){
    var satisfiedGrade by remember{mutableStateOf("만족")}

    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "여행은 만족 하셨나요?",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(700),
                    color = MateTripColors.Gray_11,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                )
            )
            Spacer(Modifier.width(12.dp))
            NormalTag(
                modifier = Modifier.width(42.dp).height(21.dp),
                tagName = "필수",
                shape = RoundedCornerShape(size = 55.dp),
                color = MateTripColors.Gray_11,
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500),
                    color = Color.White,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                innerPaddingValues = PaddingValues(0.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            CustomClickableTag(
                tagName = "만족",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = satisfiedGrade == "만족",
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {satisfiedGrade = "만족"}
            )
            Spacer(Modifier.width(10.dp))
            CustomClickableTag(
                tagName = "보통",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = satisfiedGrade == "보통",
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {satisfiedGrade = "보통"}
            )
            Spacer(Modifier.width(10.dp))
            CustomClickableTag(
                tagName = "불만족",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = satisfiedGrade == "불만족",
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {satisfiedGrade = "불만족"}
            )
        }
    }
}

@Composable
private fun RecommendNextSurvey(){
    var isRecommend by remember{mutableStateOf("추천")}

    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "다음 동행자에게 추천하시나요?",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(700),
                    color = MateTripColors.Gray_11,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                )
            )
            Spacer(Modifier.width(12.dp))
            NormalTag(
                modifier = Modifier.width(42.dp).height(21.dp),
                tagName = "필수",
                shape = RoundedCornerShape(size = 55.dp),
                color = MateTripColors.Gray_11,
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500),
                    color = Color.White,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
                innerPaddingValues = PaddingValues(0.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            CustomClickableTag(
                tagName = "추천",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = isRecommend == "추천",
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {isRecommend = "추천"}
            )
            Spacer(Modifier.width(10.dp))
            CustomClickableTag(
                tagName = "비추천",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = isRecommend == "비추천",
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {isRecommend = "비추천"}
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SelectTravelType(){
    var travelType by remember{mutableStateOf("부분동행")}

    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "동행 유형을 선택해 주세요",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(700),
                    color = MateTripColors.Gray_11,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                )
            )
            Spacer(Modifier.width(12.dp))
            NormalTag(
                modifier = Modifier.width(42.dp).height(21.dp),
                tagName = "필수",
                shape = RoundedCornerShape(size = 55.dp),
                color = MateTripColors.Gray_11,
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500),
                    color = Color.White,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                innerPaddingValues = PaddingValues(0.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ){
            CustomClickableTag(
                tagName = "전체동행",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = travelType == "추천",
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {travelType = "추천"}
            )
            Spacer(Modifier.width(10.dp))
            CustomClickableTag(
                tagName = "부분동행",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = travelType == "부분동행",
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {travelType = "비추천"}
            )
            Spacer(Modifier.width(10.dp))
            CustomClickableTag(
                tagName = "숙소공유",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = travelType == "숙소공유",
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {travelType = "숙소공유"}
            )
            Spacer(Modifier.width(10.dp))
            CustomClickableTag(
                tagName = "투어동행",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = travelType == "투어동행",
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {travelType = "투어동행"}
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SelectReviewTag(){
    val personality = remember{mutableStateListOf<String>()}
    val preferences = remember{mutableStateListOf<String>()}
    val styles = remember{mutableStateListOf<String>()}

    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "어떤 점이 좋았나요?",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(700),
                color = MateTripColors.Gray_11
            )
            Spacer(Modifier.width(12.dp))
            NormalTag(
                modifier = Modifier.width(42.dp).height(21.dp),
                tagName = "필수",
                shape = RoundedCornerShape(size = 55.dp),
                color = MateTripColors.Gray_11,
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500),
                    color = Color.White,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
                innerPaddingValues = PaddingValues(0.dp)
            )
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = "동행자의 성격, 성향, 스타일을 알려주세요",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(500),
            color = MateTripColors.Gray_06
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = "성격",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(500),
            color = MateTripColors.Gray_11
        )
        Spacer(Modifier.height(10.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            CustomClickableTag(
                tagName = "친절해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "친절해요" in personality,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("친절해요" in personality) personality.remove("친절해요")
                    else personality.add("친절해요")
                }
            )
            CustomClickableTag(
                tagName = "밝아요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "밝아요" in personality,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("밝아요" in personality) personality.remove("밝아요")
                    else personality.add("밝아요")
                }
            )
            CustomClickableTag(
                tagName = "재밌어요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "재밌어요" in personality,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("재밌어요" in personality) personality.remove("재밌어요")
                    else personality.add("재밌어요")
                }
            )
            CustomClickableTag(
                tagName = "편안해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "편안해요" in personality,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("편안해요" in personality) personality.remove("편안해요")
                    else personality.add("편안해요")
                }
            )
            CustomClickableTag(
                tagName = "신뢰가 가요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "신뢰가 가요" in personality,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("신뢰가 가요" in personality) personality.remove("신뢰가 가요")
                    else personality.add("신뢰가 가요")
                }
            )
            CustomClickableTag(
                tagName = "긍정적이에요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "긍정적이에요" in personality,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("긍정적이에요" in personality) personality.remove("긍정적이에요")
                    else personality.add("긍정적이에요")
                }
            )
            CustomClickableTag(
                tagName = "센스있어요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "센스있어요" in personality,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("센스있어요" in personality) personality.remove("센스있어요")
                    else personality.add("센스있어요")
                }
            )
            CustomClickableTag(
                tagName = "감성적이에요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "감성적이에요" in personality,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("감성적이에요" in personality) personality.remove("감성적이에요")
                    else personality.add("감성적이에요")
                }
            )
            CustomClickableTag(
                tagName = "이성적이에요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "이성적이에요" in personality,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("이성적이에요" in personality) personality.remove("이성적이에요")
                    else personality.add("이성적이에요")
                }
            )
            CustomClickableTag(
                tagName = "열정적이에요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "열정적이에요" in personality,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("열정적이에요" in personality) personality.remove("열정적이에요")
                    else personality.add("열정적이에요")
                }
            )
            CustomClickableTag(
                tagName = "붙임성이 좋아요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "붙임성이 좋아요" in personality,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("붙임성이 좋아요" in personality) personality.remove("붙임성이 좋아요")
                    else personality.add("붙임성이 좋아요")
                }
            )
        }
        Spacer(Modifier.height(46.dp))
        Text(
            text = "여행 성향",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(500),
            color = MateTripColors.Gray_11
        )
        Spacer(Modifier.height(10.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            CustomClickableTag(
                tagName = "계획적이에요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "계획적이에요" in preferences,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("계획적이에요" in preferences) preferences.remove("계획적이에요")
                    else preferences.add("계획적이에요")
                }
            )
            CustomClickableTag(
                tagName = "즉흥적이에요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "즉흥적이에요" in preferences,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("즉흥적이에요" in preferences) preferences.remove("즉흥적이에요")
                    else preferences.add("즉흥적이에요")
                }
            )
            CustomClickableTag(
                tagName = "공금이 편해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "공금이 편해요" in preferences,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("공금이 편해요" in preferences) preferences.remove("공금이 편해요")
                    else preferences.add("공금이 편해요")
                }
            )
            CustomClickableTag(
                tagName = "더치페이 해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "더치페이 해요" in preferences,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("더치페이 해요" in preferences) preferences.remove("더치페이 해요")
                    else preferences.add("더치페이 해요")
                }
            )
            CustomClickableTag(
                tagName = "부지런해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "부지런해요" in preferences,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("부지런해요" in preferences) preferences.remove("부지런해요")
                    else preferences.add("부지런해요")
                }
            )
            CustomClickableTag(
                tagName = "느긋해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "느긋해요" in preferences,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("느긋해요" in preferences) preferences.remove("느긋해요")
                    else preferences.add("느긋해요")
                }
            )
        }
        Spacer(Modifier.height(46.dp))
        Text(
            text = "여행 스타일",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(500),
            color = MateTripColors.Gray_11
        )
        Spacer(Modifier.height(10.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            CustomClickableTag(
                tagName = "맛집을 좋아해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "맛집을 좋아해요" in styles,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("맛집을 좋아해요" in styles) styles.remove("맛집을 좋아해요")
                    else styles.add("맛집을 좋아해요")
                }
            )
            CustomClickableTag(
                tagName = "맛집이 아니어도 돼요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "맛집이 아니어도 돼요" in styles,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("맛집이 아니어도 돼요" in styles) styles.remove("맛집이 아니어도 돼요")
                    else styles.add("맛집이 아니어도 돼요")
                }
            )
            CustomClickableTag(
                tagName = "핫플을 선호해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "핫플을 선호해요" in styles,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("핫플을 선호해요" in styles) styles.remove("핫플을 선호해요")
                    else styles.add("핫플을 선호해요")
                }
            )
            CustomClickableTag(
                tagName = "고즈넉한 곳을 좋아해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "고즈넉한 곳을 좋아해요" in styles,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("고즈넉한 곳을 좋아해요" in styles) styles.remove("고즈넉한 곳을 좋아해요")
                    else styles.add("고즈넉한 곳을 좋아해요")
                }
            )
            CustomClickableTag(
                tagName = "사진 찍는 걸 좋아해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "사진 찍는 걸 좋아해요" in styles,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("사진 찍는 걸 좋아해요" in styles) styles.remove("사진 찍는 걸 좋아해요")
                    else styles.add("사진 찍는 걸 좋아해요")
                }
            )
            CustomClickableTag(
                tagName = "관광지를 선호해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "관광지를 선호해요" in styles,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("관광지를 선호해요" in styles) styles.remove("관광지를 선호해요")
                    else styles.add("관광지를 선호해요")
                }
            )
            CustomClickableTag(
                tagName = "힐링을 선호해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "힐링을 선호해요" in styles,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("힐링을 선호해요" in styles) styles.remove("힐링을 선호해요")
                    else styles.add("힐링을 선호해요")
                }
            )
            CustomClickableTag(
                tagName = "액티비티를 즐겨요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "액티비티를 즐겨요" in styles,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("액티비티를 즐겨요" in styles) styles.remove("액티비티를 즐겨요")
                    else styles.add("액티비티를 즐겨요")
                }
            )
            CustomClickableTag(
                tagName = "쇼핑을 좋아해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "쇼핑을 좋아해요" in styles,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("쇼핑을 좋아해요" in styles) styles.remove("쇼핑을 좋아해요")
                    else styles.add("쇼핑을 좋아해요")
                }
            )
            CustomClickableTag(
                tagName = "카페를 좋아해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = "카페를 좋아해요" in styles,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {
                    if("카페를 좋아해요" in styles) styles.remove("카페를 좋아해요")
                    else styles.add("카페를 좋아해요")
                }
            )
        }
    }
}

@Composable
private fun WriteReviewDetails(){
    var reviewDetails by remember{mutableStateOf("")}

    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "상세 리뷰를 작성해 주세요",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(700),
                color = MateTripColors.Gray_11
            )
            Spacer(Modifier.width(12.dp))
            NormalTag(
                modifier = Modifier.width(42.dp).height(21.dp),
                tagName = "선택",
                shape = RoundedCornerShape(size = 55.dp),
                color = MateTripColors.Gray_03,
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500),
                    color = MateTripColors.Gray_08,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
                innerPaddingValues = PaddingValues(0.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .height(180.dp),
            value = reviewDetails,
            onValueChange = {reviewDetails = it},
            shape = RoundedCornerShape(size = 10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MateTripColors.Blue_03,
                unfocusedBorderColor = MateTripColors.Blue_03,
                focusedPlaceholderColor = MateTripColors.Gray_05,
                unfocusedPlaceholderColor = MateTripColors.Gray_05
            ),
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Justify,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            ),
            placeholder = {
                Text(
                    text = "메이트와 함께한 소중한 동행 경험을 알려주세요 :>",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontWeight = FontWeight(400),
                        textAlign = TextAlign.Justify,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
            },
        )
    }
}

@Composable
private fun SharePictures(
    pictures: SnapshotStateList<String>,
    onDeleteImage: (String) -> Unit,
    onCameraClick: () -> Unit,
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "여행의 특별한 순간을 공유해 주세요",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(700),
                color = MateTripColors.Gray_11
            )
            Spacer(Modifier.width(12.dp))
            NormalTag(
                modifier = Modifier.width(42.dp).height(21.dp),
                tagName = "선택",
                shape = RoundedCornerShape(size = 55.dp),
                color = MateTripColors.Gray_03,
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500),
                    color = MateTripColors.Gray_08,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
                innerPaddingValues = PaddingValues(0.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            item{
                Column(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            color = MateTripColors.Blue_04,
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .clickable {
                            onCameraClick()
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Icons.camera_icon),
                        contentDescription = "Camera Icon"
                    )
                    Text(
                        text = "${pictures.size}/5",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontWeight = FontWeight(400),
                        color = MateTripColors.icon_color
                    )
                }
            }
            items(pictures){picture ->
                ImageLoadView(
                    shape = RoundedCornerShape(size = 10.dp),
                    size = 60.dp,
                    imageUrl = picture,
                    backgroundColor = MateTripColors.icon_loading_color,
                    isEnabledClose = true,
                    onCloseClick = { onDeleteImage(picture) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun WriteReviewUITest(){
    WriteReviewScreen(
        navBack = {}
    )
}