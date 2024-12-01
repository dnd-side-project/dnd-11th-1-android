package com.materip.feature_home3.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.materip.core_designsystem.icon.Badges.fab_add_badge

@Composable
fun FabButton(
    currentBackStack: NavBackStackEntry?,
    onPostClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentRoute = remember{ currentBackStack?.destination?.route }
    if (currentRoute == "home") {
        Image(
            painter = painterResource(id = fab_add_badge),
            contentDescription = "동행 게시글 작성 버튼",
            modifier = modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false, radius = 24.dp),
                    onClick = onPostClick
                )
        )
    }
}