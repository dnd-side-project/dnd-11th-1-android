package com.materip.feature_home3.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.theme.MateTripColors.Gray_13
import com.materip.core_designsystem.theme.MateTripColors.Primary
import com.materip.core_designsystem.theme.MateTripTypographySet

@Composable
fun TabRowComponent(
    tabs: List<String>,
    contentScreens: List<@Composable () -> Unit>,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    indicatorColor: Color = Primary
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(modifier = modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = containerColor,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = indicatorColor
                )
            }
        ) {
            tabs.forEachIndexed { index, tabTitle ->
                val isSelected = selectedTabIndex == index
                Tab(
                    modifier = Modifier.height(50.dp),
                    selected = isSelected,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = tabTitle,
                            color = if (isSelected) Primary else Gray_13,
                            style = MateTripTypographySet.headline05
                        )
                    }
                )
            }
        }

        contentScreens.getOrNull(selectedTabIndex)?.invoke()
    }
}