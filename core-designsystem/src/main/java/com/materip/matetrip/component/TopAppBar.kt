@file:OptIn(ExperimentalMaterial3Api::class)

package com.materip.core_designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.icon.Icons.notification_icon
import com.materip.core_designsystem.icon.Logo.splash_icon_02
import com.materip.core_designsystem.theme.MateTripColors.Blue_02
import com.materip.core_designsystem.theme.MateTripTypographySet


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
                    tint = Blue_02

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
    isPostButtonEnabled: Boolean
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
                    enabled = isPostButtonEnabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                    ),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Text(
                        text = "게시",
                        style = MateTripTypographySet.body04,
                        color = if (isPostButtonEnabled) Color.Black else Color.Gray
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