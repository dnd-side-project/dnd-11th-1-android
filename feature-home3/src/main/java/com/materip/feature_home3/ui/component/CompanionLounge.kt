package com.materip.feature_home3.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.materip.core_designsystem.component.RegionTag
import com.materip.core_designsystem.icon.Icons.filter_icon
import com.materip.core_designsystem.theme.MateTripColors.Blue_02
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.feature_home3.viewModel.BoardViewModel

@Composable
fun CompanionLounge(
    onRegionSelected: (String) -> Unit,
    viewModel: BoardViewModel = hiltViewModel()
) {
    var showFilterDialog by remember { mutableStateOf(false) }
    val selectedOption by viewModel.selectedOption.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "동행 라운지",
                style = MateTripTypographySet.headline06,
            )
            IconButton(
                onClick = { showFilterDialog = true },
            ) {
                Icon(
                    painter = painterResource(id = filter_icon),
                    contentDescription = "filter icon",
                    modifier = Modifier.size(18.dp),
                    tint = if (selectedOption == "전체") Blue_02 else Color.Black
                )
            }
        }
        RegionTag(
            onClick = onRegionSelected,
            modifier = Modifier.fillMaxWidth()
        )
        if (showFilterDialog) {
            ShowPostFilter(
                onDismissRequest = { showFilterDialog = false },
                onFilterSelected = { region, started, recruited ->
                    viewModel.updateFilter(region, started, recruited)
                },
                selectedOption = selectedOption,
                onOptionSelected = { option -> viewModel.updateSelectedOption(option) }
            )
        }
    }
}