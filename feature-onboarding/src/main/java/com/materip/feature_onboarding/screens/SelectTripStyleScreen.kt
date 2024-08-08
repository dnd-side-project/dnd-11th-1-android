package com.materip.feature_onboarding.screens

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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.R
import com.materip.core_model.ui_model.TripInterest
import com.materip.core_model.ui_model.TripStyle
import com.materip.matetrip.component.MateTripButton
import com.materip.matetrip.component.OnboardingElevatedCard
import com.materip.matetrip.component.ProgressIndicator
import com.materip.matetrip.icon.Badges
import com.materip.matetrip.theme.MateTripTypographySet
import com.materip.matetrip.theme.MatetripColor
import com.materip.matetrip.theme.OnboardingMessage

@Composable
fun SelectTripStyleRoute(
    onBackClick: () -> Unit
){
    SelectTripStyleScreen(
        onBackClick = onBackClick,
        onNextClick = {

        }
    )
}

@Composable
fun SelectTripStyleScreen(
    onBackClick: () -> Unit,
    onNextClick: (List<TripStyle>) -> Unit,
){
    val scrollState = rememberScrollState()
    val styles = remember{ mutableStateListOf<TripStyle>() }
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
                    isSelected = TripStyle.RESTAURANT_TOUR in styles,
                    tint = if(TripStyle.RESTAURANT_TOUR in styles) Color.White else MatetripColor.Blue_01,
                    onClick = {
                        if(TripStyle.RESTAURANT_TOUR in styles){
                            styles.remove(TripStyle.RESTAURANT_TOUR)
                        } else {
                            styles.add(TripStyle.RESTAURANT_TOUR)
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
                    isSelected = TripStyle.CAFE_TOUR in styles,
                    tint = if(TripStyle.CAFE_TOUR in styles) Color.White else MatetripColor.Blue_01,
                    onClick = {
                        if(TripStyle.CAFE_TOUR in styles){
                            styles.remove(TripStyle.CAFE_TOUR)
                        } else {
                            styles.add(TripStyle.CAFE_TOUR)
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
                    isSelected = TripStyle.LIFE_SHOT in styles,
                    tint = if(TripStyle.LIFE_SHOT in styles) Color.White else MatetripColor.Blue_01,
                    onClick = {styles.add(TripStyle.LIFE_SHOT)}
                )
                Spacer(Modifier.width(10.dp))
                OnboardingElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    text = "관광지",
                    icon = Badges.tourist_badge,
                    iconSize = 48.dp,
                    isSelected = TripStyle.TOURIST_DESTINATION in styles,
                    tint = if(TripStyle.TOURIST_DESTINATION in styles) Color.White else MatetripColor.Blue_01,
                    onClick = {
                        if(TripStyle.TOURIST_DESTINATION in styles){
                            styles.remove(TripStyle.TOURIST_DESTINATION)
                        } else {
                            styles.add(TripStyle.TOURIST_DESTINATION)
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
                    isSelected = TripStyle.ACTIVITY in styles,
                    tint = if(TripStyle.ACTIVITY in styles) Color.White else MatetripColor.Blue_01,
                    onClick = {
                        if(TripStyle.ACTIVITY in styles){
                            styles.remove(TripStyle.ACTIVITY)
                        } else {
                            styles.add(TripStyle.ACTIVITY)
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
                    isSelected = TripStyle.HEALING in styles,
                    tint = if(TripStyle.HEALING in styles) Color.White else MatetripColor.Blue_01,
                    onClick = {
                        if(TripStyle.HEALING in styles){
                            styles.remove(TripStyle.HEALING)
                        } else {
                            styles.add(TripStyle.HEALING)
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
                    isSelected = TripStyle.DRIVE in styles,
                    tint = if(TripStyle.DRIVE in styles) Color.White else MatetripColor.Blue_01,
                    onClick = {
                        if(TripStyle.DRIVE in styles) {
                            styles.remove(TripStyle.DRIVE)
                        } else {
                            styles.add(TripStyle.DRIVE)
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
                    isSelected = TripStyle.PACKAGE_TOUR in styles,
                    tint = if(TripStyle.PACKAGE_TOUR in styles) Color.White else MatetripColor.Blue_01,
                    onClick = {
                        if(TripStyle.PACKAGE_TOUR in styles){
                            styles.remove(TripStyle.PACKAGE_TOUR)
                        } else {
                            styles.add(TripStyle.PACKAGE_TOUR)
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
                    isSelected = TripStyle.SHOPPING in styles,
                    tint = if(TripStyle.SHOPPING in styles) Color.White else MatetripColor.Blue_01,
                    onClick = {
                        if(TripStyle.SHOPPING in styles) {
                            styles.remove(TripStyle.SHOPPING)
                        } else {
                            styles.add(TripStyle.SHOPPING)
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
                    isSelected = TripStyle.CULTURE_AND_ARTS in styles,
                    tint = if(TripStyle.CULTURE_AND_ARTS in styles) Color.White else MatetripColor.Blue_01,
                    onClick = {
                        if(TripStyle.CULTURE_AND_ARTS in styles) {
                            styles.remove(TripStyle.CULTURE_AND_ARTS)
                        } else {
                            styles.add(TripStyle.CULTURE_AND_ARTS)
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