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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.materip.core_designsystem.icon.Icons.notification_none_icon
import com.materip.core_designsystem.theme.MateTripColors.Gray_05
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.feature_home3.state.NotificationUiState
import com.materip.feature_home3.viewModel.NotificationViewModel
import coil.compose.SubcomposeAsyncImage
import com.materip.core_designsystem.icon.Badges.profile_default_badge
import com.materip.core_designsystem.theme.MateTripColors.Blue_04
import com.materip.core_designsystem.theme.MateTripColors.Gray_07
import com.materip.core_model.accompany_board.notification.Notification
import com.materip.core_model.accompany_board.profile.GetUserProfile



@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchNotifications()
    }

    when (uiState) {
        is NotificationUiState.Initial ->
            DefaultNotification()

        is NotificationUiState.Loading -> {
            CircularProgressIndicator()
        }

        is NotificationUiState.Success -> {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
            ) {
                // TODO: NotificationList()
            }
        }

        is NotificationUiState.Error -> {
            Text("오류: ${(uiState as NotificationUiState.Error).message}")
        }
    }
}

// TODO: 더미데이터로 UI 확인
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

@Composable
private fun NotificationList(
    notifications: List<Notification>,
    user: GetUserProfile
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(start = 20.dp)
    ) {
        notifications.forEach { notification ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                verticalAlignment = Alignment.Top,
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(shape = CircleShape, color = Blue_04)
                ) {
                    if (user.profileImageUrl.isEmpty()) {
                        Image(
                            painter = painterResource(id = profile_default_badge),
                            contentDescription = "프로필 기본 이미지",
                        )
                    } else {
                        SubcomposeAsyncImage(
                            model = user.profileImageUrl,
                            contentDescription = "Network Image",
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                val request = notification.boardRequest?.hasContent() == true
                val review = notification.boardReview?.hasContent() == true

                if (notification.isSentByUser.userId == user.userId) {
                    if (request) {
                        Text(text = "${user.nickname}님으로 부터 동행 신청서가 도착했어요.")
                    }
                    if (review) {
                        Text(text = "${user.nickname}님이 동행글에 리뷰를 남겼어요.")
                    }
                }
            }
        }
    }
}

@Composable
private fun DefaultNotification() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = notification_none_icon),
            contentDescription = "notification_none_icon",
            contentScale = ContentScale.None
        )
        Text(
            text = "아직 알림 내역이 없어요.",
            style = MateTripTypographySet.numberMedium2,
            color = Gray_05
        )
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