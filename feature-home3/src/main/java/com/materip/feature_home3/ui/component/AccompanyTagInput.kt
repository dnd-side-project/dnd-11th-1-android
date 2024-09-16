package com.materip.feature_home3.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.theme.MateTripColors.ActivatedColor
import com.materip.core_designsystem.theme.MateTripColors.Blue_04
import com.materip.core_designsystem.theme.MateTripColors.Gray_06
import com.materip.core_designsystem.theme.MateTripColors.Gray_11
import com.materip.core_designsystem.theme.MateTripTypographySet

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AccompanyTagInput(
    tagInput: String,
    onTagInputChange: (String) -> Unit,
    tags: List<String>,
    onTagAdd: (String) -> Unit,
    onTagRemove: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "태그 등록",
            color = Gray_11,
            modifier = Modifier.size(320.dp, 20.dp),
            style = MateTripTypographySet.title04
        )
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 태그 입력
            BasicTextField(
                value = tagInput,
                onValueChange = onTagInputChange,
                modifier = Modifier
                    .width(330.dp)
                    .height(20.dp),
                textStyle = MateTripTypographySet.body04,
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (tagInput.isEmpty()) {
                            Text(
                                text = "관심사와 키워드를 입력해주세요. (최대 5개)",
                                style = MateTripTypographySet.body04,
                                color = Gray_06
                            )
                        }
                        innerTextField()
                    }
                }
            )
            Box(
                modifier = Modifier
                    .width(36.dp)
                    .height(28.dp)
                    .background(ActivatedColor, shape = RoundedCornerShape(6.dp))
                    .clickable {
                        if (tagInput.isNotEmpty() && tags.size < 5 && !tags.contains(tagInput)) {
                            onTagAdd(tagInput)
                        }
                    }
                    .padding(7.dp, 5.dp)
            ) {
                Text(
                    text = "입력",
                    style = MateTripTypographySet.title05,
                    color = Color.White
                )
            }
        }
        // 구분선
        SimpleDivider()
        // 태그 목록
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            maxItemsInEachRow = Int.MAX_VALUE
        ) {
            tags.forEach { tag ->
                Box(
                    modifier = Modifier
                        .height(28.dp)
                        .background(Blue_04, shape = RoundedCornerShape(6.dp))
                        .padding(12.dp, 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "#$tag", style = MateTripTypographySet.body04)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Remove tag",
                            modifier = Modifier
                                .size(16.dp)
                                .clickable { onTagRemove(tag) }
                        )
                    }
                }
            }
        }
    }
}