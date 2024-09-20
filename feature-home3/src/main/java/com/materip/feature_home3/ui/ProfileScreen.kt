package com.materip.feature_home3.ui

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.materip.feature_home3.state.ProfileUiState
import com.materip.feature_home3.ui.component.TabRowComponent
import com.materip.feature_home3.ui.profile_content.ProfileContent
import com.materip.feature_home3.ui.profile_content.QnaContent
import com.materip.feature_home3.viewModel.ProfileViewModel


@Composable
fun ProfileScreen(
    boardId: Int,
    navBack: () -> Unit,
    navReviewDescription: (Int) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    val tabs = listOf("프로필", "동행후기", "백문백답")
    val contentScreens = listOf<@Composable () -> Unit>(
        { ProfileContent() },
        { ReviewScreen(navBack = navBack, navReviewDescription = navReviewDescription) },
        { QnaContent() }
    )

    LaunchedEffect(boardId) {
        viewModel.getProfileDetails()
    }

    when (uiState) {
        is ProfileUiState.Loading -> {
            CircularProgressIndicator()
        }

        is ProfileUiState.Success -> {
            TabRowComponent(
                tabs = tabs,
                contentScreens = contentScreens
            )
        }

        is ProfileUiState.Error -> {
            Text("오류: ${(uiState as ProfileUiState.Error).message}")
        }

        ProfileUiState.Initial -> {
            // Handle initial state if needed
        }
    }
}