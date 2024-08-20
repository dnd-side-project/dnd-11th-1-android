package com.materip.core_designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.materip.matetrip.theme.MateTripColors.Blue_04
import com.materip.matetrip.theme.MateTripColors.Primary

/**
 * ProgressIndicator
 * 코루틴을 사용하여 progress 값을 반복하는 작업을 실행함. 그러지 않으면 UI 스레드가 차단됨.
 */

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    currentProgress: Float
) {
    LinearProgressIndicator(
        progress = { currentProgress },
        modifier = modifier.height(8.dp),
        color = Primary,
        trackColor = Blue_04,
        strokeCap = StrokeCap.Round,
    )
}

@Preview
@Composable
fun ProgressIndicatorPreview() {
    Column(modifier = Modifier.fillMaxWidth()){
        ProgressIndicator(modifier = Modifier.fillMaxWidth(),currentProgress = 0.25f)
        ProgressIndicator(modifier = Modifier.fillMaxWidth(),currentProgress = 0.5f)
        ProgressIndicator(modifier = Modifier.fillMaxWidth(),currentProgress = 0.75f)
        ProgressIndicator(modifier = Modifier.fillMaxWidth(),currentProgress = 1f)
    }
}