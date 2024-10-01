package com.materip.feature_home3.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.materip.core_common.toDisplayString
import com.materip.core_designsystem.component.CircleImageView
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.core_designsystem.theme.gradeStyle
import com.materip.core_model.response.GetProfileDetailsResponseDto
import com.materip.core_model.ui_model.Grade
import com.materip.feature_home3.ui.profile_content.UserProviderInfo

@Composable
fun OverviewInfo(
    userInfo: GetProfileDetailsResponseDto,
    levelInfo: Grade,
    ageCategory: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        CircleImageView(
            size = 60.dp,
            imageUrl = userInfo.profileImageUrl ?: ""
        )
        Spacer(Modifier.width(13.dp))
        Column {
            Row(
                modifier = Modifier.height(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = levelInfo.gradeKoName,
                    color = levelInfo.color as Color,
                    style = gradeStyle
                )
            }
            Row {
                Text(
                    text = userInfo.nickname,
                    color = MateTripColors.Gray_11,
                    style = MateTripTypographySet.headline05
                )
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(levelInfo.badge),
                    contentDescription = "Level Badge"
                )
            }
            Text(
                text = "$ageCategory · ${userInfo.gender.toDisplayString()}",
                color = MateTripColors.Gray_06,
                style = MateTripTypographySet.title05
            )
        }
        Spacer(Modifier.weight(1f))
        UserProviderInfo()
    }
}