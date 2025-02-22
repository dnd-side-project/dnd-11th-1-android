package com.materip.feature_home3.ui.profile_content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.materip.core_designsystem.MatetripGrade
import com.materip.feature_home3.ui.component.BasicInfo
import com.materip.feature_home3.ui.component.FoodPreferenceInfo
import com.materip.feature_home3.ui.component.MyImageInfo
import com.materip.feature_home3.ui.component.OverviewInfo
import com.materip.feature_home3.ui.component.TravelInterestInfo
import com.materip.feature_home3.ui.component.TravelStyleInfo
import com.materip.feature_home3.ui.component.UserCommentInfo
import com.materip.feature_home3.ui.component.calculateAgeCategory
import com.materip.feature_home3.viewModel.ProfileViewModel


@Composable
fun ProfileContent(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profileDetails by viewModel.profileDetails.collectAsStateWithLifecycle()

    profileDetails?.let { userInfo ->
        val scrollState = rememberScrollState()
        val levelInfo = when (userInfo.grade) {
            1.toString() -> MatetripGrade.level_1
            2.toString() -> MatetripGrade.level_2
            3.toString() -> MatetripGrade.level_3
            else -> MatetripGrade.level_4
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(horizontal = 20.dp, vertical = 20.dp),
        ) {
            val ageCategory = calculateAgeCategory(userInfo.birthYear)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = scrollState)
            ) {
                OverviewInfo(userInfo, levelInfo, ageCategory)
                Spacer(Modifier.height(20.dp))
                UserCommentInfo(userInfo)
                Spacer(Modifier.height(30.dp))
                BasicInfo(
                    nickname = userInfo.nickname,
                    gender = userInfo.gender,
                    age = ageCategory,
                    authenticatorType = userInfo.provider
                )
                Spacer(Modifier.height(40.dp))
                TravelInterestInfo(interests = userInfo.travelPreferences)
                Spacer(Modifier.height(40.dp))
                TravelStyleInfo(styles = userInfo.travelStyles)
                Spacer(Modifier.height(40.dp))
                FoodPreferenceInfo(foodPreferences = userInfo.foodPreferences)
                Spacer(Modifier.height(40.dp))
                MyImageInfo(images = userInfo.userImageUrls)
            }
        }
    }
}
