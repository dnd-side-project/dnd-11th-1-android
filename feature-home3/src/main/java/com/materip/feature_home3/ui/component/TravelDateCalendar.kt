package com.materip.feature_home3.ui.component

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Refresh
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
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.core_designsystem.theme.MateTripColors.Blue_04
import com.materip.core_designsystem.theme.MateTripColors.Gray_05
import com.materip.core_designsystem.theme.MateTripColors.Gray_11
import com.materip.core_designsystem.theme.MateTripColors.Primary
import com.materip.core_designsystem.theme.MateTripColors.Red_01
import com.materip.core_designsystem.theme.MateTripTypographySet
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun TravelDateCalendar(
    startDate: LocalDateTime?,
    endDate: LocalDateTime?,
    onDateRangeSelected: (LocalDateTime, LocalDateTime) -> Unit
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedStartDate by remember { mutableStateOf(startDate) }
    var selectedEndDate by remember { mutableStateOf(endDate) }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "여행 일정",
                color = Gray_11,
                modifier = Modifier.size(320.dp, 20.dp),
                style = MateTripTypographySet.title04
            )
            IconButton(
                onClick = {
                    selectedStartDate = null
                    selectedEndDate = null
                    onDateRangeSelected(LocalDateTime.now(), LocalDateTime.now())
                },
                modifier = Modifier.size(21.dp)
            ) {
                Icon(Icons.Outlined.Refresh, contentDescription = "Reset date")
            }
        }
        Box(
            modifier = Modifier
                .size(370.dp, 338.dp)
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
                    startDate = selectedStartDate,
                    endDate = selectedEndDate,
                    onDateSelected = { dateTime ->
                        if (dateTime.toLocalDate() >= LocalDate.now()) {
                            when {
                                selectedStartDate == null || (selectedEndDate != null && dateTime < selectedStartDate) -> {
                                    selectedStartDate = dateTime
                                    selectedEndDate = null
                                }

                                dateTime > selectedStartDate -> selectedEndDate = dateTime

                                selectedEndDate != null && dateTime < selectedStartDate -> {
                                    selectedEndDate = selectedStartDate
                                    selectedStartDate = dateTime
                                }

                                else -> {
                                    selectedEndDate = selectedStartDate
                                    selectedStartDate = dateTime
                                }
                            }
                            onDateRangeSelected(
                                selectedStartDate ?: LocalDateTime.now(),
                                selectedEndDate ?: selectedStartDate ?: LocalDateTime.now()
                            )
                        }
                    }
                )
            }
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
            text = "${
                currentMonth.month.getDisplayName(
                    TextStyle.FULL,
                    Locale.getDefault()
                )
            } ${currentMonth.year}",
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
    startDate: LocalDateTime?,
    endDate: LocalDateTime?,
    onDateSelected: (LocalDateTime) -> Unit
) {
    val firstDayOfMonth = currentMonth.atDay(1)
    val lastDayOfMonth = currentMonth.atEndOfMonth()
    val daysInMonth = (1..lastDayOfMonth.dayOfMonth).toList()
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
    val today = LocalDate.now()

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(firstDayOfWeek) {
            Box(modifier = Modifier.size(34.dp))
        }

        items(daysInMonth.size) { index ->
            val day = index + 1
            val date = currentMonth.atDay(day).atStartOfDay()
            val isPastDate = date.toLocalDate().isBefore(today)
            val isSunday = date.dayOfWeek == DayOfWeek.SUNDAY

            val isInRange = startDate != null && endDate != null && date in startDate..endDate
            val isRangeStart = startDate != null && date == startDate
            val isRangeEnd = startDate != null && date == startDate
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
                        when {
                            isRangeStart -> drawRect(
                                color = MateTripColors.Blue_02,
                                topLeft = Offset(size.width / 2, 0f),
                                size = Size(size.width / 2, size.height)
                            )

                            isRangeEnd -> drawRect(
                                color = MateTripColors.Blue_02,
                                topLeft = Offset(0f, 0f),
                                size = Size(size.width / 2, size.height)
                            )

                            isInMiddleRange -> drawRect(
                                color = MateTripColors.Blue_02,
                                topLeft = Offset(0f, 0f),
                                size = Size(size.width, size.height)
                            )
                        }
                    }
                }
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