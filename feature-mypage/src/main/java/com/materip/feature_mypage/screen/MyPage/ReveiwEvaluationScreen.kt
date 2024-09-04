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
import com.materip.core_designsystem.component.NormalTopBar
import com.materip.core_designsystem.component.ReviewItem
import com.materip.core_model.response.EvaluationItem
import com.materip.core_model.response.GetReviewEvaluationsResponseDto
import com.materip.core_model.ui_model.PersonalityType
import com.materip.core_model.ui_model.ReviewClass
import com.materip.core_model.ui_model.TravelPreferenceForReview
import com.materip.core_model.ui_model.TravelStyleForReview
import com.materip.feature_mypage.view_models.MyPage.ReviewEvaluationUiState
import com.materip.feature_mypage.view_models.MyPage.ReviewEvaluationViewModel

@Composable
fun ReviewEvaluationRoute(
    navBack: () -> Unit,
    viewModel: ReviewEvaluationViewModel = hiltViewModel()
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorState.collectAsStateWithLifecycle()
    ReviewEvaluationScreen(
        uiState = uiState.value,
        errState = errState.value,
        navBack = navBack
    )
}

@Composable
fun ReviewEvaluationScreen(
    uiState: ReviewEvaluationUiState,
    errState: ErrorState,
    navBack: () -> Unit,
){
    when(uiState){
        ReviewEvaluationUiState.Loading -> {
            CircularProgressIndicator()
        }
        ReviewEvaluationUiState.Error -> {
            Text(
                text = "Error",
                fontSize = 100.sp,
                color = Color.Red
            )
        }
        is ReviewEvaluationUiState.Success -> {
            ReviewEvaluationMainContent(
                totalCount = uiState.reviewEvaluations.evaluationCount,
                reviewEvaluations = uiState.reviewEvaluations.evaluationResponse,
                navBack = navBack
            )
        }
    }

}

@Composable
private fun ReviewEvaluationMainContent(
    totalCount: Int,
    reviewEvaluations: List<EvaluationItem>,
    navBack: () -> Unit
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
            .padding(horizontal = 20.dp)
    ){
        NormalTopBar(
            title = "받은 동행 평가",
            titleFontWeight = FontWeight(700),
            onBackClick = navBack,
            onClick = {/* 미사용 */}
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = totalCountText,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(700)
        )
        Spacer(Modifier.height(14.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(reviewEvaluations){evaluation ->
                ReviewItem(
                    message = evaluation.getKoreanType(),
                    count = evaluation.count
                )
            }
        }
    }
}

@Preview
@Composable
private fun ReviewUiTest(){
    ReviewEvaluationScreen(
        uiState = ReviewEvaluationUiState.Success(
            reviewEvaluations = GetReviewEvaluationsResponseDto(
                evaluationCount = 14,
                evaluationResponse = listOf(
                    EvaluationItem(
                        type = PersonalityType.GOOD_ATTACHMENT.name,
                        count = 5
                    ),
                    EvaluationItem(
                        type = PersonalityType.KIND.name,
                        count = 3
                    ),
                    EvaluationItem(
                        type = TravelPreferenceForReview.PLANNED.name,
                        count = 3
                    ),
                    EvaluationItem(
                        type = PersonalityType.PASSIONATE.name,
                        count = 2
                    ),
                    EvaluationItem(
                        type = PersonalityType.RATIONAL.name,
                        count = 1
                    ),
                )
            ),
        ),
        errState = ErrorState.Loading,
        navBack = {}
    )
}