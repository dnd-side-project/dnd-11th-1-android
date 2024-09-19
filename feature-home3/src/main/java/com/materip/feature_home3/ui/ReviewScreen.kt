package com.materip.feature_home3.ui

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.materip.feature_home3.state.ReviewUiState
import com.materip.feature_home3.ui.profile_content.ReviewContent
import com.materip.feature_mypage.view_models.MyPage.ReviewListUiState
import com.materip.feature_mypage.view_models.MyPage.ReviewListViewModel

@Composable
fun ReviewScreen(
    navBack: () -> Unit,
    navReviewDescription: (Int) -> Unit,
    viewModel: ReviewListViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState.value) {
        is ReviewListUiState.Loading -> {
            CircularProgressIndicator()
        }

        is ReviewListUiState.Success -> {
            ReviewContent(
                reviews = state.reviews.responses,
                totalCount = state.reviews.totalCount,
                navBack = navBack,
                navReviewDescription = navReviewDescription
            )
        }

        is ReviewListUiState.Error -> {
            Text("오류: ${(state as ReviewUiState.Error).message}")
        }
    }
}