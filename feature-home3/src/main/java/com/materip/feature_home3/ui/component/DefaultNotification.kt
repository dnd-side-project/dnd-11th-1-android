package com.materip.feature_home3.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.icon.Icons.notification_none_icon
import com.materip.core_designsystem.theme.MateTripColors.Gray_05
import com.materip.core_designsystem.theme.MateTripTypographySet

@Composable
fun DefaultNotification() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = notification_none_icon),
            contentDescription = "notification_none_icon",
            contentScale = ContentScale.None,
        )
        Text(
            text = "아직 알림 내역이 없어요.",
            style = MateTripTypographySet.numberMedium2,
            color = Gray_05
        )
    }
}