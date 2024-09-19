package com.materip.feature_home3.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.component.RegionTag
import com.materip.core_designsystem.theme.MateTripTypographySet

@Composable
fun CompanionLounge(onRegionSelected: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "동행 라운지",
            style = MateTripTypographySet.headline06,
            modifier = Modifier.padding(start = 20.dp, end = 272.dp, bottom = 12.dp)
        )
        RegionTag(
            onClick = onRegionSelected,
            modifier = Modifier.fillMaxWidth()
        )
    }
}