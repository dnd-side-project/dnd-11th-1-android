package com.materip.matetrip.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.materip.matetrip.icon.Icons.man_icon
import com.materip.matetrip.theme.MateTripTypographySet

/**
 * Onboarding Card
 */

@Composable
fun OnboardingElevatedCard(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int,
    tint: Color? = null,
    iconSize: Dp? = null,
    isSelected: Boolean, // 선택된 카드인지 여부, 이후는 부모 컴포저블에서 관리
    onClick: () -> Unit // 선택된 카드의 상태관리는 부모 컴포저블에서 관리
) {
    val backgroundColor = if (isSelected) Color(0xFF3553F2) else Color(0xFFEEF0FF)
    val textColor = if (isSelected) Color.White else MateTripTypographySet.onboardingCard.color
    val iconColor = if(tint == null) LocalContentColor.current else tint

    Card(
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = text,
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp),
                textAlign = TextAlign.Center,
                style = MateTripTypographySet.onboardingCard.copy(color = textColor),
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp, end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.End
            ){
                Icon(
                    modifier = if(iconSize != null){
                        Modifier.size(iconSize)
                    } else {
                        Modifier
                    },
                    painter = painterResource(id = icon),
                    contentDescription = "$text 아이콘",
                    tint = iconColor
                )
            }
        }
    }
}



@Preview
@Composable
fun OnboardingCardSelectionPreview() {
    val selectedCards = remember { mutableStateListOf<String>() }

    val toggleSelection: (String) -> Unit = { text ->
        if (selectedCards.contains(text)) {
            selectedCards.remove(text)
        } else {
            selectedCards.add(text)
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ){
        OnboardingElevatedCard(
            modifier = Modifier.weight(1f).aspectRatio(1f),
            text = "남성",
            icon = man_icon,
            iconSize = 30.dp,
            isSelected = selectedCards.contains("남성"),
            onClick = { toggleSelection("남성") }
        )
        OnboardingElevatedCard(
            modifier = Modifier.weight(1f)
                .aspectRatio(1f),
            text = "남성",
            icon = man_icon,
            isSelected = selectedCards.contains("남성"),
            onClick = { toggleSelection("남성") }
        )

    }
}