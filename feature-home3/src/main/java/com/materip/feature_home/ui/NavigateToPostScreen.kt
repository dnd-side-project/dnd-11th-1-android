@file:OptIn(ExperimentalLayoutApi::class)

package com.materip.feature_home.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import com.materip.matetrip.icon.Icons.date_icon
import com.materip.matetrip.icon.Icons.place_icon
import com.materip.matetrip.icon.Logo
import com.materip.matetrip.theme.MateTripColors.Blue_03
import com.materip.matetrip.theme.MateTripColors.Blue_04
import com.materip.matetrip.theme.MateTripColors.Gray_10
import com.materip.matetrip.theme.MateTripColors.Primary
import com.materip.matetrip.theme.MateTripTypographySet

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
        is HomeUiState.Loading -> {
            CircularProgressIndicator()
        }

        is HomeUiState.SuccessLoad -> {
            val boardInfo = (uiState as HomeUiState.SuccessLoad).boardDetail.boardInfo
            val profileInfo = (uiState as HomeUiState.SuccessLoad).boardDetail.profileInfo
            // BoardInfo.imageUris를 보여주는 UI (이미지 보기)
            ShowImageList(imageUris = boardInfo.imageUris)

            // GetBoardDetailDto.profileInfo.nickname, birthYear, gender, 프로필 이미지를 보여주는 UI (프로필 미리보기)
            ShowUserProfile(
                nickname = profileInfo.nickname,
                birthYear = profileInfo.birthYear,
                gender = profileInfo.gender
            )

            // BoardInfo.title, content, tagNames를 보여주는 UI (게시글 제목, 내용, 태그)
            ShowUserBoardInfo(
                title = boardInfo.title,
                content = boardInfo.content,
                tagNames = boardInfo.tagNames
            )

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

// TODO: ProfileInfo API 수정 중, 디자인 컴포넌트도 요청한 상태, 완료되면 작업하기
@Composable
fun ShowUserProfile(
    nickname: String,
    birthYear: Int,
    gender: String
) {
    Row {
        // 소셜 프로필 이미지를 받아오기

        Column {
            // 닉네임 받아오기

            // 나이를 받아와서 범주형으로 보여주기

            // 성별 보여주기
        }

        // 해당하는 유저의 프로필 상세 화면으로 이동하기
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

@Composable
fun ShowUserBoardInfo(
    title: String,
    content: String,
    tagNames: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = title,
            style = MateTripTypographySet.headline05
        )

        Text(
            text = content,
            style = MateTripTypographySet.body04,
            color = Gray_10
        )

        FlowRow(
            modifier = Modifier.padding(top = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top)
        ) {
            tagNames.forEach { tagName ->
                Box(
                    modifier = Modifier
                        .height(28.dp)
                        .background(color = Blue_04, shape = RoundedCornerShape(size = 6.dp))
                        .padding(start = 12.dp, top = 4.dp, end = 12.dp, bottom = 4.dp)
                ) {
                    Text(
                        text = tagName,
                        style = MateTripTypographySet.body04,
                        color = Gray_10
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun ShowSchedule(
    region: String,
    startDate: String,
    endDate: String
) {
    Column(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "동행 일정", style = MateTripTypographySet.headline05)
        Box(
            modifier = Modifier
                .border(width = 1.dp, color = Blue_03, shape = RoundedCornerShape(size = 10.dp))
                .height(86.dp)
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
                .width(320.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = place_icon),
                        contentDescription = "동행 장소",
                        tint = Primary
                    )
                    Text(text = region, style = MateTripTypographySet.title03)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = date_icon),
                        contentDescription = "동행 날짜",
                        tint = Primary
                    )
                    Text(
                        text = "$startDate - $endDate",
                        style = MateTripTypographySet.numberMedium2
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun NavigateToPostScreenPreview() {
    val imageList = List(5) { Logo.sample_image.toString() }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White),
    ) {
        ShowImageList(
            imageUris = imageList
        )
        ShowUserBoardInfo(
            title = "광안리 초필살돼지껍데기 가실 분 구해요!",
            content = "광안리에서 초필살돼지껍데이 먹고 바로 앞에 해수욕장에서 서로 인생샷 남길 사람 구해요!",
            tagNames = listOf("#광안리", "#초필살돼지껍데기", "#인생샷")
        )
        ShowSchedule(
            region = "부산",
            startDate = "2024.07.21",
            endDate = "2024.07.23"
        )
    }
}