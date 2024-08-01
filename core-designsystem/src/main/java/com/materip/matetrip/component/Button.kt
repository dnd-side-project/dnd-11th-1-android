package com.materip.matetrip.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.materip.matetrip.theme.MateTripTypography

/**
 * MateTrip Button
 * 온보딩과 홈 게시글 동행신청 시 버튼
 */

@Composable
fun MateTripButton(
    navController: NavController,
    onClickDestination: String,
    enabled: Boolean = false,
    buttonText: String
) {
    val buttonColor = if (enabled) Color.Black else Color(0xFFDDDFEA)
    val textColor = if (enabled) Color.White else Color(0xFF939094)

    Button(
        onClick = { navController.navigate(onClickDestination) },
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.size(width = 300.dp, height = 54.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = textColor
        )
    ) {
        Text(
            text = buttonText,
            style = MateTripTypography.bodySmall,
            color = textColor
        )
    }
}

@Preview
@Composable
fun MateTripButtonPreview() {
    val navController = rememberNavController()

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        MateTripButton(
            navController = navController,
            onClickDestination = "next_screen",
            enabled = true,
            buttonText = "다음"
        )
        Spacer(modifier = Modifier.size(16.dp))
        MateTripButton(
            navController = navController,
            onClickDestination = "next_screen",
            enabled = false,
            buttonText = "완료"
        )
    }
}