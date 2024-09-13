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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.materip.core_common.ErrorState
import com.materip.core_common.toDisplayAgeString
import com.materip.core_common.toDisplayDateString
import com.materip.core_common.toDisplayString
import com.materip.core_designsystem.component.NormalTopBar
import com.materip.core_designsystem.component.ProgressIndicatorPreview
import com.materip.core_designsystem.component.ReviewDescItem
import com.materip.core_designsystem.component.ReviewItem
import com.materip.core_designsystem.icon.Icons
import com.materip.core_model.response.DefaultListResponseDto
import com.materip.core_model.response.EvaluationItem
import com.materip.core_model.response.GetReviewEvaluationsResponseDto
import com.materip.core_model.response.ReviewItem
import com.materip.core_model.ui_model.Gender
import com.materip.core_model.ui_model.Region
import com.materip.feature_mypage.view_models.MyPage.PreviewData
import com.materip.feature_mypage.view_models.MyPage.PreviewUiState
import com.materip.feature_mypage.view_models.MyPage.PreviewViewModel

@Composable
fun PreviewRoute(
    navBack: () -> Unit,
    navReviewEvaluation: () -> Unit,
    navReviewList: () -> Unit,
    navReviewDescription: (Int) -> Unit,
    viewModel: PreviewViewModel = hiltViewModel()
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorState.collectAsStateWithLifecycle()

    PreviewScreen(
        uiState = uiState.value,
        errState = errState.value,
        navBack = navBack,
        navReviewEvaluation = navReviewEvaluation,
        navReviewList = navReviewList,
        navReviewDescription = navReviewDescription
    )
}

@Composable
fun PreviewScreen(
    uiState: PreviewUiState,
    errState: ErrorState,
    navBack: () -> Unit,
    navReviewEvaluation: () -> Unit,
    navReviewList: () -> Unit,
    navReviewDescription: (Int) -> Unit,
){
    when(uiState){
        PreviewUiState.Loading -> {
            CircularProgressIndicator()
        }
        PreviewUiState.Error -> {
            Text(
                text = "Error",
                fontSize = 100.sp,
                color = Color.Red
            )
        }
        is PreviewUiState.Success -> {
            PreviewMainContent(
                reviews = uiState.preview.reviews.responses,
                reviewTotal = uiState.preview.reviews.totalCount,
                evaluations = uiState.preview.evaluations.evaluationResponse,
                evaluationTotal = uiState.preview.evaluations.evaluationCount,
                navReviewEvaluation = navReviewEvaluation,
                navReviewList = navReviewList,
                navReviewDescription = navReviewDescription,
                navBack = navBack
            )
        }
    }
}

@Composable
private fun PreviewMainContent(
    reviews: List<ReviewItem>,
    reviewTotal: Int,
    evaluations: List<EvaluationItem>,
    evaluationTotal: Int,
    navReviewEvaluation: () -> Unit,
    navBack: () -> Unit,
    navReviewList: () -> Unit,
    navReviewDescription: (Int) -> Unit,
){
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
        ReviewEvaluations(
            evaluations = evaluations,
            evaluationTotal = evaluationTotal,
            navReviewEvaluation = navReviewEvaluation
        )
        Spacer(Modifier.height(40.dp))
        ReviewsContent(
            reviews = reviews,
            reviewTotal = reviewTotal,
            navReviewList = navReviewList,
            navReviewDescription = navReviewDescription
        )
    }
}

@Composable
private fun ReviewEvaluations(
    evaluations: List<EvaluationItem>,
    evaluationTotal: Int,
    navReviewEvaluation: () -> Unit
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
                    text = "( ${evaluationTotal} )",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.roboto_medium)),
                    fontWeight = FontWeight(700)
                )
            }
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = navReviewEvaluation
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(Icons.navigate_next_icon),
                    contentDescription = "Next Button"
                )
            }
        }
        Spacer(Modifier.height(14.dp))
        evaluations.forEach{evaluation ->
            ReviewItem(
                message = evaluation.getKoreanType(),
                count = evaluation.count
            )
            if(evaluations.indexOf(evaluation) != evaluations.lastIndex){
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun ReviewsContent(
    reviews: List<ReviewItem>,
    reviewTotal: Int,
    navReviewList: () -> Unit,
    navReviewDescription: (Int) -> Unit
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
                    text = "( ${reviewTotal} )",
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
        reviews.forEach{ review ->
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
            if(reviews.indexOf(review) != reviews.lastIndex){
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewUITest(){
    PreviewScreen(
        uiState = PreviewUiState.Success(
            PreviewData(
                evaluations = GetReviewEvaluationsResponseDto(
                    evaluationCount = 14,
                    evaluationResponse = listOf(
                        EvaluationItem(
                            type = "붙임성이 좋아요",
                            count = 5
                        ),
                        EvaluationItem(
                            type = "친절해요",
                            count = 3
                        ),
                        EvaluationItem(
                            type = "계획적이에요",
                            count = 3
                        )
                    )
                ),
                reviews = DefaultListResponseDto<ReviewItem>(
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
                    ),
                    totalCount = 3
                )
            )
        ),
        errState = ErrorState.Loading,
        navBack = {},
        navReviewEvaluation = {},
        navReviewDescription = {},
        navReviewList = {}
    )
}