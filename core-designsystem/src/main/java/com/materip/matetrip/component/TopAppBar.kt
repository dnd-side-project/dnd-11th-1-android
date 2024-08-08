@file:OptIn(ExperimentalMaterial3Api::class)

package com.materip.matetrip.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.materip.matetrip.icon.Icons
import com.materip.matetrip.icon.Icons.notification_icon
import com.materip.matetrip.icon.Logo.splash_icon_02
import com.materip.matetrip.theme.MateTripTypographySet
import com.materip.matetrip.theme.Primary


/**
 * 상단바가 스크롤될 때 고정되는 동작을 설정하고, 이를 Modifier에 적용하여 상단바의 스크롤 동작을 정의
 * pinnedScrollBehavior: 상단바가 스크롤될 때 고정되는 동작
 * rememberTopAppBarState: 상단바의 상태를 기억하는 상태 객체
 * nestedScrollConnection: 상단바의 스크롤 동작을 제어하는 객체
 */

// 홈 화면의 상단바
@Composable
fun MateTripTopAppBar(
    onNavigateUp: () -> Unit
) {
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

// 다른 화면의 상단바
@Composable
fun BackButtonWithTitleTopAppBar(
    screenTitle: String,
    onNavigateUp: () -> Unit
) {
    val shouldShowActions = true // 임시 조건식에 따라 액션 버튼을 표시할지 여부를 결정

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
            if (shouldShowActions) {
                Row(
                    modifier = Modifier.padding(end = 16.dp),
                ) {
                    ClickableText(
                        text = AnnotatedString("게시"),
                        style = MateTripTypographySet.body04,
                        onClick = { /* 텍스트 클릭 시 동작 */ }
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
fun BackButtonTopAppBar(onNavigateUp: () -> Unit) {
    TopAppBar(
        title = { /* 빈 타이틀을 설정 */ },
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

@Preview
@Composable
fun MateTripTopAppBarPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MateTripTopAppBar(
            onNavigateUp = { }
        )
        Spacer(modifier = Modifier.size(16.dp))
        BackButtonWithTitleTopAppBar(
            screenTitle = "동행 모집하기",
            onNavigateUp = { }
        )
        Spacer(modifier = Modifier.size(16.dp))

        BackButtonTopAppBar(
            onNavigateUp = { }
        )
    }
}