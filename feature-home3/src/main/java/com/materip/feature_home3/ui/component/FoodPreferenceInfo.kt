package com.materip.feature_home3.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.R
import com.materip.core_designsystem.component.ProfileTag

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FoodPreferenceInfo(foodPreferences: List<String>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "음식취향",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
            fontWeight = FontWeight(700)
        )
        Spacer(Modifier.height(12.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            foodPreferences.forEach {
                ProfileTag(tagName = it)
            }
        }
    }
}