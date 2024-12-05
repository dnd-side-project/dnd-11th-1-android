package com.materip.feature_home3.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.icon.Icons.minus_icon
import com.materip.core_designsystem.icon.Icons.plus_icon
import com.materip.core_designsystem.theme.MateTripColors.Gray_03
import com.materip.core_designsystem.theme.MateTripColors.Gray_06
import com.materip.core_designsystem.theme.MateTripColors.Gray_11
import com.materip.core_designsystem.theme.MateTripTypographySet

@Composable
fun SetCapacity(
    capacity: Int,
    onCapacityChange: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "모집 인원",
            color = Gray_11,
            modifier = Modifier.size(320.dp, 20.dp),
            style = MateTripTypographySet.title04
        )
        Text(
            text = "본인을 포함한 인원을 설정해주세요.  (최대 6명)",
            style = MateTripTypographySet.body06,
            modifier = Modifier.padding(bottom = 8.dp),
            color = Gray_06
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = {
                    if (capacity > 2) onCapacityChange(capacity - 1)
                },
                enabled = capacity > 2,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .background(
                        color = if (capacity <= 2) Color(0xFFD9D9D9) else Color.Black,
                        shape = RoundedCornerShape(size = 12.dp)
                    )
                    .padding(3.dp)
            ) {
                Image(
                    painter = painterResource(id = minus_icon),
                    contentDescription = "Minus icon"
                )
            }
            Text(text = capacity.toString(), style = MateTripTypographySet.numberBold1)
            IconButton(
                onClick = {
                    if (capacity < 6) onCapacityChange(capacity + 1)
                },
                enabled = capacity < 6,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .background(
                        color = if (capacity >= 6) Color(0xFFD9D9D9) else Color.Black,
                        shape = RoundedCornerShape(size = 12.dp)
                    )
                    .padding(3.dp)
            ) {
                Image(
                    painter = painterResource(id = plus_icon),
                    contentDescription = "Plus icon"
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        SimpleDivider(Gray_03)
        Spacer(modifier = Modifier.height(20.dp))
    }
}