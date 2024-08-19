package com.materip.feature_home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.materip.feature_home.state.NotificationUiState
import com.materip.feature_home.viewModel.NotificationViewModel
import com.materip.matetrip.icon.Icons.notification_none_icon
import com.materip.matetrip.theme.MateTripColors.Gray_05
import com.materip.matetrip.theme.MateTripTypographySet

// TODO: 로그인 정보가 필요해서 병합 후 작업 예정
@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = hiltViewModel()
) {
    when (val uiState = viewModel.uiState.collectAsState().value) {
        is NotificationUiState.Initial ->
            DefaultNotification()

        is NotificationUiState.Loading -> {
            CircularProgressIndicator()
        }

        is NotificationUiState.Success -> {
            /* TODO: Success 상태에 대한 UI 로직 추가 */
        }

        is NotificationUiState.Error -> {
            /* TODO: Error 상태에 대한 UI 로직 추가 */
        }
    }
}

//@Composable
//private fun NotificationList(
//    notifications: List<Notification>,
//) {
//    Column(
//        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.padding(start = 20.dp)
//    ) {
//        notifications.forEach { notification ->
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
//                verticalAlignment = Alignment.Top,
//            ) {
//                Box(
//                    modifier = Modifier
//                        .size(38.dp)
//                        .background(shape = CircleShape, color = Blue_04)
//                ) {
//                    if (유저프로필조회.profileImageUrl.isEmpty()) {
//                        Image(
//                            painter = painterResource(id = profile_default_badge),
//                            contentDescription = "프로필 기본 이미지",
//                        )
//                    } else {
//                        SubcomposeAsyncImage(
//                            model = 유저프로필조회.profileImageUrl,
//                            contentDescription = "Network Image",
//                            contentScale = ContentScale.Crop
//                        )
//                    }
//                }
//                val request = notification.boardRequest != null
//                val review = notification.boardReview != null
//
//                if (request) {
//                    Text(text = "${유저프로필조회.nickname}으로 부터 동행 신청서가 도착했어요.")
//                }
//                if (review) {
//                    Text(text = "${유저프로필조회.nickname}님이 동행글에 리뷰를 남겼어요.")
//                }
//            }
//        }
//    }
//}

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
    NotificationScreen()
}