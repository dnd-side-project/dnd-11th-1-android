@file:OptIn(ExperimentalMaterial3Api::class)

package com.materip.core_designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.materip.matetrip.theme.MateTripColors.Gray_06
import com.materip.matetrip.theme.MateTripColors.Primary
import com.materip.matetrip.theme.MateTripTypographySet

/**
 * SearchBar/Container, Leading icon button, Supporting text
 */
@ExperimentalMaterial3Api
@Composable
fun MateTripSearchBar() {
    val query = remember { mutableStateOf("") }
    val active = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(bottom = 30.dp)
    ) {
        DockedSearchBar(
            query = query.value,
            onQueryChange = { query.value = it },
            onSearch = { /* Handle search */ },
            active = active.value,
            onActiveChange = { active.value = it },
            placeholder = { Text(
                text = "어디로 여행을 떠나시나요?",
                style = MateTripTypographySet.body04
            ) },
            leadingIcon = {
                IconButton(onClick = { /* Handle click */ }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = if(active.value) Primary else Gray_06
                    )
                }
            },
            colors = SearchBarDefaults.colors(
                containerColor = Color.White,
                dividerColor = Gray_06,
            ),
            shadowElevation = 6.dp,
            content = {
                Text(
                    text = "검색한 게 보이게 하기",
                    style = MateTripTypographySet.body04,
                    modifier = Modifier.padding(22.dp)
                )
            }
        )
    }
}