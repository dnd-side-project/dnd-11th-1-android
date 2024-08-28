package com.materip.feature_home3.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.materip.core_designsystem.component.MateTripSearchBar
import com.materip.core_designsystem.component.RegionTag
import com.materip.core_designsystem.icon.Badges.fab_add_badge
import com.materip.core_designsystem.icon.Logo
import com.materip.core_designsystem.theme.MateTripColors.Blue_02
import com.materip.core_designsystem.theme.MateTripColors.Blue_04
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.core_model.accompany_board.BoardItem
import com.materip.feature_home3.intent.BoardListIntent
import com.materip.feature_home3.state.BoardListUiState
import com.materip.feature_home3.ui.component.toDisplayString
import com.materip.feature_home3.viewModel.BoardViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToPostDetail: (Int) -> Unit,
    viewModel: BoardViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val pageable by viewModel.pageable.collectAsState()
    val boardListState = viewModel.boardList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(BoardListIntent.LoadBoardList(pageable))
    }

    var selectedRegion by remember { mutableStateOf("전체") }

    val filteredBoardItems = if (selectedRegion == "전체") {
        boardListState.value.data
    } else {
        boardListState.value.data.filter { it.region.toDisplayString() == selectedRegion }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White, Blue_04, Blue_02)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // 상단 타이틀
        HomeTitle()

        // 동행글 검색바
        MateTripSearchBar()

        // 지역으로 태그해서 동행글 보여주기
        CompanionLounge(onRegionSelected = { selectedRegion = it })

        // 동행글 목록
        Box(modifier = Modifier.weight(1f)) {
            when (uiState) {
                is BoardListUiState.Loading -> {
                    // 로딩 상태일 때 더미 데이터로 UI 표시
                    ShowAccompanyPost(
                        boardItems = BoardListUiState.Loading.dummyData,
                        onPostClick = onNavigateToPostDetail
                    )
                }
                is BoardListUiState.Success -> {
                    ShowAccompanyPost(
                        boardItems = filteredBoardItems,
                        onPostClick = onNavigateToPostDetail
                    )
                }
                is BoardListUiState.Error -> {
                    // 오류 상태일 때 더미 데이터로 UI 표시
                    ShowAccompanyPost(
                        boardItems = BoardListUiState.Error("").dummyData,
                        onPostClick = onNavigateToPostDetail
                    )
                }
                else -> {
                    // ELSE 상태일 때 더미 데이터로 보여줄 UI
                    ShowAccompanyPost(
                        boardItems = (uiState as BoardListUiState.Initial).dummyData,
                        onPostClick = onNavigateToPostDetail
                    )
                }
            }
        }
    }
}


@Composable
fun HomeTitle() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 83.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "동행의 즐거움,\n당신의 여행을 특별하게!",
            style = MateTripTypographySet.displayLarge01,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}

@Composable
fun CompanionLounge(onRegionSelected: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "동행 라운지",
            style = MateTripTypographySet.headline06,
            modifier = Modifier.padding(start = 20.dp, end = 272.dp, bottom = 12.dp)
        )
        RegionTag(
            onClick = onRegionSelected,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PostItem(
    boardItem: BoardItem,
    onBoardItemClick: (Int) -> Unit
) {
    val firstImage: String = boardItem.imageUrls.firstOrNull() ?: Logo.app_icon_60.toString()
    val duration = calculateDuration(boardItem.startDate, boardItem.endDate)

    Row(
        modifier = Modifier
            .shadow(
                elevation = 10.dp,
                spotColor = Color(0x330E1537),
                ambientColor = Color(0x330E1537)
            )
            .width(360.dp)
            .height(148.dp)
            .background(color = Color.White, shape = RoundedCornerShape(size = 12.dp))
            .padding(18.dp)
            .clickable { onBoardItemClick(boardItem.boardId) },
        horizontalArrangement = Arrangement.spacedBy(50.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // 왼쪽 텍스트 부분
        Column(
            modifier = Modifier
                .width(155.dp)
                .height(111.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            // 태그 및 기간
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .width(108.dp)
                    .height(26.dp),
            ) {
                Text(
                    text = boardItem.region.toDisplayString(),
                    color = Color.White,
                    style = MateTripTypographySet.title05,
                    modifier = Modifier
                        .width(42.dp)
                        .height(26.dp)
                        .background(
                            color = Color(0xFF3553F2),
                            shape = RoundedCornerShape(size = 5.dp)
                        )
                        .padding(start = 10.dp, top = 4.dp, end = 10.dp, bottom = 5.dp)
                )
                Text(
                    text = duration,
                    style = MateTripTypographySet.title05,
                    modifier = Modifier
                        .width(66.dp)
                        .height(26.dp)
                        .background(
                            color = Color(0xFFEFF1FF),
                            shape = RoundedCornerShape(size = 5.dp)
                        )
                        .padding(start = 10.dp, top = 4.dp, end = 10.dp, bottom = 5.dp)
                )
            }
            Text(
                text = boardItem.title,
                style = MateTripTypographySet.headline05,
            )
            Text(
                text = "${boardItem.startDate} ~ ${boardItem.endDate}",
                style = MateTripTypographySet.title04,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = boardItem.nickname,
                style = MateTripTypographySet.body06,
            )
        }

        Image(
            painter = rememberAsyncImagePainter(firstImage),
            contentDescription = "image description",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 110.dp, height = 112.dp)
                .clip(RoundedCornerShape(10.dp))
        )
    }
}


@Composable
fun ShowAccompanyPost(
    boardItems: List<BoardItem>,
    onPostClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(boardItems) { boardItem ->
            PostItem(
                boardItem = boardItem,
                onBoardItemClick = { boardId -> onPostClick(boardId) }
            )
        }
    }
}

@Composable
fun FabButton(
    onPostClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onPostClick,
        containerColor = Color.Transparent,
        contentColor = Color.Transparent,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = fab_add_badge),
            contentDescription = "동행 게시글 작성 버튼"
        )
    }
}

fun calculateDuration(startDate: String, endDate: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val start = LocalDate.parse(startDate, formatter)
    val end = LocalDate.parse(endDate, formatter)
    val days = ChronoUnit.DAYS.between(start, end).toInt() + 1
    val nights = days - 1
    return "${nights}박 ${days}일"
}