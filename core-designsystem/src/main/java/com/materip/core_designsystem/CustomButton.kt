package com.materip.core_designsystem

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.TextUnit

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    containerColor: Color,
    shape: Shape,
    text: String,
    textColor: Color,
    fontSize: TextUnit,
    isEnabled: Boolean,
    onClick: () -> Unit,
){
    Button(
        modifier = modifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        onClick = onClick,
        enabled = isEnabled
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = fontSize,
        )
    }
}