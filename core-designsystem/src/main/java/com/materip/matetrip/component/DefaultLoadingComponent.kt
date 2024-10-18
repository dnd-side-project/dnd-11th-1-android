package com.materip.matetrip.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.theme.MateTripColors

@Composable
fun DefaultLoadingComponent(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(
            modifier = Modifier.size(36.dp),
            strokeWidth = 5.dp,
            trackColor = MateTripColors.Primary,
            strokeCap = StrokeCap.Round
        )
    }
}