package com.materip.feature_home3.ui.profile_content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_common.toDisplayAgeString
import com.materip.core_common.toDisplayDateString
import com.materip.core_common.toDisplayString
import com.materip.core_designsystem.component.NormalTopBar
import com.materip.core_designsystem.component.ReviewDescItem
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.theme.MateTripColors.Gray_04
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.core_model.response.ReviewItem


@Composable
fun ReviewContent(
    reviews: List<ReviewItem>,
    totalCount: Int,
    navBack: () -> Unit,
    navReviewDescription: (Int) -> Unit
) {
    val totalCountText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)))) {
            append("총 ")
        }
        withStyle(style = SpanStyle(fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.roboto_medium)))) {
            append("${totalCount}")
        }
        withStyle(style = SpanStyle(fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)))) {
            append("개")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp),
    ) {
        NormalTopBar(
            title = "받은 동행 후기",
            titleFontWeight = FontWeight(700),
            onBackClick = navBack,
            onClick = {/* 미사용 */ }
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = totalCountText,
            fontSize = 16.sp,
            fontWeight = FontWeight(700),
        )
        Spacer(Modifier.height(14.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(reviews) { review ->
                ReviewDescItem(
                    destination = review.region.toDisplayString(),
                    period = review.getDuration(),
                    startDate = review.startDate.toDisplayDateString(),
                    endDate = review.endDate.toDisplayDateString(),
                    profileUrl = review.profileImageUrl,
                    nickname = review.nickname,
                    age = review.age.toDisplayAgeString(),
                    gender = review.gender.toDisplayString(),
                    content = review.detailContent,
                    onClick = { navReviewDescription(review.reviewId) }
                )
            }
        }
    }
}


@Composable
fun ReviewContentInitial() {
    Column(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(180.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "받은 동행 평가", style = MateTripTypographySet.headline05)
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painter = painterResource(id = Icons.enter_24_icon),
                        contentDescription = "받은 동행 평가 더보기"
                    )
                }
            }
            Spacer(modifier = Modifier.height(240.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(180.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "받은 동행 후기", style = MateTripTypographySet.headline05)
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painter = painterResource(id = Icons.enter_24_icon),
                        contentDescription = "받은 동행 후기 더보기"
                    )
                }
            }
            Spacer(modifier = Modifier.height(170.dp))
        }
        Text(
            text = "아직 받은 동행 평가와 후기가 없어요.",
            style = MateTripTypographySet.body03,
            color = Gray_04,
            modifier = Modifier.height(24.dp)
        )
        Spacer(modifier = Modifier.height(170.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewContentPreview() {
    ReviewContentInitial()
}