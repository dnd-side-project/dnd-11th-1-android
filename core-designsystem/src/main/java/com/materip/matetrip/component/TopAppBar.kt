@file:OptIn(ExperimentalMaterial3Api::class)

package com.materip.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.icon.Icons.notification_icon
import com.materip.core_designsystem.icon.Logo.splash_icon_02
import com.materip.core_designsystem.theme.MateTripColors.Primary
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.matetrip.component.DeleteBox


/**
 * 상단바가 스크롤될 때 고정되는 동작을 설정하고, 이를 Modifier에 적용하여 상단바의 스크롤 동작을 정의
 * pinnedScrollBehavior: 상단바가 스크롤될 때 고정되는 동작
 * rememberTopAppBarState: 상단바의 상태를 기억하는 상태 객체
 * nestedScrollConnection: 상단바의 스크롤 동작을 제어하는 객체
 */

// 홈 화면의 상단바
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MateTripTopAppBar(
    onNotificationClick: () -> Unit
) {
    TopAppBar(
        title = {
            Icon(
                painter = painterResource(id = splash_icon_02),
                contentDescription = "Home Icon",
                modifier = Modifier.size(122.dp, 36.dp),
                tint = Color.Unspecified
            )
        },
        actions = {
            IconButton(onClick = onNotificationClick) {
                Icon(
                    painter = painterResource(id = notification_icon),
                    contentDescription = "Notifications",
                    tint = Primary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}

@Composable
fun BackButtonWithTitleTopAppBar(
    screenTitle: String,
    onNavigateUp: () -> Unit,
    onPostClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = screenTitle,
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
                Button(
                    onClick = onPostClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent
                    )
                ) {
                    Text(
                        text = "게시",
                        style = MateTripTypographySet.body04,
                        color = Color.Black
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}

// 뒤로가기 버튼만 있는 상단바
@Composable
fun BackButtonTopAppBar(
    screenTitle: String,
    onNavigateUp: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = screenTitle,
                style = MateTripTypographySet.headline06
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    painter = painterResource(id = Icons.arrow_back_icon),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}

@Composable
fun BoardDetailTopAppBar(
    onNavigateUp: () -> Unit,
    showDialogState: MutableState<Boolean>
) {
    var showDeleteBox by remember { mutableStateOf(false) }

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
                IconButton(onClick = { showDeleteBox = !showDeleteBox }) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.MoreVert,
                        contentDescription = "삭제하기"
                    )
                }
                if (showDeleteBox) {
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
            containerColor = Color.White
        )
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewBoardDetailTopAppBar() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        BoardDetailTopAppBar(
            onNavigateUp = { /* Handle navigate up action */ },
            showDialogState = remember { mutableStateOf(false) }
        )

        var showDialogState by remember { mutableStateOf(false) }

        if (showDialogState) {
            AlertDialog(
                onDismissRequest = { showDialogState = false },
                confirmButton = {
                    Row(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        MateTripHomeButton(
                            buttonText = "아니요",
                            enabled = true,
                            onClick = { showDialogState = false },
                            modifier = Modifier
                                .width(143.dp)
                                .height(54.dp)
                        )
                        MateTripHomeButton(
                            buttonText = "예",
                            enabled = true,
                            onClick = {
                                showDialogState = false
                            },
                            modifier = Modifier
                                .width(143.dp)
                                .height(54.dp)
                        )
                    }
                },
                text = {
                    Box(
                        modifier = Modifier
                            .height(80.dp)
                            .width(320.dp)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "정말 게시글을 삭제하시나요?",
                            style = MateTripTypographySet.title03,
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                containerColor = Color.White,
                shape = RoundedCornerShape(size = 10.dp)
            )
        }
    }
}