package com.materip.feature_home3.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.materip.feature_home3.state.ProfileUiState
import com.materip.feature_home3.ui.component.ProfileTopAppBar
import com.materip.feature_home3.ui.component.TabRowComponent
import com.materip.feature_home3.ui.profile_content.ProfileContent
import com.materip.feature_home3.ui.profile_content.QnaContent
import com.materip.feature_home3.viewModel.ProfileViewModel
import com.materip.feature_mypage.view_models.MyPage.ReviewListViewModel


@Composable
fun ProfileScreen(
    navBack: () -> Unit,
    navReviewDescription: (Int) -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    reviewViewModel: ReviewListViewModel = hiltViewModel()
) {
    val uiState by profileViewModel.uiState.collectAsState()
    val userNickname by profileViewModel.userNickname.collectAsState()

    val tabs = listOf("프로필", "동행후기", "백문백답")
    val contentScreens = listOf<@Composable () -> Unit>(
        { ProfileContent(viewModel = profileViewModel) },
        {
            ReviewScreen(
                navBack = navBack,
                navReviewDescription = navReviewDescription,
                viewModel = reviewViewModel
            )
        },
        { QnaContent() }
    )

    when (uiState) {
        is ProfileUiState.Loading -> {
            CircularProgressIndicator()
        }

        is ProfileUiState.Success, ProfileUiState.Initial -> {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                ProfileTopAppBar(
                    screenTitle = userNickname,
                    onNavigateUp = navBack
                )
                TabRowComponent(
                    tabs = tabs,
                    contentScreens = contentScreens
                )
            }
        }

        is ProfileUiState.Error -> {
            Text("오류: ${(uiState as ProfileUiState.Error).message}")
        }
    }
}