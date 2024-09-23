package com.materip.feature_mypage.screen.MyPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.materip.core_common.ErrorState
import com.materip.core_common.toDisplayAgeString
import com.materip.core_common.toDisplayDateString
import com.materip.core_common.toDisplayString
import com.materip.core_designsystem.component.NormalTopBar
import com.materip.core_designsystem.component.ReviewDescItem
import com.materip.core_model.response.DefaultListResponseDto
import com.materip.core_model.response.ReviewItem
import com.materip.core_model.ui_model.Gender
import com.materip.core_model.ui_model.Region
import com.materip.core_model.ui_model.ReviewDescClass
import com.materip.feature_mypage.view_models.MyPage.ReviewListUiState
import com.materip.feature_mypage.view_models.MyPage.ReviewListViewModel
import com.materip.matetrip.toast.ErrorView

@Composable
fun ReviewListRoute(
    navBack: () -> Unit,
    navReviewDescription: (Int) -> Unit,
    viewModel: ReviewListViewModel = hiltViewModel()
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorState.collectAsStateWithLifecycle()

    ReviewListScreen(
        uiState = uiState.value,
        errState = errState.value,
        navBack = navBack,
        navReviewDescription = navReviewDescription
    )
}

@Composable
fun ReviewListScreen(
    uiState: ReviewListUiState,
    errState: ErrorState,
    navBack: () -> Unit,
    navReviewDescription: (Int) -> Unit,
){
    when(uiState){
        ReviewListUiState.Loading -> {
            CircularProgressIndicator()
        }
        ReviewListUiState.Error -> {
            ErrorView(
                errState = errState,
                navBack = navBack
            )
        }
        is ReviewListUiState.Success -> {
            ReviewListMainContent(
                reviews = uiState.reviews.responses,
                totalCount = uiState.reviews.totalCount,
                navBack = navBack,
                navReviewDescription = navReviewDescription
            )
        }
    }
}

@Composable
private fun ReviewListMainContent(
    reviews: List<ReviewItem>,
    totalCount: Int,
    navBack: () -> Unit,
    navReviewDescription: (Int) -> Unit
){
    val totalCountText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)))){
            append("총 ")
        }
        withStyle(style = SpanStyle(fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.roboto_medium)))){
            append("${totalCount}")
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
            onBackClick = navBack,
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
            items(reviews){review ->
                ReviewDescItem(
                    destination = review.region.toDisplayString(),
                    period = review.getDuration(),
                    startDate = review.startDate.toDisplayDateString(),
                    endDate = review.endDate.toDisplayDateString(),
                    profileUrl = review.profileImageUrl,
                    nickname = review.nickname,
                    age = review.age.toDisplayAgeString(),
                    gender = review.gender.toDisplayString(),
                    content = review.detailContent,
                    onClick = { navReviewDescription(review.reviewId) }
                )
            }
        }
    }
}

@Preview
@Composable
fun ReviewListUITest(){
    ReviewListScreen(
        uiState = ReviewListUiState.Success(
            reviews = DefaultListResponseDto(
                totalCount = 3,
                responses = listOf(
                    ReviewItem(
                        startDate = "2024.07.20",
                        endDate = "2024.07.22",
                        nickname = "닉네임",
                        region = Region.BUSAN,
                        detailContent = "같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰",
                        profileImageUrl = "",
                        reviewId = 0,
                        age = 2000,
                        gender = Gender.MALE
                    ),
                    ReviewItem(
                        startDate = "2024.07.20",
                        endDate = "2024.07.22",
                        nickname = "닉네임",
                        region = Region.BUSAN,
                        detailContent = "같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰",
                        profileImageUrl = "",
                        reviewId = 0,
                        age = 2000,
                        gender = Gender.MALE
                    ),
                    ReviewItem(
                        startDate = "2024.07.20",
                        endDate = "2024.07.22",
                        nickname = "닉네임",
                        region = Region.BUSAN,
                        detailContent = "같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰 같이 여행해서 좋았다는 리뷰",
                        profileImageUrl = "",
                        reviewId = 0,
                        age = 2000,
                        gender = Gender.MALE
                    )
                )
            )
        ),
        errState = ErrorState.Loading,
        navBack = {},
        navReviewDescription = {}
    )
}