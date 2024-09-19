package com.materip.feature_home3.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.icon.Icons.fold_icon
import com.materip.core_designsystem.theme.MateTripColors.Gray_06
import com.materip.core_designsystem.theme.MateTripColors.Gray_11
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.core_model.ui_model.Category

@Composable
fun AccompanyTypeButton(
    selectedCategories: List<Category>,
    onCategorySelected: (List<Category>) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val categoryDisplayMap = mapOf(
        "전체 동행" to Category.FULL,
        "부분 동행" to Category.PART,
        "식사 동행" to Category.MEAL,
        "투어 동행" to Category.TOUR,
        "숙박 공유" to Category.LODGING
    )
    val displayCategoryMap = categoryDisplayMap.entries.associate { (k, v) -> v to k }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "동행 유형",
            color = Gray_11,
            modifier = Modifier.size(320.dp, 20.dp),
            style = MateTripTypographySet.title04
        )
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                value = selectedCategories.joinToString(", ") { displayCategoryMap[it] ?: "" },
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .width(330.dp)
                    .height(20.dp),
                textStyle = MateTripTypographySet.body04,
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (selectedCategories.isEmpty()) {
                            Text(
                                text = "동행 유형을 선택해주세요.",
                                style = MateTripTypographySet.body04,
                                color = Gray_06
                            )
                        }
                        innerTextField()
                    }
                }
            )
            Icon(
                painter = painterResource(fold_icon),
                contentDescription = "Open dialog",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { showDialog = true }
            )
        }
        SimpleDivider()
    }
    if (showDialog) {
        CustomRadioButtonDialog(
            options = displayCategoryMap.values.toList(),
            selectedOption = selectedCategories.map { displayCategoryMap[it] ?: "" },
            onOptionsSelected = { selectedDisplayNames ->
                val newSelectedCategories =
                    selectedDisplayNames.mapNotNull { categoryDisplayMap[it] }
                onCategorySelected(updateSelectedCategories(newSelectedCategories))
                if (newSelectedCategories.contains(Category.FULL)) {
                    showDialog = false
                }
            },
            onDismissRequest = { showDialog = false }
        )
    }
}

private fun updateSelectedCategories(
    newSelectedCategories: List<Category>
): List<Category> {
    return if (newSelectedCategories.contains(Category.FULL)) {
        listOf(Category.FULL)
    } else {
        newSelectedCategories
    }
}