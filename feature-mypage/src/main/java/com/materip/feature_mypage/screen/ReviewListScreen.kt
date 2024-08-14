package com.materip.feature_mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_model.ui_model.ReviewDescClass
import com.materip.feature_mypage.R
import com.materip.matetrip.component.NormalTopBar
import com.materip.matetrip.component.ReviewDescItem

@Composable
fun ReviewListRoute(){
    ReviewListScreen()
}

@Composable
fun ReviewListScreen(){
    val dummyReviewDesc = listOf(
        ReviewDescClass(
            destination = "부산",
            period = "2박3일",
            startDate = "2024.07.20",
            endDate = "2024.07.22",
            profileUrl = "",
            nickname = "닉네임",
            age = "20대 초반",
            gender = "여자",
            content = "같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰"
        ),
        ReviewDescClass(
            destination = "부산",
            period = "2박3일",
            startDate = "2024.07.20",
            endDate = "2024.07.22",
            profileUrl = "",
            nickname = "닉네임",
            age = "20대 초반",
            gender = "여자",
            content = "같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰"
        ),
        ReviewDescClass(
            destination = "부산",
            period = "2박3일",
            startDate = "2024.07.20",
            endDate = "2024.07.22",
            profileUrl = "",
            nickname = "닉네임",
            age = "20대 초반",
            gender = "여자",
            content = "같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰"
        )
    )
    val totalCountText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)))){
            append("총 ")
        }
        withStyle(style = SpanStyle(fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.roboto_medium)))){
            append("${dummyReviewDesc.size}")
        }
        withStyle(style = SpanStyle(fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)))){
            append("개")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp),
    ){
        NormalTopBar(
            title = "받은 동행 후기",
            titleFontWeight = FontWeight(700),
            onBackClick = {/** 뒤로 가기 */},
            onClick = {/* 미사용 */}
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = totalCountText,
            fontSize = 16.sp,
            fontWeight = FontWeight(700),
        )
        Spacer(Modifier.height(14.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(dummyReviewDesc){review ->
                ReviewDescItem(
                    destination = review.destination,
                    period = review.period,
                    startDate = review.startDate,
                    endDate = review.endDate,
                    profileUrl = review.profileUrl,
                    nickname = review.nickname,
                    age = review.age,
                    gender = review.gender,
                    content = review.content,
                    onClick = {/** 상세 후기 navigation */}
                )
            }
        }
    }
}

@Preview
@Composable
fun ReviewListUITest(){
    ReviewListScreen()
}