package com.materip.core_designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.theme.MateTripColors.Primary
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.core_designsystem.theme.MateTripColors.Gray_06
import com.materip.core_model.navigation.MyPageRoute
import com.materip.core_model.navigation.SettingRoute

@Composable
fun MateTripBottomBar(
    currentBackStack: NavBackStackEntry?,
    onHomeClick: () -> Unit,
    onMyPageClick: () -> Unit,
    onSettingClick: () -> Unit
) {
    val useBottomNavScreen = listOf("home", MyPageRoute.MyPageRoute.name, SettingRoute.SettingRoute.name)
    val currentRoute = currentBackStack?.destination?.route ?: "home"
    if (currentRoute in useBottomNavScreen){
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