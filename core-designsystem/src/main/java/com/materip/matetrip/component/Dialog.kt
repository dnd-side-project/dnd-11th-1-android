package com.materip.matetrip.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.materip.core_designsystem.R
import com.materip.matetrip.icon.Icons
import com.materip.matetrip.theme.MatetripColor

@Composable
fun SelectableDialog(
    value: String?,
    onValueChange: (String) -> Unit,
    options: List<String>,
    onDismissRequest: () -> Unit,
){
    val configuration = LocalConfiguration.current
    val dialogWidth = configuration.screenWidthDp.dp - 40.dp
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        LazyColumn(
            modifier = Modifier
                .width(dialogWidth)
                .heightIn(max = 405.dp)
                .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
        ){
            items(options){option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .padding(start = 10.dp, end = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = option,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                        fontWeight = FontWeight(400),
                        color = MatetripColor.Gray_11
                    )
                    IconButton(
                        modifier = Modifier.size(18.dp),
                        onClick = {
                            if(options.indexOf(option) != 0){
                                onValueChange(option)
                                onDismissRequest()
                            }
                        }
                    ) {
                        Image(
                            painter = painterResource(if(value == option) Icons.selected_radio_btn else Icons.not_selected_radio_btn),
                            contentDescription = "Radio Button"
                        )
                    }
                }
                if(options.indexOf(option) != options.lastIndex){HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = MatetripColor.divider_color)}
            }
        }
    }
}

@Preview
@Composable
private fun DialogUITest(){
    var isOpen by remember{mutableStateOf(false)}
    val yearOptions  = (1950..2024).toMutableList().map{it.toString()}.toMutableList().apply{
        this.add(0, "출생연도 선택")
    }.toList()
    val options = listOf("성별 선택", "남성", "여성")
    var value by remember{mutableStateOf(options[0])}
    Button(
        onClick = {isOpen = !isOpen}
    ){
        Text(value)
    }
    if(isOpen){
        SelectableDialog(
            value = value,
            onValueChange = {value = it},
            options = options,
            onDismissRequest = {isOpen = false}
        )
    }
}