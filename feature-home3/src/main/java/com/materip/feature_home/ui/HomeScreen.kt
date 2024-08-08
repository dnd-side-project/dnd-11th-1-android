package com.materip.feature_home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materip.matetrip.component.MateTripSearchBar
import com.materip.matetrip.theme.MateTripTypographySet
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HomeTitle()
        MateTripSearchBar()
    }
}

@Composable
fun HomeTitle() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "동행의 즐거움,\n당신의 여행을 특별하게!",
            style = MateTripTypographySet.displayLarge01,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}