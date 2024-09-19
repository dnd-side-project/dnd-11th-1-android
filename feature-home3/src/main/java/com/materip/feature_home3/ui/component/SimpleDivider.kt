package com.materip.feature_home3.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.theme.MateTripColors.Devider

@Composable
fun SimpleDivider(
    dividerColor: Color = Devider
) {
    Box(
        modifier = Modifier
            .padding(0.dp)
            .width(370.dp)
            .height(1.dp)
            .background(dividerColor)
    )
}