package com.materip.core_designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.materip.matetrip.theme.MateTripTypographySet
import com.materip.matetrip.theme.OnboardingMessage

@Composable
fun ShowOnboardingMessage(onboardingMessage: OnboardingMessage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = onboardingMessage.title,
            style = MateTripTypographySet.onboardingTitle,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = onboardingMessage.message,
            style = MateTripTypographySet.onboardingMessage
        )
    }
}

@Preview
@Composable
fun ShowOnboardingMessagePreview() {
    ShowOnboardingMessage(OnboardingMessage.MESSAGE_1)
}