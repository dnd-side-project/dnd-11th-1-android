package com.materip.feature_mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_model.ui_model.ReviewClass
import com.materip.feature_mypage.R
import com.materip.matetrip.component.NormalTopBar
import com.materip.matetrip.component.ReviewItem

@Composable
fun ReviewRoute(
    navBack: () -> Unit,
){
    ReviewScreen(navBack = navBack)
}

@Composable
fun ReviewScreen(
    navBack: () -> Unit,
){
    val dummyReview = ReviewClass(
        totalCount = 14,
        review = listOf(
            Pair(5, "붙임성이 좋아요."),
            Pair(3, "친절해요."),
            Pair(3, "계획적이에요."),
            Pair(2, "열정적이에요."),
            Pair(1, "이성적이에요."),
        )
    )
    val totalCountText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)))){
            append("총 ")
        }
        withStyle(style = SpanStyle(fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.roboto_medium)))){
            append(dummyReview.totalCount.toString())
        }
        withStyle(style = SpanStyle(fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)))){
            append("개")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
    ){
        NormalTopBar(
            title = "받은 동행 평가",
            titleFontWeight = FontWeight(700),
            onBackClick = {/** 뒤로가기 */},
            onClick = {/* 미사용 */}
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = totalCountText,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(700)
        )
        Spacer(Modifier.height(14.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(dummyReview.review){
                ReviewItem(review = it)
            }
        }
    }
}

@Preview
@Composable
private fun ReviewUiTest(){
    ReviewScreen(navBack = {})
}