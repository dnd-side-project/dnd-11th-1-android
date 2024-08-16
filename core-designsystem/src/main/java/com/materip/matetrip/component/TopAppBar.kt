@file:OptIn(ExperimentalMaterial3Api::class)

package com.materip.matetrip.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.materip.matetrip.icon.Icons
import com.materip.matetrip.icon.Icons.notification_icon
import com.materip.matetrip.icon.Logo.splash_icon_02
import com.materip.matetrip.theme.MateTripColors.Primary
import com.materip.matetrip.theme.MateTripTypographySet


/**
 * 상단바가 스크롤될 때 고정되는 동작을 설정하고, 이를 Modifier에 적용하여 상단바의 스크롤 동작을 정의
 * pinnedScrollBehavior: 상단바가 스크롤될 때 고정되는 동작
 * rememberTopAppBarState: 상단바의 상태를 기억하는 상태 객체
 * nestedScrollConnection: 상단바의 스크롤 동작을 제어하는 객체
 */

// 홈 화면의 상단바
@Composable
fun MateTripTopAppBar() {
    TopAppBar(
        title = {
            Icon(
                painter = painterResource(id = splash_icon_02),
                contentDescription = "Home Icon",
                modifier = Modifier.size(122.dp, 36.dp),
                tint = Color.Unspecified // 투명으로 설정하여 아이콘의 색상을 유지
            )
        },
        actions = {
            IconButton(onClick = { /* 알림 설정 시 알림을 표시하기 */ }) {
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
    TopAppBar(
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