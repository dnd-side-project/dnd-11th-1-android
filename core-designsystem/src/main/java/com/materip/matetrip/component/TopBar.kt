package com.materip.matetrip.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.R
import com.materip.matetrip.icon.Icons

@Composable
fun TempTopBar(
    title: String
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = title,
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
            fontWeight = FontWeight(900)
        )
    }
}

@Composable
fun NormalTopBar(
    title: String,
    navIcon: Int = Icons.arrow_back_icon,
    menuIcon: Int? = null,
    menuText: String? = null,
    onBackClick: () -> Unit,
    onClick: () -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = onBackClick
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(navIcon),
                contentDescription = "Back Button"
            )
        }
        Spacer(Modifier.weight(1f))
        Text(
            text = title,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
            fontWeight = FontWeight(500)
        )
        Spacer(Modifier.weight(1f))
        Row(
            modifier = Modifier
                .width(40.dp)
                .height(30.dp)
                .clickable { onClick() },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(menuText != null){
                Text(
                    text = "확인",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400)
                )
            } else if (menuIcon != null){
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = onClick
                ){
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(menuIcon),
                        contentDescription = "Menu Icon"
                    )
                }
            } else {
                Spacer(Modifier.width(24.dp))
            }
        }
    }
}