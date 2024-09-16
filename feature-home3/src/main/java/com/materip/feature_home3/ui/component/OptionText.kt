package com.materip.feature_home3.ui.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.theme.MateTripColors.Gray_11
import com.materip.core_designsystem.theme.MateTripTypographySet

@Composable
fun OptionText(text: String) {
    Text(
        text = text,
        style = MateTripTypographySet.body02,
        modifier = Modifier
            .width(230.dp)
            .height(26.dp),
        color = Gray_11
    )
}