package com.materip.feature_home3.ui.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.materip.core_designsystem.icon.Badges.profile_default_badge
import com.materip.core_designsystem.icon.Icons.enter_24_icon
import com.materip.core_designsystem.icon.Logo
import com.materip.core_designsystem.theme.MateTripColors.Blue_04
import com.materip.core_designsystem.theme.MateTripColors.Gray_12
import com.materip.core_designsystem.theme.MateTripTypographySet

@Composable
fun ShowUserProfile(
    profileImageUrl: String,
    nickname: String,
    birthYear: Int,
    gender: String,
    onNavigateToProfile: () -> Unit
) {
    val ageCategory = calculateAgeCategory(birthYear)

    val imageList: Any = if (profileImageUrl.isNotEmpty()) {
        if (profileImageUrl.startsWith("http")) {
            profileImageUrl
        } else if (profileImageUrl.startsWith("content://") || profileImageUrl.startsWith("file://")) {
            Uri.parse(profileImageUrl)
        } else {
            Logo.default_image.toString()
        }
    } else {
        Logo.default_image.toString()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp)
            .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .background(shape = CircleShape, color = Blue_04)
                .clip(CircleShape)
        ) {
            if (profileImageUrl.isEmpty()) {
                Image(
                    painter = painterResource(id = profile_default_badge),
                    contentDescription = "프로필 기본 이미지",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            } else {
                SubcomposeAsyncImage(
                    model = imageList,
                    contentDescription = "Network Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = nickname, style = MateTripTypographySet.title04)
            Text(
                text = "$ageCategory · $gender",
                style = MateTripTypographySet.body05, color = Gray_12
            )
        }

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(
                onClick = onNavigateToProfile,
                modifier = Modifier
                    .padding(top = 7.dp)
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = enter_24_icon),
                    contentDescription = "프로필 상세 화면으로 이동"
                )
            }
        }
    }

    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
        color = Color(0xFFEEEEEE)
    )
}