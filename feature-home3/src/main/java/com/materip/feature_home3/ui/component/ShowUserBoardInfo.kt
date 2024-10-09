package com.materip.feature_home3.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.theme.MateTripColors.Blue_04
import com.materip.core_designsystem.theme.MateTripColors.Gray_10
import com.materip.core_designsystem.theme.MateTripTypographySet

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShowUserBoardInfo(
    title: String,
    content: String,
    tagNames: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = title,
            style = MateTripTypographySet.headline05
        )

        Text(
            text = content,
            style = MateTripTypographySet.body04,
            color = Gray_10
        )

        FlowRow(
            modifier = Modifier.padding(top = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top)
        ) {
            tagNames.forEach { tagName ->
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(28.dp)
                        .background(color = Blue_04, shape = RoundedCornerShape(size = 6.dp))
                        .padding(horizontal = 12.dp, vertical = 5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tagName,
                        style = MateTripTypographySet.body04,
                        color = Gray_10
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}