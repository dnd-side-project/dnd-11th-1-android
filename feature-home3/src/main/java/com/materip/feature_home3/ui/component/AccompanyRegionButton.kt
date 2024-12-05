package com.materip.feature_home3.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.materip.core_model.ui_model.Region

@Composable
fun AccompanyRegionButton(
    selectedRegion: Region?,
    onRegionSelected: (Region) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val regionDisplayMap = mapOf(
        "서울" to Region.SEOUL,
        "경기·인천" to Region.GYEONGGI_INCHEON,
        "충청·대전·세종" to Region.CHUNGCHEONG_DAEJON_SEJONG,
        "강원" to Region.GANGWON,
        "전라·광주" to Region.JEOLLA_GWANGJU,
        "경상·대구" to Region.GYEONGSANG_DAEGU_ULSAN,
        "부산" to Region.BUSAN,
        "제주" to Region.JEJU,
    )
    val displayRegionMap = regionDisplayMap.entries.associate { (k, v) -> v to k }

    Box(
        modifier = Modifier.clickable { showDialog = true }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "여행 지역",
                color = Gray_11,
                modifier = Modifier.size(320.dp, 20.dp),
                style = MateTripTypographySet.title04
            )
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BasicTextField(
                    value = selectedRegion?.let { displayRegionMap[it] } ?: "",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .weight(1f)
                        .height(20.dp),
                    textStyle = MateTripTypographySet.body04,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.clickable { showDialog = true },
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (selectedRegion == null) {
                                Text(
                                    text = "여행 지역을 선택해주세요.",
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
                        .size(36.dp)
                        .clickable { showDialog = true }
                )
            }
            SimpleDivider()
        }
    }
    if (showDialog) {
        RegionRadioButtonDialog(
            options = displayRegionMap.values.toList(),
            selectedOption = selectedRegion,
            onOptionsSelected = {
                onRegionSelected(it)
                showDialog = false
            },
            onDismissRequest = { showDialog = false }
        )
    }
}