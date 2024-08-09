@file:OptIn(ExperimentalMaterial3Api::class)

package com.materip.matetrip.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.matetrip.theme.MatetripColor.gray_06

/**
 * SearchBar/Container, Leading icon button, Supporting text
 */
@Composable
fun MateTripSearchBar() {
    val query = remember { mutableStateOf("") }
    val active = remember { mutableStateOf(false) }

    Column {
        SearchBar(
            query = query.value,
            onQueryChange = { query.value = it },
            onSearch = { /* Handle search */ },
            active = active.value,
            onActiveChange = { active.value = it },
            placeholder = { Text("어디로 여행을 떠나시나요?") },
            leadingIcon = {
                IconButton(onClick = { /* Handle click */ }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = gray_06
                    )
                }
            },
            content = {
                Text("SearchBar content", fontSize = 20.sp, modifier = Modifier.padding(16.dp))
            }
        )

        if (active.value) {
            // Search suggestions/results go here
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Text("Search results", fontSize = 20.sp, modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Preview
@Composable
fun MateTripSearchBarPreview() {
    MateTripSearchBar()
}