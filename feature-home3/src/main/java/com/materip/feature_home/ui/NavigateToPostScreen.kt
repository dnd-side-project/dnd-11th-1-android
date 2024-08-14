package com.materip.feature_home.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.materip.feature_home.R
import com.materip.feature_home.intent.HomeIntent
import com.materip.feature_home.state.HomeUiState
import com.materip.feature_home.viewModel.HomeHiltViewModel
import com.materip.matetrip.icon.Logo
import com.materip.matetrip.theme.MateTripColors.Blue_04

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

    when (uiState) {
        is HomeUiState.Loading ->  {
            CircularProgressIndicator()
        }
        is HomeUiState.SuccessLoad -> {
            val boardInfo = (uiState as HomeUiState.SuccessLoad).boardDetail.boardInfo
            // BoardInfo.imageUris를 보여주는 UI (이미지 보기)
            ShowImageList(imageUris = boardInfo.imageUris)

            // GetBoardDetailDto.profileInfo.nickname, birthYear, gender를 보여주는 UI (프로필 미리보기)

            // BoardInfo.title, content, tagNames를 보여주는 UI (게시글 제목, 내용, 태그)

            // BoardInfo.region, startDate, endDate를 보여주는 UI (동행 일정)

            // BoardInfo.category를 보여주는 UI (동행 유형)

            // BoardInfo.preferredAge, preferredGender를 보여주는 UI (선호 동행자)

            // BoardInfo.headCount, capacity를 보여주는 UI (모집 인원)
        }

        is HomeUiState.Error -> {
            Text("오류: \\${(uiState as HomeUiState.Error).message}")
        }
        else -> {}
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowImageList(imageUris: List<String>) {
    val pagerState = rememberPagerState(pageCount = { imageUris.size })
    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            currentIndex = page
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val imageUri = imageUris[page]
            if (imageUri.startsWith("http://") || imageUri.startsWith("https://")) {
                // 네트워크 이미지 처리
                SubcomposeAsyncImage(
                    model = imageUri,
                    contentDescription = "Network Image $page",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Blue_04)
                ) {
                    when (painter.state) {
                        is AsyncImagePainter.State.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        is AsyncImagePainter.State.Error -> {
                            Text(
                                text = "Error loading image",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        else -> SubcomposeAsyncImageContent()
                    }
                }
            } else {
                // 로컬 리소스 이미지 처리
                val resourceId = imageUri.toIntOrNull() ?: R.drawable.default_image
                Image(
                    painter = painterResource(id = resourceId),
                    contentDescription = "Local Image $page",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Blue_04)
                )
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .width(46.dp)
                .height(6.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            imageUris.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(
                            color = if (index == currentIndex) Color.Black else Color.Gray,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}


@Preview
@Composable
fun NavigateToPostScreenPreview() {
    val imageList = List(5) { Logo.sample_image.toString() }
    ShowImageList(
        imageUris = imageList
    )
}