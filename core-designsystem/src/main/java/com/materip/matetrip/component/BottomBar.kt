package com.materip.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.theme.MateTripColors.Primary
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.core_designsystem.theme.MateTripColors.Gray_06

@Composable
fun MateTripBottomBar(
    currentRoute: String,
    onHomeClick: () -> Unit,
    onMyPageClick: () -> Unit,
    onSettingClick: () -> Unit
) {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp),
        containerColor = Color.White,
    ) {
        BottomBarItem(
            iconRes = Icons.home_icon,
            label = "홈",
            isSelected = currentRoute == "home",
            onClick = onHomeClick,
            modifier = Modifier
                .weight(1f)
                .padding(top = 10.dp)
        )
        BottomBarItem(
            iconRes = Icons.my_page_icon,
            label = "마이페이지",
            isSelected = currentRoute == "MyPageRoute",
            onClick = onMyPageClick,
            modifier = Modifier
                .weight(1f)
                .padding(top = 10.dp)
        )
        BottomBarItem(
            iconRes = Icons.setting_icon,
            label = "설정",
            isSelected = currentRoute == "SettingRoute",
            onClick = onSettingClick,
            modifier = Modifier
                .weight(1f)
                .padding(top = 10.dp)
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
    val color = if (isSelected) Primary else Gray_06

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
        currentRoute = "",
        onHomeClick = {},
        onMyPageClick = {},
        onSettingClick = {}
    )
}