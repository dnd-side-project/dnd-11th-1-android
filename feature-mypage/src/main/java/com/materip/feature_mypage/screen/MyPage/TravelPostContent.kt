package com.materip.feature_mypage.screen.MyPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.materip.core_common.toDisplayString
import com.materip.core_designsystem.component.NoDataContent
import com.materip.core_designsystem.component.TravelPostItem
import com.materip.core_model.accompany_board.all.BoardItem
import com.materip.feature_mypage.view_models.MyPage.TravelPostUiState
import com.materip.feature_mypage.view_models.MyPage.TravelPostViewModel
import com.materip.matetrip.component.DefaultLoadingComponent
import com.materip.matetrip.toast.ErrorView

@Composable
fun TravelPostContent(
    navBack: () -> Unit,
    viewModel: TravelPostViewModel = hiltViewModel()
){
    val errState by viewModel.errorState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val posts = viewModel.applicationPagingSource().collectAsLazyPagingItems()
    when(uiState){
        TravelPostUiState.Loading -> {
            DefaultLoadingComponent()
        }
        TravelPostUiState.Error -> {
            ErrorView(
                errState = errState,
                navBack = navBack
            )
        }
        is TravelPostUiState.Success -> {
            TravelPostMainContent(posts = posts)
        }
    }
}

@Composable
private fun TravelPostMainContent(
    posts: LazyPagingItems<BoardItem>
){
    val posts = posts.itemSnapshotList
    if (posts.isEmpty()){
        NoDataContent(message = "첫 동행글을 작성해보세요.")
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(posts){post ->
                if (post != null){
                    TravelPostItem(
                        modifier = Modifier.fillMaxWidth(),
                        destination = post.region.toDisplayString(),
                        period = post.getDuration(),
                        title = post.title,
                        startDate = post.getStartDateText(),
                        endDate = post.getEndDateText(),
                        postImage = if(post.imageUrls.isNotEmpty()) post.imageUrls[0] else null,
                        onClick = { /** 동행글 description으로 이동? */ }
                    )
                }
            }
        }
    }
}