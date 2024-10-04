package com.materip.feature_home3.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.icon.Icons.gender_icon
import com.materip.core_designsystem.icon.Icons.party_icon
import com.materip.core_designsystem.theme.MateTripColors.Primary
import com.materip.core_designsystem.theme.MateTripTypographySet

@Composable
fun ShowPreferredPerson(
    preferredAge: String,
    preferredGender: String,
    birthYear: Int,
    userGender: String
) {
    val ageCategory = calculateAgeCategory(birthYear)

    Column(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 50.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "선호 동행자", style = MateTripTypographySet.headline05)
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = party_icon),
                    contentDescription = "선호하는 동행자 나이",
                    tint = Primary
                )
                if (preferredAge == "상관없음") {
                    Text(text = preferredAge, style = MateTripTypographySet.title03)
                } else {
                    Text(text = ageCategory, style = MateTripTypographySet.title03)
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = gender_icon),
                    contentDescription = "선호하는 동행자 성별",
                    tint = Primary
                )
                if (preferredGender == "상관없음") {
                    Text(text = preferredGender, style = MateTripTypographySet.title03)
                } else {
                    Text(text = userGender, style = MateTripTypographySet.title03)
                }
            }
        }
    }
}