package com.materip.matetrip.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.materip.matetrip.icon.Icons
import com.materip.matetrip.theme.MateTripTypographySet
import com.materip.matetrip.theme.Primary
import com.materip.matetrip.theme.gray_06

@Composable
fun MateTripBottomBar(
    onHomeClick: () -> Unit,
    onMyPageClick: () -> Unit,
    onSettingClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("home") }

    BottomAppBar(
        modifier = Modifier
            .width(360.dp)
            .height(68.dp),
        containerColor = Color.White,
        contentPadding = PaddingValues(horizontal = 34.dp)
    ) {
        BottomBarItem(
            iconRes = Icons.home_icon,
            label = "홈",
            isSelected = selectedTab == "home",
            onClick = {
                selectedTab = "home"
                onHomeClick()
            },
            modifier = Modifier.weight(1f)
        )
        BottomBarItem(
            iconRes = Icons.my_page_icon,
            label = "마이페이지",
            isSelected = selectedTab == "my_page",
            onClick = {
                selectedTab = "my_page"
                onMyPageClick()
            },
            modifier = Modifier.weight(1f)
        )
        BottomBarItem(
            iconRes = Icons.setting_icon,
            label = "설정",
            isSelected = selectedTab == "setting",
            onClick = {
                selectedTab = "setting"
                onSettingClick()
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun BottomBarItem(
    iconRes: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val color = if (isSelected) Primary else gray_06

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = color,
            modifier = Modifier.clickable(onClick = onClick)
        )
        Text(
            text = label,
            style = MateTripTypographySet.title05,
            color = color,
            modifier = Modifier.clickable(onClick = onClick)
        )
    }
}

@Preview
@Composable
fun PreviewMateTripBottomBar() {
    MateTripBottomBar(
        onHomeClick = {},
        onMyPageClick = {},
        onSettingClick = {}
    )
}