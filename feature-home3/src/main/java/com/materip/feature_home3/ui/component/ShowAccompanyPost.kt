package com.materip.feature_home3.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materip.core_model.accompany_board.all.BoardItem

@Composable
fun ShowAccompanyPost(
    boardItems: List<BoardItem>,
    onPostClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp),
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