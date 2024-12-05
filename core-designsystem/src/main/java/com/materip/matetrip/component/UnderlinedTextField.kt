package com.materip.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.R
import com.materip.core_model.ui_model.InputKeyboardType
import com.materip.core_designsystem.theme.MateTripColors.Gray_06


@Composable
fun UnderlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (newValue: String) -> Unit,
    placeholder: String?,
    fontSize: TextUnit,
    fontFamily: FontFamily,
    fontWeight: FontWeight,
    textColor: Color,
    underlineColor: Color,
    inputType: InputKeyboardType = InputKeyboardType.TEXT,
){
    val keyboardType = when(inputType){
        InputKeyboardType.TEXT -> KeyboardType.Text
        InputKeyboardType.URI -> KeyboardType.Uri
        InputKeyboardType.EMAIL -> KeyboardType.Email
        InputKeyboardType.PHONE -> KeyboardType.Phone
        InputKeyboardType.PASSWORD -> KeyboardType.Password
        InputKeyboardType.NUMBER -> KeyboardType.Number
        InputKeyboardType.NUMBER_PASSWORD -> KeyboardType.NumberPassword
    }
    val textStyle = TextStyle(
        fontSize = fontSize,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        color = textColor
    )
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        maxLines = 1,
        textStyle = textStyle,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        decorationBox = {
            Column {
                Spacer(Modifier.height(10.dp))
                if(value.isEmpty() && placeholder != null){
                    Text(
                        text = placeholder,
                        color = Gray_06,
                        style = textStyle
                    )
                } else {
                    it()
                }
                Spacer(Modifier.height(10.dp))
                HorizontalDivider(thickness = 1.dp, color = underlineColor)
            }
        }
    )
}

@Preview
@Composable
fun UnderlinedTextFieldText(){
    var string by remember{mutableStateOf("1")}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        UnderlinedTextField(
            value = string,
            onValueChange = {string = it},
            placeholder = "2024",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.roboto_medium)),
            fontWeight = FontWeight(500),
            textColor = Color.Black,
            underlineColor = Gray_06,
            inputType = InputKeyboardType.NUMBER
        )
    }
}