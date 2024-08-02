package com.materip.matetrip.theme

/**
 * 메이트립 Android Typography
 */
/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    val homeTag: TextStyle
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
    )
)