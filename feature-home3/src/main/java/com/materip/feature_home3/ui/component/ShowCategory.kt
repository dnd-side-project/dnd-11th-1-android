package com.materip.feature_home3.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.theme.MateTripColors.Gray_02
import com.materip.core_designsystem.theme.MateTripColors.Gray_11
import com.materip.core_designsystem.theme.MateTripTypographySet

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShowCategory(category: List<String>) {
    Column(
        modifier = Modifier.padding(top = 50.dp, start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "동행 유형", style = MateTripTypographySet.headline05)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        ) {
            category.forEach { category ->
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(28.dp)
                        .background(color = Gray_02, shape = RoundedCornerShape(size = 6.dp))
                        .padding(horizontal = 12.dp, vertical = 5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = category,
                        style = MateTripTypographySet.body04,
                        color = Gray_11
                    )
                }
            }
        }
    }
}