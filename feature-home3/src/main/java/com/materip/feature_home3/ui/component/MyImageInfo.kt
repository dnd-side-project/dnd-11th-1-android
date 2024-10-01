package com.materip.feature_home3.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.R
import com.materip.core_designsystem.component.ImageLoadView
import com.materip.core_designsystem.theme.MateTripColors

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MyImageInfo(images: List<String>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "이미지 (${images.size})",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
            fontWeight = FontWeight(700)
        )
        Spacer(Modifier.height(12.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            images.forEach {
                ImageLoadView(
                    backgroundColor = MateTripColors.Blue_04,
                    shape = RoundedCornerShape(size = 10.dp),
                    size = 100.dp,
                    imageUrl = it,
                    onCloseClick = {/*미사용*/ }
                )
            }
        }
    }
}