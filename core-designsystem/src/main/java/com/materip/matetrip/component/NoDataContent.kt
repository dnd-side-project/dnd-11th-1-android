package com.materip.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.R
import com.materip.matetrip.theme.MateTripColors

@Composable
fun NoDataContent(
    message: String
){
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = message,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
            fontWeight = FontWeight(400),
            color = MateTripColors.Gray_04
        )
    }
}