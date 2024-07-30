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

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.LineHeightStyle.Alignment
import androidx.compose.ui.text.style.LineHeightStyle.Trim
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.R

/**
 * Now in Android typography.
 */
internal val MateTripTypography = Typography(
    // 홈화면 상단바 타이틀 타이포그래피
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.helvetica_black)),
        fontWeight = FontWeight.Black,
        fontSize = 17.31.sp,
        lineHeight = 9.38.sp,
    )
)