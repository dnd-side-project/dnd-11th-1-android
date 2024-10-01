package com.materip.feature_home3.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.core_designsystem.theme.userCommentBoldStyle
import com.materip.core_designsystem.theme.userCommentStyle
import com.materip.core_model.response.GetProfileDetailsResponseDto

@Composable
fun UserCommentInfo(userInfo: GetProfileDetailsResponseDto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MateTripColors.Blue_04,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (userInfo.description.isNullOrEmpty()) {
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "프로필을 수정해서 나를 표현해 보세요",
                    style = userCommentBoldStyle
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "(상세하게 표현할수록 신뢰도가 쌓여요)",
                    style = userCommentStyle
                )
            }
        } else {
            Text(
                text = "${userInfo.description}",
                style = MateTripTypographySet.body04
            )
        }
    }
}