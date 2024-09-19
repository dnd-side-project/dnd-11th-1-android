package com.materip.feature_home3.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.theme.MateTripTypographySet

@Composable
fun HomeTitle() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 83.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "동행의 즐거움,\n당신의 여행을 특별하게!",
            style = MateTripTypographySet.displayLarge01,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}