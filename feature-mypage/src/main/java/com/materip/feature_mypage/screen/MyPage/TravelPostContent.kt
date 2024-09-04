package com.materip.feature_mypage.screen.MyPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.ItemSnapshotList
import androidx.paging.compose.collectAsLazyPagingItems
import com.materip.core_common.toDisplayString
import com.materip.core_designsystem.component.NoDataContent
import com.materip.core_designsystem.component.TravelPostItem
import com.materip.core_model.accompany_board.BoardItem
import com.materip.core_model.ui_model.TempTravelPost
import com.materip.feature_mypage.view_models.MyPage.TravelPostUiState
import com.materip.feature_mypage.view_models.MyPage.TravelPostViewModel

@Composable
fun TravelPostContent(
    viewModel: TravelPostViewModel = hiltViewModel()
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorState.collectAsStateWithLifecycle()
    val posts = viewModel.applicationPagingSource().collectAsLazyPagingItems()
    when(uiState.value){
        TravelPostUiState.Loading -> {
            CircularProgressIndicator()
        }
        TravelPostUiState.Error -> {
            Text(
                text = "Error",
                fontSize = 100.sp,
                color = Color.Red
            )
        }
        TravelPostUiState.Success -> {
            TravelPostMainContent(
                posts = posts.itemSnapshotList
            )
        }
    }
}

@Composable
private fun TravelPostMainContent(
    posts: ItemSnapshotList<BoardItem>
){
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
                        postImage = post.imageUrls[0],
                        onClick = { /** 동행글 description으로 이동? */ }
                    )
                }
            }
        }
    }
}