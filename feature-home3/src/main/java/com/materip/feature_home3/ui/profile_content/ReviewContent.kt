package com.materip.feature_home3.ui.profile_content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.materip.core_common.ErrorState
import com.materip.core_common.toDisplayAgeString
import com.materip.core_common.toDisplayDateString
import com.materip.core_common.toDisplayString
import com.materip.core_designsystem.component.ReviewDescItem
import com.materip.core_designsystem.component.ReviewItem
import com.materip.core_designsystem.icon.Icons.enter_24_icon
import com.materip.core_designsystem.theme.MateTripColors.Gray_04
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.core_model.response.EvaluationItem
import com.materip.core_model.response.ReviewItem
import com.materip.feature_mypage.view_models.MyPage.ReviewEvaluationUiState
import com.materip.feature_mypage.view_models.MyPage.ReviewListUiState
import com.materip.matetrip.component.DefaultLoadingComponent
import com.materip.matetrip.toast.ErrorView

@Composable
fun ReviewContent(
    navReviewDescription: (Int) -> Unit,
    navEvaluation: () -> Unit,
    reviewState: ReviewListUiState,
    reviewEvalState: ReviewEvaluationUiState,
    reviewErrState: ErrorState,
    reviewEvalErrState: ErrorState,
    navBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // 받은 동행 평가, 상세화면은 nav로 해결
        when (reviewEvalState) {
            is ReviewEvaluationUiState.Loading -> {
                DefaultLoadingComponent()
            }

            is ReviewEvaluationUiState.Success -> {
                ShowReceivedEvaluations(
                    totalCount = reviewEvalState.reviewEvaluations.evaluationCount,
                    reviewEvaluations = reviewEvalState.reviewEvaluations.evaluationResponse,
                    navEvaluation = navEvaluation
                )
            }

            is ReviewEvaluationUiState.Error -> {
                ErrorView(
                    errState = reviewEvalErrState,
                    navBack = navBack
                )
            }
        }
        // 받은 동행 후기, 상세화면은 nav로 해결
        when (reviewState) {
            is ReviewListUiState.Loading -> {
                CircularProgressIndicator()
            }

            is ReviewListUiState.Success -> {
                ShowReceivedReviews(
                    totalCount = reviewState.reviews.totalCount,
                    reviews = reviewState.reviews.responses,
                    navReviewDescription = navReviewDescription
                )
            }

            is ReviewListUiState.Error -> {
                ErrorView(
                    errState = reviewErrState,
                    navBack = navBack
                )
            }
        }
    }
}


@Composable
private fun ShowReceivedEvaluations(
    totalCount: Int,
    reviewEvaluations: List<EvaluationItem>,
    navEvaluation: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "받은 동행 평가 ($totalCount)",
                textAlign = TextAlign.Center,
                style = MateTripTypographySet.headline05
            )
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(
                    onClick = { navEvaluation() },
                    modifier = Modifier
                        .padding(top = 7.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = enter_24_icon),
                        contentDescription = "더보기 아이콘"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        // 동행 평가 아이템들
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (reviewEvaluations.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "아직 받은 동행 평가가 없어요.",
                        style = MateTripTypographySet.body03,
                        color = Gray_04
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(reviewEvaluations) { evaluation ->
                        ReviewItem(
                            message = evaluation.getKoreanType(),
                            count = evaluation.count
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShowReceivedReviews(
    totalCount: Int,
    reviews: List<ReviewItem>,
    navReviewDescription: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "받은 동행 후기 ($totalCount)",
                textAlign = TextAlign.Center,
                style = MateTripTypographySet.headline05
            )
            IconButton(
                onClick = { /* 동작 없음 */ },
                modifier = Modifier
                    .padding(top = 7.dp)
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = enter_24_icon),
                    contentDescription = "더보기 아이콘"
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (reviews.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "아직 받은 동행 후기가 없어요.",
                        style = MateTripTypographySet.body03,
                        color = Gray_04
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(reviews) { review ->
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
    }
}
