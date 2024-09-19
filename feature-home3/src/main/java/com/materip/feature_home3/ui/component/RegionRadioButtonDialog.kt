package com.materip.feature_home3.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.materip.core_designsystem.theme.MateTripColors.Blue_02
import com.materip.core_designsystem.theme.MateTripColors.Gray_03
import com.materip.core_designsystem.theme.MateTripColors.Primary
import com.materip.core_model.ui_model.Region

@Composable
fun RegionRadioButtonDialog(
    options: List<String>,
    selectedOption: Region?,
    onOptionsSelected: (Region) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .width(320.dp)
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .padding(10.dp)
        ) {
            options.forEachIndexed { index, option ->
                val region = Region.entries[index]
                Row(
                    horizontalArrangement = Arrangement.spacedBy(48.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(320.dp)
                        .height(47.dp)
                        .clickable { onOptionsSelected(region) }
                        .padding(start = 10.dp, top = 10.dp, end = 12.dp, bottom = 11.dp)
                ) {
                    OptionText(option)
                    RadioButton(
                        selected = selectedOption == region,
                        onClick = { onOptionsSelected(region) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Primary,
                            unselectedColor = Blue_02
                        )
                    )
                }
                if (index < options.size - 1) {
                    SimpleDivider(Gray_03)
                }
            }
        }
    }
}