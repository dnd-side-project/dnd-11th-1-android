@file:OptIn(ExperimentalMaterial3Api::class)

package com.materip.matetrip.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.materip.matetrip.icon.Icons.notification_icon
import com.materip.matetrip.icon.Logo.splash_icon_02
import com.materip.matetrip.theme.Primary


/**
 * 상단바가 스크롤될 때 고정되는 동작을 설정하고, 이를 Modifier에 적용하여 상단바의 스크롤 동작을 정의
 * pinnedScrollBehavior: 상단바가 스크롤될 때 고정되는 동작
 * rememberTopAppBarState: 상단바의 상태를 기억하는 상태 객체
 * nestedScrollConnection: 상단바의 스크롤 동작을 제어하는 객체
 */
@Composable
fun MateTripTopAppBar(
//    currentRoute: String,
//    onNavigateUp: () -> Unit
) {
    TopAppBar(
        title = { splash_icon_02 },
        actions = {
            IconButton(onClick = { /* 알림 설정 시 알림을 표시하기 */ }) {
                Icon(
                    painter = painterResource(id = notification_icon),
                    contentDescription = "Localized description",
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
fun ScrollContent(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        // 여기에 스크롤 가능한 컨텐츠를 추가
    }
}

@Preview
@Composable
fun MateTripTopAppBarPreview() {
    MateTripTopAppBar(
//        currentRoute = Screen.Home.route,
//        onNavigateUp = { }
    )
}