package com.materip.matetrip.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.R
import com.materip.matetrip.icon.Icons
import com.materip.matetrip.theme.MatetripColor

@Composable
fun Spinner(
    modifier: Modifier,
    menuWidth: Dp,
    value: Any,
    onValueChange: (Any) -> Unit,
    options: List<Any>
){
    val fontFamily = FontFamily(Font(if (value is Int) R.font.roboto_medium else R.font.noto_sans_kr))
    var expanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = value.toString(),
                fontSize = 16.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight(500),
                color = MatetripColor.Gray_10
            )
            Icon(
                painter = painterResource(Icons.fold_icon),
                contentDescription = "Fold Icon"
            )
        }
        DropdownMenu(
            modifier = Modifier
                .width(menuWidth)
                .heightIn(150.dp)
                .verticalScroll(state = scrollState),
            expanded = expanded,
            onDismissRequest = { expanded = !expanded }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    text = {
                        Text(
                            text = selectionOption.toString(),
                            fontSize = 14.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight(500),
                            color = MatetripColor.Gray_10
                        )
                    },
                    onClick = {
                        onValueChange(selectionOption)
                        expanded = false
                    }
                )
                if(options.lastIndex != options.indexOf(selectionOption)){
                    HorizontalDivider(modifier = Modifier.width(menuWidth), thickness = 0.5.dp, color = MatetripColor.Gray_11)
                }
            }
        }
    }
}

@Preview
@Composable
private fun SpinnerUITest(){
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp.dp - 36.dp - 40.dp
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Spinner(
                modifier = Modifier.weight(1f),
                value = "value",
                menuWidth = (width / 2),
                onValueChange = {

                },
                options = listOf()
            )
            Spacer(Modifier.width(40.dp))
            Spinner(
                modifier = Modifier.weight(1f),
                value = "value",
                menuWidth = (width / 2),
                onValueChange = {

                },
                options = listOf()

            )
        }
    }
}