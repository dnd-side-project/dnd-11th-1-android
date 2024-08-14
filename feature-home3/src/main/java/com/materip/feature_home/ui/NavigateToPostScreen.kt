package com.materip.feature_home.ui

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.materip.feature_home.intent.HomeIntent
import com.materip.feature_home.state.HomeUiState
import com.materip.feature_home.viewModel.HomeHiltViewModel

@Composable
fun NavigateToPostScreen(
    boardId: Int,
    viewModel: HomeHiltViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // 게시글 상세 정보를 로드하는 인텐트 처리
    LaunchedEffect(boardId) {
        viewModel.onIntent(HomeIntent.LoadBoardDetail(boardId))
    }

    // BoardInfo.imageUris를 보여주는 UI

    // GetBoardDetailDto.profileInfo.nickname, birthYear, gender를 보여주는 UI (프로필 미리보기)

    // BoardInfo.title, content, tagNames를 보여주는 UI (게시글 제목, 내용, 태그)

    // BoardInfo.region, startDate, endDate를 보여주는 UI (동행 일정)

    // BoardInfo.category를 보여주는 UI (동행 유형)

    // BoardInfo.preferredAge, preferredGender를 보여주는 UI (선호 동행자)

    // BoardInfo.headCount, capacity를 보여주는 UI (모집 인원)

    when (uiState) {
        is HomeUiState.Loading -> CircularProgressIndicator()
        is HomeUiState.SuccessLoad -> {
            // 게시글 상세 정보 표시
//            val boardDetail = (uiState as HomeUiState.Success).boardDetail
//            Text("게시글 제목: ${boardDetail?.title}")
            // 나머지 UI 구성 요소들
        }
        is HomeUiState.Error -> Text("오류: \\${(uiState as HomeUiState.Error).message}")
        else -> {}
    }
}

//@Composable
//fun


@Preview
@Composable
fun NavigateToPostScreenPreview() {
    NavigateToPostScreen(
        boardId = 1
    )
}