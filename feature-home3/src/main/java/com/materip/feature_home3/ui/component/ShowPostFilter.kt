package com.materip.feature_home3.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.materip.core_designsystem.theme.MateTripColors.Blue_02
import com.materip.core_designsystem.theme.MateTripColors.Gray_03
import com.materip.core_designsystem.theme.MateTripColors.Gray_11
import com.materip.core_designsystem.theme.MateTripColors.Primary
import com.materip.core_designsystem.theme.MateTripTypographySet

@Composable
fun ShowPostFilter(
    onDismissRequest: () -> Unit,
    onFilterSelected: (region: String?, started: Boolean, recruited: Boolean) -> Unit,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val options = listOf("전체", "동행 모집 중", "동행 모집 완료")

    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .width(320.dp)
                .background(Color.White, shape = RoundedCornerShape(10.dp))
        ) {
            options.forEach { option ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(48.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(320.dp)
                        .height(47.dp)
                        .padding(8.dp)
                        .clickable {
                            onOptionSelected(option)
                            when (option) {
                                "동행 모집 중" -> {
                                    onFilterSelected(null, true, false)
                                    onDismissRequest()
                                }
                                "동행 모집 완료" -> {
                                    onFilterSelected(null, true, true)
                                    onDismissRequest()
                                }
                                else -> {
                                    onFilterSelected(null, false, false)
                                }
                            }
                            onDismissRequest()
                        }
                ) {
                    Text(
                        text = option,
                        style = MateTripTypographySet.body02,
                        color = Gray_11
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    RadioButton(
                        selected = selectedOption == option,
                        onClick = { onOptionSelected(option) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Primary,
                            unselectedColor = Blue_02
                        )
                    )
                }
                if (options.indexOf(option) < options.size - 1) {
                    SimpleDivider(Gray_03)
                }
            }
        }
    }
}