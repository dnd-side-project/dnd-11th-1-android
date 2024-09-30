package com.materip.feature_home3.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.matetrip.component.DeleteBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoardDetailTopAppBar(
    onNavigateUp: () -> Unit,
    showDialogState: MutableState<Boolean>
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "",
                style = MateTripTypographySet.headline06
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    painter = painterResource(id = Icons.arrow_back_icon),
                    contentDescription = "뒤로 가기 아이콘",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
            }
        },
        actions = {
            Row(
                modifier = Modifier.padding(end = 10.dp),
            ) {
                IconButton(onClick = { showDialogState.value = !showDialogState.value }) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.MoreVert,
                        contentDescription = "삭제하기"
                    )
                }
                if (showDialogState.value) {
                    Popup(
                        alignment = Alignment.BottomEnd,
                        offset = IntOffset(-20, 120)
                    ) {
                        DeleteBox(
                            onDeleteConfirm = { showDialogState.value = true }
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent ,
            navigationIconContentColor = Color.Black
        ),
        windowInsets = WindowInsets(0.dp)
    )
}