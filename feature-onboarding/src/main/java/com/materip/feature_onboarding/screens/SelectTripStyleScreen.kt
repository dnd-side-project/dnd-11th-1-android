package com.materip.feature_onboarding.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.R
import com.materip.core_designsystem.component.MateTripButton
import com.materip.core_designsystem.component.OnboardingElevatedCard
import com.materip.core_designsystem.component.ProgressIndicator
import com.materip.core_designsystem.icon.Badges
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.core_designsystem.theme.OnboardingMessage
import com.materip.core_model.ui_model.SelectStyles
import com.materip.core_model.ui_model.TravelStyle
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun SelectTripStyleRoute(
    userInfo: String?,
    tripInterests: String?,
    onBackClick: () -> Unit,
    onNextClick: (userInfo: String?, tripInterests: String?, tripStyles: String) -> Unit,
){
    SelectTripStyleScreen(
        onBackClick = onBackClick,
        onNextClick = {tripStyles ->
            val tripStylesJson = Json.encodeToString(SelectStyles(tripStyles))
            onNextClick(userInfo, tripInterests, tripStylesJson)
        }
    )
}

@Composable
fun SelectTripStyleScreen(
    onBackClick: () -> Unit,
    onNextClick: (List<TravelStyle>) -> Unit,
){
    val scrollState = rememberScrollState()
    val styles = remember{ mutableStateListOf<TravelStyle>() }
    var isEnabled = remember{derivedStateOf{styles.isNotEmpty()}}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 30.dp)
            .padding(bottom = 40.dp)
    ) {
        Spacer(Modifier.height(10.dp))
        ProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            currentProgress = 0.75f
        )
        Spacer(Modifier.height(10.dp))
        Spacer(Modifier.height(20.dp))
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = onBackClick
        ){
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.arrow_back_24px),
                contentDescription = "Back Button"
            )
        }
        Spacer(Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(state = scrollState)
        ){
            Text(
                text = OnboardingMessage.MESSAGE_3.title,
                style = MateTripTypographySet.onboardingTitle
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = OnboardingMessage.MESSAGE_3.message,
                style = MateTripTypographySet.onboardingMessage
            )
            Spacer(Modifier.height(40.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                OnboardingElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    text = "맛집탐방",
                    icon = Badges.restaurant_badge,
                    iconSize = 48.dp,
                    isSelected = TravelStyle.RESTAURANT_TOUR in styles,
                    tint = if(TravelStyle.RESTAURANT_TOUR in styles) Color.White else MateTripColors.Blue_01,
                    onClick = {
                        if(TravelStyle.RESTAURANT_TOUR in styles){
                            styles.remove(TravelStyle.RESTAURANT_TOUR)
                        } else {
                            styles.add(TravelStyle.RESTAURANT_TOUR)
                        }
                    }
                )
                Spacer(Modifier.width(10.dp))
                OnboardingElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    text = "카페투어",
                    icon = Badges.cafe_tour_badge,
                    iconSize = 48.dp,
                    isSelected = TravelStyle.CAFE_TOUR in styles,
                    tint = if(TravelStyle.CAFE_TOUR in styles) Color.White else MateTripColors.Blue_01,
                    onClick = {
                        if(TravelStyle.CAFE_TOUR in styles){
                            styles.remove(TravelStyle.CAFE_TOUR)
                        } else {
                            styles.add(TravelStyle.CAFE_TOUR)
                        }
                    }
                )
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                OnboardingElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    text = "인생샷",
                    icon = Badges.image_badge,
                    iconSize = 48.dp,
                    isSelected = TravelStyle.LIFE_SHOT in styles,
                    tint = if(TravelStyle.LIFE_SHOT in styles) Color.White else MateTripColors.Blue_01,
                    onClick = {
                        if(TravelStyle.LIFE_SHOT in styles){
                            styles.remove(TravelStyle.LIFE_SHOT)
                        } else {
                            styles.add(TravelStyle.LIFE_SHOT)
                        }
                    }
                )
                Spacer(Modifier.width(10.dp))
                OnboardingElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    text = "관광지",
                    icon = Badges.tourist_badge,
                    iconSize = 48.dp,
                    isSelected = TravelStyle.TOURIST_DESTINATION in styles,
                    tint = if(TravelStyle.TOURIST_DESTINATION in styles) Color.White else MateTripColors.Blue_01,
                    onClick = {
                        if(TravelStyle.TOURIST_DESTINATION in styles){
                            styles.remove(TravelStyle.TOURIST_DESTINATION)
                        } else {
                            styles.add(TravelStyle.TOURIST_DESTINATION)
                        }
                    }
                )
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                OnboardingElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    text = "액티비티",
                    icon = Badges.activity_badge,
                    iconSize = 48.dp,
                    isSelected = TravelStyle.ACTIVITY in styles,
                    tint = if(TravelStyle.ACTIVITY in styles) Color.White else MateTripColors.Blue_01,
                    onClick = {
                        if(TravelStyle.ACTIVITY in styles){
                            styles.remove(TravelStyle.ACTIVITY)
                        } else {
                            styles.add(TravelStyle.ACTIVITY)
                        }
                    }
                )
                Spacer(Modifier.width(10.dp))
                OnboardingElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    text = "힐링",
                    icon = Badges.healing_badge,
                    iconSize = 48.dp,
                    isSelected = TravelStyle.HEALING in styles,
                    tint = if(TravelStyle.HEALING in styles) Color.White else MateTripColors.Blue_01,
                    onClick = {
                        if(TravelStyle.HEALING in styles){
                            styles.remove(TravelStyle.HEALING)
                        } else {
                            styles.add(TravelStyle.HEALING)
                        }
                    }
                )
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                OnboardingElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    text = "드라이브",
                    icon = Badges.drive_badge,
                    iconSize = 48.dp,
                    isSelected = TravelStyle.DRIVE in styles,
                    tint = if(TravelStyle.DRIVE in styles) Color.White else MateTripColors.Blue_01,
                    onClick = {
                        if(TravelStyle.DRIVE in styles) {
                            styles.remove(TravelStyle.DRIVE)
                        } else {
                            styles.add(TravelStyle.DRIVE)
                        }
                    }
                )
                Spacer(Modifier.width(10.dp))
                OnboardingElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    text = "패키지 여행",
                    icon = Badges.package_badge,
                    iconSize = 48.dp,
                    isSelected = TravelStyle.PACKAGE_TOUR in styles,
                    tint = if(TravelStyle.PACKAGE_TOUR in styles) Color.White else MateTripColors.Blue_01,
                    onClick = {
                        if(TravelStyle.PACKAGE_TOUR in styles){
                            styles.remove(TravelStyle.PACKAGE_TOUR)
                        } else {
                            styles.add(TravelStyle.PACKAGE_TOUR)
                        }
                    }
                )
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                OnboardingElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    text = "쇼핑",
                    icon = Badges.shopping_badge,
                    iconSize = 48.dp,
                    isSelected = TravelStyle.SHOPPING in styles,
                    tint = if(TravelStyle.SHOPPING in styles) Color.White else MateTripColors.Blue_01,
                    onClick = {
                        if(TravelStyle.SHOPPING in styles) {
                            styles.remove(TravelStyle.SHOPPING)
                        } else {
                            styles.add(TravelStyle.SHOPPING)
                        }
                    }
                )
                Spacer(Modifier.width(10.dp))
                OnboardingElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    text = "문화예술",
                    icon = Badges.art_badge,
                    iconSize = 48.dp,
                    isSelected = TravelStyle.CULTURE_AND_ARTS in styles,
                    tint = if(TravelStyle.CULTURE_AND_ARTS in styles) Color.White else MateTripColors.Blue_01,
                    onClick = {
                        if(TravelStyle.CULTURE_AND_ARTS in styles) {
                            styles.remove(TravelStyle.CULTURE_AND_ARTS)
                        } else {
                            styles.add(TravelStyle.CULTURE_AND_ARTS)
                        }
                    }
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        MateTripButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onNextClick(styles) },
            buttonText = "다음" ,
            enabled = isEnabled.value
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectTripStyleUITest(){
    SelectTripStyleScreen(
        onBackClick = {},
        onNextClick = {

        }
    )
}