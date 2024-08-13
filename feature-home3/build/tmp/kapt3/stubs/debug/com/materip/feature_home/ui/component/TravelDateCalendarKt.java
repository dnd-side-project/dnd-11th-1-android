package com.materip.feature_home.ui.component;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a8\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00052\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a,\u0010\t\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u001a\b\u0010\r\u001a\u00020\u0001H\u0007\u001a&\u0010\u000e\u001a\u00020\u00012\u001c\u0010\u000f\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00010\u0010H\u0007\u001a\b\u0010\u0011\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0012\u001a\u00020\u0001H\u0007\u00a8\u0006\u0013"}, d2 = {"CalendarDays", "", "currentMonth", "Ljava/time/YearMonth;", "startDate", "Ljava/time/LocalDate;", "endDate", "onDateSelected", "Lkotlin/Function1;", "CalendarHeader", "onPreviousClick", "Lkotlin/Function0;", "onNextClick", "DaysOfWeekHeader", "TravelDateCalendar", "onDateRangeSelected", "Lkotlin/Function2;", "TravelDateScreen", "TravelDateScreenPreview", "feature-home3_debug"})
public final class TravelDateCalendarKt {
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @androidx.compose.runtime.Composable
    public static final void TravelDateCalendar(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super java.time.LocalDate, ? super java.time.LocalDate, kotlin.Unit> onDateRangeSelected) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void CalendarHeader(@org.jetbrains.annotations.NotNull
    java.time.YearMonth currentMonth, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onPreviousClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onNextClick) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void DaysOfWeekHeader() {
    }
    
    @androidx.compose.runtime.Composable
    public static final void CalendarDays(@org.jetbrains.annotations.NotNull
    java.time.YearMonth currentMonth, @org.jetbrains.annotations.Nullable
    java.time.LocalDate startDate, @org.jetbrains.annotations.Nullable
    java.time.LocalDate endDate, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.time.LocalDate, kotlin.Unit> onDateSelected) {
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @androidx.compose.runtime.Composable
    public static final void TravelDateScreen() {
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    @androidx.compose.runtime.Composable
    public static final void TravelDateScreenPreview() {
    }
}