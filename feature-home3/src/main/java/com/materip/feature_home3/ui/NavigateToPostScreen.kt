@file:OptIn(ExperimentalLayoutApi::class)

package com.materip.feature_home3.ui

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.materip.core_designsystem.component.MateTripHomeButton
import com.materip.core_designsystem.icon.Badges.profile_default_badge
import com.materip.core_designsystem.icon.Icons.date_icon
import com.materip.core_designsystem.icon.Icons.enter_24_icon
import com.materip.core_designsystem.icon.Icons.gender_icon
import com.materip.core_designsystem.icon.Icons.party_icon
import com.materip.core_designsystem.icon.Icons.place_icon
import com.materip.core_designsystem.theme.MateTripColors.Blue_03
import com.materip.core_designsystem.theme.MateTripColors.Blue_04
import com.materip.core_designsystem.theme.MateTripColors.Gray_02
import com.materip.core_designsystem.theme.MateTripColors.Gray_10
import com.materip.core_designsystem.theme.MateTripColors.Gray_11
import com.materip.core_designsystem.theme.MateTripColors.Gray_12
import com.materip.core_designsystem.theme.MateTripColors.Primary
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.feature_home3.intent.HomeIntent
import com.materip.feature_home3.state.HomeUiState
import com.materip.feature_home3.viewModel.HomeViewModel

/**
 * param: onNavigateToForm 동행신청
 * param: onNavigateToUserProfile 유저프로필상세보기
 * */
@Composable
fun NavigateToPostScreen(
    boardId: Int,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToForm: () -> Unit,
    onNavigateToUserProfile: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(boardId) {
        viewModel.onHomeIntent(HomeIntent.LoadBoardDetail(boardId))
    }

    when (uiState) {
        is HomeUiState.Loading -> {
            CircularProgressIndicator()
        }

        is HomeUiState.SuccessLoad -> {
            val boardInfo = (uiState as HomeUiState.SuccessLoad).boardDetail.boardInfo
            val profileInfo = (uiState as HomeUiState.SuccessLoad).boardDetail.profileThumbnail

            ShowImageList(imageUris = boardInfo.imageUris)

            ShowUserProfile(
                nickname = profileInfo.nickname,
                birthYear = profileInfo.birthYear,
                gender = profileInfo.gender,
                profileImageUrl = profileInfo.profileImageUrl,
                onNavigateToProfile = onNavigateToUserProfile
            )

            ShowUserBoardInfo(
                title = boardInfo.title,
                content = boardInfo.content,
                tagNames = boardInfo.tagNames
            )

            ShowSchedule(
                region = boardInfo.region,
                startDate = boardInfo.startDate,
                endDate = boardInfo.endDate
            )

            ShowCategory(category = boardInfo.category)

            ShowPreferredPerson(
                preferredAge = boardInfo.preferredAge,
                preferredGender = boardInfo.preferredGender,
                birthYear = profileInfo.birthYear,
                userGender = profileInfo.gender
            )

            ShowRecruitment(boardInfo.headCount, boardInfo.capacity)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MateTripHomeButton(
                    buttonText = "동행 신청",
                    enabled = true,
                    onClick = { onNavigateToForm() },
                    modifier = Modifier
                        .width(370.dp)
                        .height(54.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
        }

        is HomeUiState.Error -> {
            Text("오류: \\${(uiState as HomeUiState.Error).message}")
        }

        else -> {}
    }
}

@Composable
fun ShowUserProfile(
    profileImageUrl: String,
    nickname: String,
    birthYear: Int,
    gender: String,
    onNavigateToProfile: () -> Unit
) {
    val ageCategory = calculateAgeCategory(birthYear)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp)
            .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .background(shape = CircleShape, color = Blue_04)
        ) {
            if (profileImageUrl.isEmpty()) {
                Image(
                    painter = painterResource(id = profile_default_badge),
                    contentDescription = "프로필 기본 이미지",
                )
            } else {
                SubcomposeAsyncImage(
                    model = profileImageUrl,
                    contentDescription = "Network Image",
                    contentScale = ContentScale.Crop
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = nickname, style = MateTripTypographySet.title04)
            Text(
                text = "$ageCategory · $gender",
                style = MateTripTypographySet.body05, color = Gray_12
            )
        }

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(
                onClick = onNavigateToProfile,
                modifier = Modifier
                    .padding(top = 7.dp)
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = enter_24_icon),
                    contentDescription = "프로필 상세 화면으로 이동"
                )
            }
        }
    }

    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
        color = Color(0xFFEEEEEE)
    )
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
                val resourceId = imageUri.toIntOrNull() ?: com.materip.core_designsystem.R.drawable.profile_default
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
@Composable
fun ShowCategory(category: List<String>) {
    Column(
        modifier = Modifier.padding(top = 50.dp, start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "동행 유형", style = MateTripTypographySet.headline05)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        ) {
            category.forEach { category ->
                Box(
                    modifier = Modifier
                        .height(28.dp)
                        .background(color = Gray_02, shape = RoundedCornerShape(size = 6.dp))
                        .padding(start = 12.dp, top = 4.dp, end = 12.dp, bottom = 4.dp)
                ) {
                    Text(
                        text = category,
                        style = MateTripTypographySet.body04,
                        color = Gray_11
                    )
                }
            }
        }
    }
}

