package com.materip.feature_home.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.materip.matetrip.theme.MateTripColors.Blue_02
import com.materip.matetrip.theme.MateTripColors.Blue_04
import com.materip.matetrip.theme.MateTripColors.Gray_05
import com.materip.matetrip.theme.MateTripColors.Gray_11
import com.materip.matetrip.theme.MateTripColors.Primary
import com.materip.matetrip.theme.MateTripColors.Red_01
import com.materip.matetrip.theme.MateTripTypographySet
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun TravelDateCalendar(
    onDateRangeSelected: (LocalDate?, LocalDate?) -> Unit
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }

    Box(
        modifier = Modifier
            .size(320.dp, 298.dp)
            .background(Blue_04)
            .padding(top = 14.dp, bottom = 12.dp, start = 20.dp, end = 20.dp)
    ) {
        Column {
            CalendarHeader(
                currentMonth = currentMonth,
                onPreviousClick = { currentMonth = currentMonth.minusMonths(1) },
                onNextClick = { currentMonth = currentMonth.plusMonths(1) }
            )

            DaysOfWeekHeader()

            CalendarDays(
                currentMonth = currentMonth,
                startDate = startDate,
                endDate = endDate,
                onDateSelected = { date ->
                    if (date >= LocalDate.now()) {
                        when {
                            startDate == null || (endDate != null && date < startDate) -> {
                                startDate = date
                                endDate = null
                            }
                            date > startDate -> endDate = date
                            else -> {
                                endDate = startDate
                                startDate = date
                            }
                        }
                        onDateRangeSelected(startDate, endDate)
                    }
                }
            )
        }
    }
}

@Composable
fun CalendarHeader(
    currentMonth: YearMonth,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousClick) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Previous month")
        }
        Text(
            text = "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${currentMonth.year}",
            style = MaterialTheme.typography.titleMedium
        )
        IconButton(onClick = onNextClick) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Next month")
        }
    }
}

@Composable
fun DaysOfWeekHeader() {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (day in listOf("일", "월", "화", "수", "목", "금", "토")) {
            Text(
                text = day,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = if (day == "일") Red_01 else MaterialTheme.colorScheme.onSurface,
                style = MateTripTypographySet.numberRegular2
            )
        }
    }
}

@Composable
fun CalendarDays(
    currentMonth: YearMonth,
    startDate: LocalDate?,
    endDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val firstDayOfMonth = currentMonth.atDay(1)
    val lastDayOfMonth = currentMonth.atEndOfMonth()
    val daysInMonth = (1..lastDayOfMonth.dayOfMonth).toList()
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
    val today = LocalDate.now()

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.fillMaxSize()
    ) {
        items(firstDayOfWeek) {
            Box(modifier = Modifier.size(34.dp))
        }

        items(daysInMonth.size) { index ->
            val day = index + 1
            val date = currentMonth.atDay(day)
            val isPastDate = date < today
            val isSunday = date.dayOfWeek == DayOfWeek.SUNDAY

            val isInRange = startDate != null && endDate != null && date in startDate..endDate
            val isRangeStart = date == startDate
            val isRangeEnd = date == endDate
            val isInMiddleRange = isInRange && !isRangeStart && !isRangeEnd

            Box(
                modifier = Modifier
                    .padding(top = 7.dp)
                    .size(34.dp)
                    .clickable(enabled = !isPastDate) { onDateSelected(date) },
                contentAlignment = Alignment.Center
            ) {
                if (isInRange) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        if (isRangeStart) {
                            drawRect(
                                color = Blue_02, // Light Blue color
                                topLeft = Offset(size.width / 2, 0f),
                                size = Size(size.width / 2, size.height)
                            )
                        } else if (isRangeEnd) {
                            drawRect(
                                color = Blue_02, // Light Blue color
                                topLeft = Offset(0f, 0f),
                                size = Size(size.width / 2, size.height)
                            )
                        } else if (isInMiddleRange) {
                            drawRect(
                                color = Blue_02, // Light Blue color
                                topLeft = Offset(0f, 0f),
                                size = Size(size.width, size.height)
                            )
                        }
                    }
                }
                // 선택된 날짜들 강조
                if (isRangeStart || isRangeEnd) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Primary)
                    )
                }

                Text(
                    text = day.toString(),
                    style = MateTripTypographySet.numberMedium3,
                    color = when {
                        isRangeStart || isRangeEnd -> Color.White
                        isPastDate -> Gray_05
                        isSunday -> Red_01
                        else -> Gray_11
                    },
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}


//@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
//@Composable
//fun CalendarDays(
//    currentMonth: YearMonth,
//    startDate: LocalDate?,
//    endDate: LocalDate?,
//    onDateSelected: (LocalDate) -> Unit
//) {
//    val firstDayOfMonth = currentMonth.atDay(1)
//    val lastDayOfMonth = currentMonth.atEndOfMonth()
//    val daysInMonth = (1..lastDayOfMonth.dayOfMonth).toList()
//    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
//    val today = LocalDate.now()
//
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(7),
//        modifier = Modifier.fillMaxSize()
//    ) {
//        items(firstDayOfWeek) {
//            Box(modifier = Modifier.size(34.dp))
//        }
//
//        items(daysInMonth.size) { index ->
//            val day = index + 1
//            val date = currentMonth.atDay(day)
//            val isSelected = startDate != null && endDate != null && date in startDate..endDate
//            val isRangeStart = date == startDate
//            val isRangeEnd = date == endDate
//            val isPastDate = date < today
//            val isSunday = date.dayOfWeek == DayOfWeek.SUNDAY
//
//            Box(
//                modifier = Modifier
//                    .padding(top = 7.dp)
//                    .size(34.dp)
//                    .clip(if (isRangeStart || isRangeEnd) CircleShape else RoundedCornerShape(0.dp))
//                    .background(
//                        when {
//                            isRangeStart || isRangeEnd -> Primary
//                            isSelected -> Blue_02
//                            else -> Color.Transparent
//                        }
//                    )
//                    .clickable(enabled = !isPastDate) { onDateSelected(date) },
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = day.toString(),
//                    style = MateTripTypographySet.numberMedium3,
//                    color = when {
//                        isRangeStart || isRangeEnd -> Color.White
//                        isPastDate -> Gray_05
//                        isSunday -> Red_01
//                        else -> Gray_11
//                    },
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.align(Alignment.Center)
//                )
//            }
//        }
//    }
//}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun TravelDateScreen() {
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }

    TravelDateCalendar { start, end ->
        startDate = start
        endDate = end
    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Preview(showBackground = true)
@Composable
fun TravelDateScreenPreview() {
    TravelDateScreen()
}