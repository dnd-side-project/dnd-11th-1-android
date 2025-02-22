package com.materip.feature_home3.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.icon.Icons.date_icon
import com.materip.core_designsystem.icon.Icons.place_icon
import com.materip.core_designsystem.theme.MateTripColors.Blue_03
import com.materip.core_designsystem.theme.MateTripColors.Primary
import com.materip.core_designsystem.theme.MateTripTypographySet

@Composable
fun ShowSchedule(
    region: String,
    startDate: String,
    endDate: String
) {
    val formatStartDate = formatDateStringToDisplay(startDate)
    val formatEndDate = formatDateStringToDisplay(endDate)

    Column(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "동행 일정", style = MateTripTypographySet.headline05)
        Box(
            modifier = Modifier
                .border(width = 1.dp, color = Blue_03, shape = RoundedCornerShape(size = 10.dp))
                .height(86.dp)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = place_icon),
                        contentDescription = "동행 장소",
                        tint = Primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(text = region, style = MateTripTypographySet.numberMedium2)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = date_icon),
                        contentDescription = "동행 날짜",
                        tint = Primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "$formatStartDate - $formatEndDate",
                        style = MateTripTypographySet.numberMedium2
                    )
                }
            }
        }
    }
}