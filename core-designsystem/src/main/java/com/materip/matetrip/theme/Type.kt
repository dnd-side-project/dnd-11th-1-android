package com.materip.matetrip.theme

/**
 * 메이트립 Android Typography
 */

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.R

/**
 * Now in Android typography.
 */
data class MateTripTypography(
    val topBarTitle: TextStyle,
    val onboardingCard: TextStyle,
    val onboardingButton: TextStyle,
    val onboardingTitle: TextStyle,
    val onboardingMessage: TextStyle,
    val homeTag: TextStyle,
    val displayLarge01: TextStyle,
    val displayLarge02: TextStyle,
    val headline01: TextStyle,
    val headline02: TextStyle,
    val headline03: TextStyle,
    val headline04: TextStyle,
    val headline05: TextStyle,
    val headline06: TextStyle,
    val title01: TextStyle,
    val title02: TextStyle,
    val title03: TextStyle,
    val title04: TextStyle,
    val title05: TextStyle,
)

val customFontFamily = FontFamily(
    Font(R.font.helvetica_black),
    Font(R.font.noto_sans_kr)
)

val MateTripTypographySet = MateTripTypography(
    // 홈화면 상단바 타이틀 타이포그래피
    topBarTitle = TextStyle(
        fontFamily = FontFamily(Font(R.font.helvetica_black)),
        fontWeight = FontWeight.Black,
        fontSize = 17.31.sp,
        lineHeight = 9.38.sp,
    ),
    // 온보딩 카드 타이포그래피
    onboardingCard = TextStyle(
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = Color(0xFFBEC3EF)
    ),
    // 버튼 타이포그래피(온보딩, 홈 게시글 동행 신청)
    onboardingButton = TextStyle(
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = Color.White
    ),
    // 온보딩 타이포그래피
    onboardingTitle = TextStyle(
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight.W700,
        fontSize = 28.sp,
        lineHeight = 40.54.sp,
    ),
    // 온보딩 메시지 타이포그래피
    onboardingMessage = TextStyle(
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = Color(Gray05)
    ),
    // 홈 태그 타이포그래피
    homeTag = TextStyle(
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 18.sp,
    ),
    // DisplayLarge 01
    displayLarge01 = TextStyle(
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight.W900,
        fontSize = 26.sp,
        lineHeight = 37.65.sp,
    ),
    // DisplayLarge 02
    displayLarge02 = TextStyle(
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight.W900,
        fontSize = 18.sp,
        lineHeight = 26.06.sp,
    ),
    // HeadLine 01
    headline01 = TextStyle(
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight.W700,
        fontSize = 28.sp,
        lineHeight = 40.54.sp,
    ),
    // HeadLine 02
    headline02 = TextStyle(
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight.W700,
        fontSize = 26.sp,
        lineHeight = 37.65.sp,
    ),
    // HeadLine 03
    headline03 = TextStyle(
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight.W700,
        fontSize = 20.sp,
        lineHeight = 30.sp,
    ),
    // HeadLine 04
    headline04 = TextStyle(
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight.W700,
        fontSize = 18.sp,
        lineHeight = 26.sp,
    ),
    // HeadLine 05
    headline05 = TextStyle(
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight.W700,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    // HeadLine 06
    headline06 = TextStyle(
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight.W700,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    // Title 01
    title01 = TextStyle(
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight.W700,
        fontSize = 20.sp,
        lineHeight = 30.sp,
    ),
    // Title 02
    title02 = TextStyle(
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight.W700,
        fontSize = 18.sp,
        lineHeight = 26.sp,
    ),
    // Title 03
    title03 = TextStyle(
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight.W700,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    // Title 04
    title04 = TextStyle(
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight.W700,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    // Title 05
    title05 = TextStyle(
        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
        fontWeight = FontWeight.W700,
        fontSize = 12.sp,
        lineHeight = 18.sp,
    ),
)