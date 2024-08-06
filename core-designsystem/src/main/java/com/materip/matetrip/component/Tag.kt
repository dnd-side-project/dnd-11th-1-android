package com.materip.matetrip.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.materip.matetrip.theme.Gray_11
import com.materip.matetrip.theme.Primary
import com.materip.matetrip.theme.MateTripTypographySet


@Composable
fun RegionTag(
    regions: List<String>,
    onClick: (String) -> Unit,
) {
    var selectedRegion by remember { mutableStateOf("전체") }

    LazyRow(
        modifier = Modifier
            .background(color = Color.LightGray) // preview에서 보려고 추가 사용 시 수정
            .padding(horizontal = 16.dp)
    ) {
        items(regions) { region ->
            val isSelected = region == selectedRegion
            val backgroundColor = if (isSelected) Primary else Color.White
            val textColor = if (isSelected) Color.White else Gray_11

            Button(
                onClick = {
                    selectedRegion = region
                    onClick(region)
                },
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .height(36.dp)
                    .padding(horizontal = 4.dp)
                    .wrapContentHeight(align = Alignment.Top),
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColor,
                    contentColor = textColor,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Gray_11
                )
            ) {
                Text(
                    text = region,
                    style = MateTripTypographySet.homeTag,
                )
            }
        }
    }
}

@Preview
@Composable
fun RegionTagPreview() {
    val regions = listOf("전체", "수도권", "경기도", "충청도", "강원도", "경상도", "전라도", "제주도", "해외")
    RegionTag(regions = regions, onClick = {})
}