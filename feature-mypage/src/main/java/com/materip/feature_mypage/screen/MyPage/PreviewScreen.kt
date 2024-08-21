package com.materip.feature_mypage.screen.MyPage

import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.component.NormalTopBar
import com.materip.core_designsystem.component.ReviewDescItem
import com.materip.core_designsystem.component.ReviewItem
import com.materip.core_designsystem.icon.Icons
import com.materip.core_model.ui_model.ReviewClass
import com.materip.core_model.ui_model.ReviewDescClass

@Composable
fun PreviewRoute(
    navBack: () -> Unit,
    navReview: () -> Unit,
    navReviewList: () -> Unit,
    navReviewDescription: () -> Unit,
){
    PreviewScreen(
        navBack = navBack,
        navReview = navReview,
        navReviewList = navReviewList,
        navReviewDescription = navReviewDescription
    )
}

@Composable
fun PreviewScreen(
    navBack: () -> Unit,
    navReview: () -> Unit,
    navReviewList: () -> Unit,
    navReviewDescription: () -> Unit,
){
    val dummyReview = ReviewClass(
        totalCount = 14,
        review = listOf(
            Pair(5, "붙임성이 좋아요."),
            Pair(3, "친절해요."),
            Pair(3, "계획적이에요.")
        )
    )
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
    ){
        NormalTopBar(
            title = "동행후기",
            titleFontWeight = FontWeight(700),
            onBackClick = navBack,
            onClick = {/* 미사용 */}
        )
        Spacer(Modifier.height(10.dp))
        ReviewFeedback(
            dummyReview = dummyReview,
            navReview = navReview
        )
        Spacer(Modifier.height(40.dp))
        ReviewFeedbackDesc(
            dummyReviewDesc = dummyReviewDesc,
            navReviewList = navReviewList,
            navReviewDescription = navReviewDescription
        )
    }
}

@Composable
fun ReviewFeedback(
    dummyReview: ReviewClass,
    navReview: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "받은 동행 평가",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(700),
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = "(${dummyReview.totalCount})",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.roboto_medium)),
                    fontWeight = FontWeight(700)
                )
            }
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = navReview
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(Icons.navigate_next_icon),
                    contentDescription = "Next Button"
                )
            }
        }
        Spacer(Modifier.height(14.dp))
        dummyReview.review.forEach{
            ReviewItem(review = it)
            if(dummyReview.review.indexOf(it) != dummyReview.review.lastIndex){
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun ReviewFeedbackDesc(
    dummyReviewDesc: List<ReviewDescClass>,
    navReviewList: () -> Unit,
    navReviewDescription: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "받은 동행 후기",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(700),
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = dummyReviewDesc.size.toString(),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.roboto_medium)),
                    fontWeight = FontWeight(700),
                )
            }
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = navReviewList
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(Icons.navigate_next_icon),
                    contentDescription = "Next Button"
                )
            }
        }
        Spacer(Modifier.height(14.dp))
        dummyReviewDesc.forEach{
            ReviewDescItem(
                destination = it.destination,
                period = it.period,
                startDate = it.startDate,
                endDate = it.endDate,
                profileUrl = it.profileUrl,
                nickname = it.nickname,
                age = it.age,
                gender = it.gender,
                content = it.content,
                onClick = navReviewDescription
            )
            if(dummyReviewDesc.indexOf(it) != dummyReviewDesc.lastIndex){
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewUITest(){
    PreviewScreen(
        navBack = {},
        navReview = {},
        navReviewDescription = {},
        navReviewList = {}
    )
}