@Composable
fun ShowPreferredPerson(
    preferredAge: String,
    preferredGender: String,
    birthYear: Int,
    userGender: String
) {
    val ageCategory = calculateAgeCategory(birthYear)

    Column(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 50.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "선호 동행자", style = MateTripTypographySet.headline05)
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = party_icon),
                    contentDescription = "선호하는 동행자 나이",
                    tint = Primary
                )
                if (preferredAge == "상관없음") {
                    Text(text = preferredAge, style = MateTripTypographySet.title03)
                } else {
                    Text(text = ageCategory, style = MateTripTypographySet.title03)
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = gender_icon),
                    contentDescription = "선호하는 동행자 성별",
                    tint = Primary
                )
                if (preferredGender == "상관없음") {
                    Text(text = preferredGender, style = MateTripTypographySet.title03)
                } else {
                    Text(text = userGender, style = MateTripTypographySet.title03)
                }
            }
        }
    }
}

fun calculateAgeCategory(birthYear: Int): String {
    val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
    val age = currentYear - birthYear
    return when (age) {
        in 10..19 -> "10대"
        in 20..29 -> "20대"
        in 30..39 -> "30대"
        in 40..49 -> "40대"
        in 50..59 -> "50대"
        else -> "기타"
    }
}

@Composable
fun ShowRecruitment(headCount: Int, capacity: Int) {
    Column(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 50.dp, bottom = 40.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(240.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "모집 인원", style = MateTripTypographySet.headline05)
                Box(
                    modifier = Modifier
                        .width(41.dp)
                        .height(24.dp)
                        .background(color = Blue_04, shape = RoundedCornerShape(size = 54.dp))
                        .padding(start = 10.dp, top = 4.dp, end = 10.dp, bottom = 4.dp),
                ) {
                    Text(
                        text = "$headCount/$capacity",
                        style = MateTripTypographySet.numberRegular3,
                        color = Color(0xFF555555),
                        fontWeight = FontWeight(500)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun NavigateToPostScreenPreview() {
    val imageList = List(5) { com.materip.core_designsystem.R.drawable.profile_default.toString() }
    val category = listOf("전체동행", "부분동행", "식사동행")

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White),
    ) {
        item { ShowImageList(imageUris = imageList) }
        item {
            ShowUserProfile(
                nickname = "한산한 개구리",
                birthYear = 2003,
                gender = "남성",
                profileImageUrl = "",
                onNavigateToProfile = { }
            )
        }
        item {
            ShowUserBoardInfo(
                title = "광안리 초필살돼지껍데기 가실 분 구해요!",
                content = "광안리에서 초필살돼지껍데이 먹고 바로 앞에 해수욕장에서 서로 인생샷 남길 사람 구해요!",
                tagNames = listOf("#광안리", "#초필살돼지껍데기", "#인생샷")
            )
        }
        item {
            ShowSchedule(
                region = "부산",
                startDate = "2024.07.21",
                endDate = "2024.07.23"
            )
        }
        item { ShowCategory(category) }
        item {
            ShowPreferredPerson(
                preferredAge = "20대",
                preferredGender = "상관없음",
                birthYear = 2003,
                userGender = "남성"
            )
        }
        item { ShowRecruitment(headCount = 1, capacity = 4) }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MateTripHomeButton(
                    buttonText = "동행 신청",
                    enabled = true,
                    onClick = {  },
                    modifier = Modifier
                        .width(370.dp)
                        .height(54.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}