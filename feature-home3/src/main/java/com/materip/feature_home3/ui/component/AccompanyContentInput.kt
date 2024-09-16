package com.materip.feature_home3.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.theme.MateTripColors.Blue_03
import com.materip.core_designsystem.theme.MateTripColors.Gray_06
import com.materip.core_designsystem.theme.MateTripColors.Gray_11
import com.materip.core_designsystem.theme.MateTripTypographySet

@Composable
fun AccompanyContentInput(
    content: String,
    onContentChange: (String) -> Unit
) {
    val isContentEmpty = content.isEmpty()
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "내용",
            color = Gray_11,
            modifier = Modifier.size(320.dp, 20.dp),
            style = MateTripTypographySet.title04
        )
        BasicTextField(
            value = content,
            onValueChange = onContentChange,
            modifier = Modifier
                .width(370.dp)
                .height(234.dp)
                .border(
                    width = 1.dp,
                    color = Blue_03,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(12.dp),
            textStyle = MateTripTypographySet.title04,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopStart
                ) {
                    if (isContentEmpty) {
                        Text(
                            text = "동행 모집 내용을 작성해주세요.\n\n최대한 자세하게 작성해주시면 좋아요.\nex)구체적인 장소, 여행목적, 동행을 구하는 이유 등\n(최대 500자)",
                            style = MateTripTypographySet.title04,
                            color = Gray_06
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}