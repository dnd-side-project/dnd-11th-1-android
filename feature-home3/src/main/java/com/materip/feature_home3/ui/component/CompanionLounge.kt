package com.materip.feature_home3.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.component.RegionTag
import com.materip.core_designsystem.icon.Icons.filter_icon
import com.materip.core_designsystem.theme.MateTripTypographySet

@Composable
fun CompanionLounge(
    onRegionSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "동행 라운지",
                style = MateTripTypographySet.headline06,
                modifier = Modifier.padding(start = 20.dp, bottom = 12.dp)
            )
            IconButton(
                onClick = { /*TODO: 게시글이 오늘 날짜를 기준으로 유효한지 안한지에 대해 필터링할 수 있는 아이콘*/ },
                modifier = Modifier.padding(end = 20.dp, bottom = 12.dp)
            ) {
                Icon(
                    painter = painterResource(id = filter_icon),
                    contentDescription = "filter icon"
                )
            }
        }
        RegionTag(
            onClick = onRegionSelected,
            modifier = Modifier.fillMaxWidth()
        )
    }
}