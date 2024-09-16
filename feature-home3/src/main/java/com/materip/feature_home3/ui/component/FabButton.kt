package com.materip.feature_home3.ui.component

import androidx.compose.foundation.Image
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.materip.core_designsystem.icon.Badges.fab_add_badge

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