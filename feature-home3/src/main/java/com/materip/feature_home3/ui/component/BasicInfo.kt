package com.materip.feature_home3.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_common.toDisplayString
import com.materip.core_designsystem.R
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.core_model.ui_model.Gender

@Composable
fun BasicInfo(
    nickname: String,
    gender: Gender,
    age: String,
    authenticatorType: String,
) {
    val titleStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight(500),
        color = MateTripColors.Gray_06
    )
    val contentStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight(500),
        color = MateTripColors.Gray_10
    )
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "기본정보",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
            fontWeight = FontWeight(700),
        )
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.width(70.dp),
                text = "닉네임",
                style = titleStyle
            )
            Spacer(Modifier.width(20.dp))
            Text(
                text = nickname,
                style = contentStyle
            )
        }
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.width(70.dp),
                text = "성별",
                style = titleStyle
            )
            Spacer(Modifier.width(20.dp))
            Text(
                text = gender.toDisplayString(),
                style = contentStyle
            )
        }
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.width(70.dp),
                text = "나이",
                style = titleStyle
            )
            Spacer(Modifier.width(20.dp))
            Text(
                text = age,
                style = contentStyle
            )
        }
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.width(70.dp),
                text = "인증방식",
                style = titleStyle
            )
            Spacer(Modifier.width(20.dp))
            Text(
                text = if (authenticatorType == "KAKAO") "카카오" else "카카오",
                style = contentStyle
            )
        }
    }
}