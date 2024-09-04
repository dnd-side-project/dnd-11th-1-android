package com.materip.matetrip.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.theme.MateTripTypographySet

@Composable
fun DeleteBox(onDeleteConfirm: () -> Unit) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 4.dp,
                spotColor = Color(0x40000000),
                ambientColor = Color(0x40000000)
            )
            .width(76.dp)
            .height(44.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))
            .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)
    ) {
        Text(
            text = "삭제하기",
            style = MateTripTypographySet.body04,
            color = Color.Black,
            modifier = Modifier.clickable { onDeleteConfirm() })
    }
}