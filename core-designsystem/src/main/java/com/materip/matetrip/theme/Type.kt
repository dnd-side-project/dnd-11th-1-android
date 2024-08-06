package com.materip.matetrip.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.R

/**
 * 메이트립 Android Typography
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
    val body01: TextStyle,
    val body02: TextStyle,
    val body03: TextStyle,
    val body04: TextStyle,
    val body05: TextStyle,
    val body06: TextStyle,
    val numberBold1: TextStyle,
    val numberBold2: TextStyle,
    val numberBold3: TextStyle,
    val numberMedium1: TextStyle,
    val numberMedium2: TextStyle,
    val numberMedium3: TextStyle,
    val numberRegular1: TextStyle,
    val numberRegular2: TextStyle,
    val numberRegular3: TextStyle,
)

data class CustomFontFamily(
    val helveticaBlack: FontFamily,
    val notoSansKr: FontFamily,
    val robotoBold: FontFamily,
    val robotoMedium: FontFamily,
    val robotoRegular: FontFamily,
)

val customFontFamily = CustomFontFamily(
    helveticaBlack = FontFamily(Font(R.font.helvetica_black)),
    notoSansKr = FontFamily(Font(R.font.noto_sans_kr)),
    robotoBold = FontFamily(Font(R.font.roboto_bold)),
    robotoMedium = FontFamily(Font(R.font.roboto_medium)),
    robotoRegular = FontFamily(Font(R.font.roboto_regular)),
)

val MateTripTypographySet = MateTripTypography(
    topBarTitle = TextStyle(
        fontFamily = customFontFamily.helveticaBlack,
        fontWeight = FontWeight.Black,
        fontSize = 17.31.sp,
        lineHeight = 9.38.sp,
    ),
    onboardingCard = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = Color(0xFFBEC3EF)
    ),
    onboardingButton = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = Color.White
    ),
    onboardingTitle = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W700,
        fontSize = 28.sp,
        lineHeight = 40.54.sp,
    ),
    onboardingMessage = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = gray_05
    ),
    homeTag = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 18.sp,
    ),
    displayLarge01 = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W900,
        fontSize = 26.sp,
        lineHeight = 37.65.sp,
    ),
    displayLarge02 = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W900,
        fontSize = 18.sp,
        lineHeight = 26.06.sp,
    ),
    headline01 = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W700,
        fontSize = 28.sp,
        lineHeight = 40.54.sp,
    ),
    headline02 = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W700,
        fontSize = 26.sp,
        lineHeight = 37.65.sp,
    ),
    headline03 = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W700,
        fontSize = 20.sp,
        lineHeight = 30.sp,
    ),
    headline04 = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W700,
        fontSize = 18.sp,
        lineHeight = 26.sp,
    ),
    headline05 = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W700,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    headline06 = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W700,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    title01 = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp,
        lineHeight = 30.sp,
    ),
    title02 = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W500,
        fontSize = 18.sp,
        lineHeight = 26.sp,
    ),
    title03 = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    title04 = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    title05 = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp,
        lineHeight = 18.sp,
    ),
    body01 = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W400,
        fontSize = 28.sp,
        lineHeight = 42.sp,
    ),
    body02 = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W400,
        fontSize = 18.sp,
        lineHeight = 26.sp,
    ),
    body03 = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    body04 = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    body05 = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 18.sp,
    ),
    body06 = TextStyle(
        fontFamily = customFontFamily.notoSansKr,
        fontWeight = FontWeight.W400,
        fontSize = 10.sp,
        lineHeight = 14.sp,
    ),
    numberBold1 = TextStyle(
        fontFamily = customFontFamily.robotoBold,
        fontWeight = FontWeight.W700,
        fontSize = 40.sp,
        lineHeight = 48.sp,
    ),
    numberBold2 = TextStyle(
        fontFamily = customFontFamily.robotoBold,
        fontWeight = FontWeight.W700,
        fontSize = 16.sp,
        lineHeight = 20.sp,
    ),
    numberBold3 = TextStyle(
        fontFamily = customFontFamily.robotoBold,
        fontWeight = FontWeight.W700,
        fontSize = 12.sp,
        lineHeight = 14.sp,
    ),
    numberMedium1 = TextStyle(
        fontFamily = customFontFamily.robotoMedium,
        fontWeight = FontWeight.W500,
        fontSize = 18.sp,
        lineHeight = 22.sp,
    ),
    numberMedium2 = TextStyle(
        fontFamily = customFontFamily.robotoMedium,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        lineHeight = 20.sp,
    ),
    numberMedium3 = TextStyle(
        fontFamily = customFontFamily.robotoMedium,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 16.sp,
    ),
    numberRegular1 = TextStyle(
        fontFamily = customFontFamily.robotoRegular,
        fontWeight = FontWeight.W400,
        fontSize = 28.sp,
        lineHeight = 34.sp,
    ),
    numberRegular2 = TextStyle(
        fontFamily = customFontFamily.robotoRegular,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 16.sp,
    ),
    numberRegular3 = TextStyle(
        fontFamily = customFontFamily.robotoRegular,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 14.sp,
    )
)