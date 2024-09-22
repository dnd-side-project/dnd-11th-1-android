package com.materip.matetrip.toast

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.R
import com.materip.core_designsystem.icon.Badges
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.theme.MateTripColors

object CustomToastUtil {

    @Composable
    fun SetErrorView(
        message: String,
        resourceIcon: Int = Badges.warning_badge
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .background(color = MateTripColors.Red_01, shape = RoundedCornerShape(10.dp))
                .padding(12.dp)
        ){
            Column(
                modifier = Modifier.width(24.dp),
                verticalArrangement = Arrangement.Top
            ){
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(resourceIcon),
                    contentDescription = "Warning Notification Icon"
                )
            }
            Spacer(Modifier.width(16.dp))
            Text(
                text = message,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                fontWeight = FontWeight(400),
                color = Color.White
            )
        }
    }

    @Composable
    fun SetCommonToastView(
        message: String,
        resourceIcon: Int = Badges.information_badge
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .background(color = Color.Black, shape = RoundedCornerShape(10.dp))
                .padding(12.dp)
        ){
            Column(
                modifier = Modifier.width(24.dp),
                verticalArrangement = Arrangement.Top
            ){
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(resourceIcon),
                    contentDescription = "Warning Notification Icon"
                )
            }
            Spacer(Modifier.width(16.dp))
            Text(
                text = message,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                fontWeight = FontWeight(400),
                color = Color.White
            )
        }
    }
}