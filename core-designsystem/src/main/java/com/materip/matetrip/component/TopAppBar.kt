@file:OptIn(ExperimentalMaterial3Api::class)

package com.materip.matetrip.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.materip.matetrip.icon.Icons.notification_icon
import com.materip.matetrip.theme.MateTripTypography


/**
 * 상단바가 스크롤될 때 고정되는 동작을 설정하고, 이를 Modifier에 적용하여 상단바의 스크롤 동작을 정의
 * pinnedScrollBehavior: 상단바가 스크롤될 때 고정되는 동작
 * rememberTopAppBarState: 상단바의 상태를 기억하는 상태 객체
 * nestedScrollConnection: 상단바의 스크롤 동작을 제어하는 객체
 */
@Composable
fun MateTripTopAppBar() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .padding(top = 20.dp, start = 0.dp, bottom = 20.dp),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Text(
                            "MATE",
                            style = MateTripTypography.bodyLarge
                        )
                        // 이미지 추가
                        Icon(
                            imageVector = Icons.Filled.Face, // 원하는 이미지로 변경
                            contentDescription = "Logo"
                        )
                        Text(
                            "TRIP",
                            style = MateTripTypography.bodyLarge
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* 알림 설정 시 알림을 표시하기 */ }) {
                        Icon(
                            painter = painterResource(id = notification_icon),
                            contentDescription = "Localized description",
                            tint = Color.Unspecified // 드로어블의 원래 색상을 사용
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
    ) { innerPadding ->
        ScrollContent(innerPadding)
    }
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
        Text("Inner content를 위한 임시 컴포저블")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMateTripTopAppBar() {
    MateTripTopAppBar()
}