package com.materip.matetrip.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.materip.matetrip.theme.MainColor
import com.materip.matetrip.theme.SecondColor
import kotlinx.coroutines.launch

/**
 * ProgressIndicator
 * 코루틴을 사용하여 progress 값을 반복하는 작업을 실행함. 그러지 않으면 UI 스레드가 차단됨.
 */

@Composable
fun ProgressIndicator() {
    var currentProgress by remember { mutableFloatStateOf(0.25f) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = { currentProgress },
            modifier = Modifier.size(width = 300.dp, height = 8.dp),
            color = Color(MainColor),
            trackColor = Color(SecondColor),
            strokeCap = StrokeCap.Round,
        )
        Spacer(modifier = Modifier.height(40.dp))
        MateTripButton(
            onClick = {
                scope.launch {
                    if (currentProgress < 1f) {
                        currentProgress += 0.25f
                    }
                }
            },
            enabled = true,
            buttonText = "다음"
        )
    }
}

@Preview
@Composable
fun ProgressIndicatorPreview() {
    ProgressIndicator()
}
