package com.materip.matetrip.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.materip.matetrip.icon.Icons.man_icon
import com.materip.matetrip.theme.MateTripTypography

/**
 * Onboarding Card
 * text는 카드의 정보를 나타냄
 * icon은 카드의 아이콘을 나타냄
 * selectedCards는 선택된 카드의 text를 저장하는 리스트 // 이 부분은 변경 가능, 필요없으면 매개변수 삭제
 */

@Composable
fun OnboardingElevatedCard(
    text: String,
    icon: Int,
    selectedCards: MutableList<String>
) {
    var isClicked by remember { mutableStateOf(false) }
    val backgroundColor = if (isClicked) Color(0xFF3553F2) else Color(0xFFEEF0FF)
    val textColor = if (isClicked) Color.White else MateTripTypography.bodyMedium.color

    Card(
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = Modifier
            .size(145.dp)
            .clickable {
                isClicked = !isClicked
                if (isClicked) {
                    selectedCards.add(text)
                } else {
                    selectedCards.remove(text)
                }
            }
    ) {
        Column {
            Text(
                text = text,
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp),
                textAlign = TextAlign.Center,
                style = MateTripTypography.bodyMedium.copy(color = textColor),
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

@Composable
fun CardExample() {
    val selectedCards = remember { mutableStateListOf<String>() }
    // OnboardingElevatedCard를 호출하고 selectedCards에 selectedCards를 할당
}

@Preview
@Composable
fun OnboardingCardPreview() {
    OnboardingElevatedCard(
        text = "남성",
        icon = man_icon,
        selectedCards = remember { mutableListOf() }
    )
}
