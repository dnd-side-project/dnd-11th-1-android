package com.materip.feature_home3.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.materip.core_designsystem.component.MateTripSearchBar
import com.materip.core_designsystem.component.mapRegionNameToEnum
import com.materip.core_designsystem.theme.MateTripColors.Blue_02
import com.materip.core_designsystem.theme.MateTripColors.Blue_04
import com.materip.core_model.accompany_board.search.QueryRequestDto
import com.materip.core_model.request.PagingRequestDto
import com.materip.core_model.ui_model.Region
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
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val boardListState = viewModel.boardList.collectAsStateWithLifecycle()

    var query by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    var selectedRegion by remember { mutableStateOf<List<Region>?>(null) }

    var cursor by remember { mutableStateOf<String?>(null) }
    val size = 8

    val onSearch: (String) -> Unit = { searchQuery ->
        if (searchQuery.isNotEmpty()) {
            isSearching = true
            viewModel.handleIntent(BoardListIntent.SearchBoardList(QueryRequestDto(searchQuery)))
        }
    }

    val onClearSearch = {
        query = ""
        isSearching = false
        cursor = null
        val initialRequest = PagingRequestDto(cursor = null, size = size)
        viewModel.handleIntent(BoardListIntent.LoadBoardList(initialRequest))
    }

    LaunchedEffect(Unit) {
        val initialRequest = PagingRequestDto(cursor = null, size = size)
        viewModel.handleIntent(BoardListIntent.LoadBoardList(initialRequest))
    }

    val filteredBoardItems = remember(boardListState.value, selectedRegion, isSearching) {
        val allItems = boardListState.value?.data ?: emptyList()
        if (isSearching) {
            allItems
        } else {
            if (selectedRegion == null) {
                allItems
            } else {
                allItems.filter { regionItem ->
                    selectedRegion?.contains(regionItem.region) ?: true
                }
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
        CompanionLounge(
            onRegionSelected = { regionName ->
                selectedRegion = mapRegionNameToEnum(regionName)
            }
        )

        // 동행글 목록
        Box(
            modifier = Modifier.weight(1f)
        ) {
            when (uiState) {
                is BoardListUiState.Loading -> CircularProgressIndicator()

                is BoardListUiState.Success -> {
                    ShowAccompanyPost(
                        boardItems = filteredBoardItems,
                        onPostClick = onNavigateToPostDetail
                    )
                }

                is BoardListUiState.Error -> {
                    val errorMessage = (uiState as? BoardListUiState.Error)?.message ?: "알 수 없는 오류"
                    Text("오류: $errorMessage")
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