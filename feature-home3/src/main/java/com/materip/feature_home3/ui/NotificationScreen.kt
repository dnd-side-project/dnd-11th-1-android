package com.materip.feature_home3.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.materip.core_designsystem.icon.Badges.profile_default_badge
import com.materip.core_designsystem.theme.MateTripColors.Blue_04
import com.materip.core_designsystem.theme.MateTripColors.Gray_07
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.feature_home3.intent.NotificationIntent
import com.materip.feature_home3.state.NotificationUiState
import com.materip.feature_home3.ui.component.DefaultNotification
import com.materip.feature_home3.ui.component.NotificationList
import com.materip.feature_home3.viewModel.NotificationViewModel

@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // 추후 알림 기능 추가 시 userid 매개변수 적용하기
//    LaunchedEffect(Unit) {
//        viewModel.handleIntent(NotificationIntent.FetchNotifications)
//    }

    when (uiState) {
        is NotificationUiState.Initial -> DefaultNotification()
        is NotificationUiState.Loading -> CircularProgressIndicator()

        is NotificationUiState.Success -> {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
            ) {
                DefaultNotification()
            }
        }

        is NotificationUiState.Error -> {
            Text("오류: ${(uiState as NotificationUiState.Error).message}")
        }
    }
}

@Composable
private fun NotificationDummyList(
    user: String,
    request: Boolean,
    review: Boolean
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = Color.White)
            .height(60.dp)
            .padding(start = 20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalAlignment = Alignment.Top,
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(shape = CircleShape, color = Blue_04)
            ) {
                Image(
                    painter = painterResource(id = profile_default_badge),
                    contentDescription = "프로필 기본 이미지",
                )
            }
            if (request) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append(user)
                            withStyle(style = SpanStyle(color = Gray_07)) {
                                append("으로부터")
                            }
                            append(" 동행 신청서")
                            withStyle(style = SpanStyle(color = Gray_07)) {
                                append("가 도착했어요!")
                            }
                        },
                        style = MateTripTypographySet.title04
                    )
                    Text(
                        text = "2024.07.12",
                        style = MateTripTypographySet.numberMedium3,
                        color = Gray_07
                    )
                }
            }
            if (review) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append(user)
                            withStyle(style = SpanStyle(color = Gray_07)) {
                                append("으로부터")
                            }
                            append(" 동행 후기")
                            withStyle(style = SpanStyle(color = Gray_07)) {
                                append("가 도착했어요!")
                            }
                        },
                        style = MateTripTypographySet.title04
                    )
                    Text(
                        text = "2024.07.12",
                        style = MateTripTypographySet.numberMedium3,
                        color = Gray_07
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun NotificationScreenPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
    ) {
        NotificationDummyList(
            user = "김철수",
            request = true,
            review = false
        )
        NotificationDummyList(
            user = "아보카도열정",
            request = false,
            review = true
        )
        NotificationDummyList(
            user = "레몬라임젤리",
            request = true,
            review = false
        )
    }
}