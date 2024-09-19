package com.materip.feature_home3.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.materip.core_designsystem.icon.Badges.profile_default_badge
import com.materip.core_designsystem.theme.MateTripColors.Blue_04
import com.materip.core_model.accompany_board.notification.Notification
import com.materip.core_model.accompany_board.profile.GetUserProfile

// TODO: 알림 api 연동 후 수정 필요
@Composable
fun NotificationList(
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