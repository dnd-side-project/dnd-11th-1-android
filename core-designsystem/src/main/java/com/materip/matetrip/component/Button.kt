package com.materip.matetrip.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.R
import com.materip.matetrip.icon.Icons
import com.materip.matetrip.theme.MateTripTypographySet
import com.materip.matetrip.theme.MatetripColor.ActivatedColor
import com.materip.matetrip.theme.MatetripColor.Blue_04
import com.materip.matetrip.theme.MatetripColor.Gray_08
import com.materip.matetrip.theme.MatetripColor.Gray_10
import com.materip.matetrip.theme.MatetripColor.InactiveColor
import com.materip.matetrip.theme.MatetripColor.Primary
import com.materip.matetrip.theme.MatetripColor.gray_06

/**
 * MateTrip Button
 * 온보딩과 홈 게시글 동행신청 시 버튼
 */

@Composable
fun MateTripButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = false,
    buttonText: String
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.height(height = 54.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ActivatedColor,
            contentColor = Color.White,
            disabledContainerColor = InactiveColor,
            disabledContentColor = gray_06
        )
    ) {
        Text(
            text = buttonText,
            style = MateTripTypographySet.headline06,
        )
    }
}

@Composable
fun MateTripHomeButton(
    onClick: () -> Unit,
    enabled: Boolean = false,
    buttonText: String
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.size(width = 320.dp, height = 54.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ActivatedColor,
            contentColor = Color.White,
            disabledContainerColor = InactiveColor,
            disabledContentColor = gray_06
        )
    ) {
        Text(
            text = buttonText,
            style = MateTripTypographySet.headline06,
        )
    }
}

// 홈 게시글 작성, 모집 연령, 성별 버튼
@Composable
fun FavoriteButton(
    onClick: () -> Unit,
    enabled: Boolean = false,
    buttonText: String
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(7.dp),
        modifier = Modifier.size(width = 155.dp, height = 40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = Color.White,
            disabledContainerColor = InactiveColor,
            disabledContentColor = Gray_10
        )
    ) {
        Text(
            text = buttonText,
            style = MateTripTypographySet.body04,
        )
    }
}

// 거절, 수락
@Composable
fun AccessStatusButton(
    onClick: () -> Unit,
    enabled: Boolean = false,
    buttonText: String
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.size(width = 155.dp, height = 48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ActivatedColor,
            contentColor = Color.White,
            disabledContainerColor = InactiveColor,
            disabledContentColor = gray_06
        )
    ) {
        Text(
            text = buttonText,
            style = MateTripTypographySet.body04,
        )
    }
}

// 동행 후기 작성, 보낸 신청서 보기, 받은 신청서 보기 버튼
@Composable
fun CreateReviewButton(
    onClick: () -> Unit,
    buttonText: String
) {
    Button(
        onClick = onClick,
        enabled = true,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.size(width = 320.dp, height = 38.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue_04,
            contentColor = Gray_08,
        )
    ) {
        Text(
            text = buttonText,
            style = MateTripTypographySet.title04,
        )
    }
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    trailingIcon: Int? = null,
    shape: Shape,
    btnText: String,
    fontSize: TextUnit,
    textColor: Color = Color.Black,
    btnColor: Color,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
){
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = btnColor
        ),
        shape = shape,
        enabled = isEnabled,
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            if(trailingIcon != null){
                Image(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(trailingIcon),
                    contentDescription = "Review Icon"
                )
                Spacer(Modifier.width(8.dp))
            }
            Text(
                text = btnText,
                fontSize = fontSize,
                color = textColor,
                fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                fontWeight = FontWeight(500)
            )
        }
    }
}

@Composable
fun OnAndOffButton(
    modifier: Modifier,
    isClicked: Boolean,
    onClick: () -> Unit,
){
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(if(isClicked) Icons.active_toggle else Icons.inactive_toggle),
            contentDescription = "Toggle"
        )
    }
}

@Preview
@Composable
fun MateTripButtonPreview() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        MateTripButton(
            onClick = { },
            enabled = false,
            buttonText = "다음"
        )
        Spacer(modifier = Modifier.size(16.dp))
        MateTripButton(
            onClick = {},
            enabled = true,
            buttonText = "완료"
        )
        Spacer(modifier = Modifier.size(16.dp))
        MateTripHomeButton(
            onClick = {},
            enabled = false,
            buttonText = "작성완료"
        )
        Spacer(modifier = Modifier.size(16.dp))
        MateTripHomeButton(
            onClick = {},
            enabled = true,
            buttonText = "동행신청"
        )
        Spacer(modifier = Modifier.size(16.dp))
        FavoriteButton(
            onClick = {},
            enabled = true,
            buttonText = "동일 나이대"
        )
        Spacer(modifier = Modifier.size(16.dp))
        FavoriteButton(
            onClick = {},
            enabled = false,
            buttonText = "상관없음"
        )
        Spacer(modifier = Modifier.size(16.dp))
        AccessStatusButton(
            onClick = {},
            enabled = true,
            buttonText = "수락"
        )
        Spacer(modifier = Modifier.size(16.dp))
        AccessStatusButton(
            onClick = {},
            enabled = false,
            buttonText = "거절"
        )
        Spacer(modifier = Modifier.size(16.dp))
        CreateReviewButton(
            onClick = {},
            buttonText = "보낸 신청서 작성"
        )
        Spacer(modifier = Modifier.size(16.dp))
        CreateReviewButton(
            onClick = {},
            buttonText = "받은 신청서 보기"
        )
        Spacer(modifier = Modifier.size(16.dp))
        CreateReviewButton(
            onClick = {},
            buttonText = "동행 후기 작성"
        )
        Spacer(modifier = Modifier.size(16.dp))
    }
}