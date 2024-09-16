package com.materip.feature_home3.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.materip.core_common.toDisplayString
import com.materip.core_designsystem.component.MateTripSearchBar
import com.materip.core_designsystem.theme.MateTripColors.Blue_02
import com.materip.core_designsystem.theme.MateTripColors.Blue_04
import com.materip.core_model.accompany_board.search.QueryRequestDto
import com.materip.core_model.request.PagingRequestDto
import com.materip.feature_home3.intent.BoardListIntent
import com.materip.feature_home3.state.BoardListUiState
import com.materip.feature_home3.ui.component.CompanionLounge
import com.materip.feature_home3.ui.component.HomeTitle
import com.materip.feature_home3.ui.component.ShowAccompanyPost
import com.materip.feature_home3.viewModel.BoardViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToPostDetail: (Int) -> Unit,
    viewModel: BoardViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val boardListState = viewModel.boardList.collectAsState()

    val listState = rememberLazyListState()
    var isLoading by remember { mutableStateOf(false) }
    var cursor: String? by remember { mutableStateOf(null) }
    val size = 8

    var query by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    var selectedRegion by remember { mutableStateOf("전체") }

    val onSearch: (String) -> Unit = { searchQuery ->
        if (searchQuery.isNotEmpty()) {
            isSearching = true
            viewModel.handleIntent(BoardListIntent.SearchBoardList(QueryRequestDto(searchQuery)))
        }
    }

    val onClearSearch = {
        query = ""
        isSearching = false
        val initialRequest = PagingRequestDto(cursor = null, size = size)
        viewModel.handleIntent(BoardListIntent.LoadBoardList(initialRequest))
    }

    LaunchedEffect(Unit) {
        val initialRequest = PagingRequestDto(cursor = null, size = size)
        viewModel.handleIntent(BoardListIntent.LoadBoardList(initialRequest))
    }

    val filteredBoardItems = if (isSearching) {
        boardListState.value?.data ?: emptyList()
    } else {
        if (selectedRegion == "전체") {
            boardListState.value?.data ?: emptyList()
        } else {
            boardListState.value?.data?.filter { it.region.toDisplayString() == selectedRegion }
                ?: emptyList()
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                val totalItemCount = listState.layoutInfo.totalItemsCount
                val boardListResponse = boardListState.value
                if (
                    lastVisibleItemIndex != null &&
                    lastVisibleItemIndex >= totalItemCount - 1 &&
                    !isLoading &&
                    boardListResponse?.hasNext == true
                ) {
                    isLoading = true
                    cursor = boardListResponse.cursor
                    val pagingRequestDto = PagingRequestDto(cursor = cursor, size = size)
                    viewModel.handleIntent(BoardListIntent.LoadBoardList(pagingRequestDto))
                    isLoading = false
                }
            }
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
        MateTripSearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = onSearch,
            onClear = onClearSearch
        )

        // 지역으로 태그해서 동행글 보여주기
        CompanionLounge(onRegionSelected = { selectedRegion = it })

        // 동행글 목록
        Box(modifier = Modifier.weight(1f)) {
            when (uiState) {
                is BoardListUiState.Loading -> CircularProgressIndicator()

                is BoardListUiState.Success -> {
                    ShowAccompanyPost(
                        boardItems = filteredBoardItems,
                        onPostClick = onNavigateToPostDetail
                    )
                }

                is BoardListUiState.Error -> {
                    Text("오류: ${(uiState as BoardListUiState.Error).message}")
                }

                else -> {
                    ShowAccompanyPost(
                        boardItems = filteredBoardItems,
                        onPostClick = onNavigateToPostDetail
                    )
                }
            }
        }
    }
}