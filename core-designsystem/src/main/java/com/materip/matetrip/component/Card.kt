package com.materip.matetrip.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
    isSelected: Boolean, // 선택된 카드인지 여부, 이후는 부모 컴포저블에서 관리
    onClick: () -> Unit // 선택된 카드의 상태관리는 부모 컴포저블에서 관리
) {
    val backgroundColor = if (isSelected) Color(0xFF3553F2) else Color(0xFFEEF0FF)
    val textColor = if (isSelected) Color.White else MateTripTypographySet.onboardingCard.color

    Card(
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = modifier.clickable { onClick() }
    ) {
        Column {
            Text(
                text = text,
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp),
                textAlign = TextAlign.Center,
                style = MateTripTypographySet.onboardingCard.copy(color = textColor),
            )

            Image(
                painter = painterResource(id = icon),
                contentDescription = "$text 아이콘",
                modifier = Modifier
                    .padding(start = 65.dp, top = 20.dp)
            )
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

    OnboardingElevatedCard(
        text = "남성",
        icon = man_icon,
        isSelected = selectedCards.contains("남성"),
        onClick = { toggleSelection("남성") }
    )
}