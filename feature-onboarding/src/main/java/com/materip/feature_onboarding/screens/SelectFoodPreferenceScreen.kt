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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.R
import com.materip.core_model.ui_model.FoodPreference
import com.materip.core_model.ui_model.TripInterest
import com.materip.matetrip.component.MateTripButton
import com.materip.matetrip.component.OnboardingElevatedCard
import com.materip.matetrip.component.ProgressIndicator
import com.materip.matetrip.icon.Badges
import com.materip.matetrip.theme.MateTripTypographySet
import com.materip.matetrip.theme.MatetripColor
import com.materip.matetrip.theme.OnboardingMessage

@Composable
fun SelectFoodStyleRoute(
    userInfo: String?,
    tripInterests: String?,
    tripStyles: String?,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
){
    SelectFoodPreferenceScreen(
        onBackClick = {},
        onNextClick = {}
    )
}

@Composable
fun SelectFoodPreferenceScreen(
    onBackClick: () -> Unit,
    onNextClick: (List<FoodPreference>) -> Unit,
){
    val scrollState = rememberScrollState()
    val foodStyles = remember{ mutableStateListOf<FoodPreference>() }
    val isEnabled = remember{derivedStateOf{foodStyles.isNotEmpty()}}
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
            currentProgress = 1f
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
            modifier = Modifier.weight(1f).verticalScroll(state = scrollState)
        ){
            Text(
                text = OnboardingMessage.MESSAGE_4.title,
                style = MateTripTypographySet.onboardingTitle
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = OnboardingMessage.MESSAGE_4.message,
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
                    text = "육류",
                    icon = Badges.meat_badge,
                    iconSize = 48.dp,
                    isSelected = FoodPreference.MEAT in foodStyles,
                    tint = if(FoodPreference.MEAT in foodStyles) Color.White else MatetripColor.Blue_01,
                    onClick = {
                        if (FoodPreference.MEAT in foodStyles){
                            foodStyles.remove(FoodPreference.MEAT)
                        } else {
                            foodStyles.add(FoodPreference.MEAT)
                        }
                    }
                )
                Spacer(Modifier.width(10.dp))
                OnboardingElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    text = "해산물",
                    icon = Badges.unplanned_badge,
                    iconSize = 48.dp,
                    isSelected = FoodPreference.SEAFOOD in foodStyles,
                    tint = if(FoodPreference.SEAFOOD in foodStyles) Color.White else MatetripColor.Blue_01,
                    onClick = {
                        if (FoodPreference.SEAFOOD in foodStyles){
                            foodStyles.remove(FoodPreference.SEAFOOD)
                        } else {
                            foodStyles.add(FoodPreference.SEAFOOD)
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
                    text = "밥류",
                    icon = Badges.rice_badge,
                    iconSize = 48.dp,
                    isSelected = FoodPreference.RICE in foodStyles,
                    tint = if(FoodPreference.RICE in foodStyles) Color.White else MatetripColor.Blue_01,
                    onClick = {
                        if (FoodPreference.RICE in foodStyles){
                            foodStyles.remove(FoodPreference.RICE)
                        } else {
                            foodStyles.add(FoodPreference.RICE)
                        }
                    }
                )
                Spacer(Modifier.width(10.dp))
                OnboardingElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    text = "채소",
                    icon = Badges.dutch_pay_badge,
                    iconSize = 48.dp,
                    isSelected = FoodPreference.VEGETABLES in foodStyles,
                    tint = if(FoodPreference.VEGETABLES in foodStyles) Color.White else MatetripColor.Blue_01,
                    onClick = {
                        if (FoodPreference.VEGETABLES in foodStyles){
                            foodStyles.remove(FoodPreference.VEGETABLES)
                        } else {
                            foodStyles.add(FoodPreference.VEGETABLES)
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
                    text = "커피",
                    icon = Badges.coffee_badge,
                    iconSize = 48.dp,
                    isSelected = FoodPreference.COFFEE in foodStyles,
                    tint = if(FoodPreference.COFFEE in foodStyles) Color.White else MatetripColor.Blue_01,
                    onClick = {
                        if (FoodPreference.COFFEE in foodStyles){
                            foodStyles.remove(FoodPreference.COFFEE)
                        } else {
                            foodStyles.add(FoodPreference.COFFEE)
                        }
                    }
                )
                Spacer(Modifier.width(10.dp))
                OnboardingElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    text = "디저트",
                    icon = Badges.dessert_badge,
                    iconSize = 48.dp,
                    isSelected = FoodPreference.DESSERT in foodStyles,
                    tint = if(FoodPreference.DESSERT in foodStyles) Color.White else MatetripColor.Blue_01,
                    onClick = {
                        if (FoodPreference.DESSERT in foodStyles){
                            foodStyles.remove(FoodPreference.DESSERT)
                        } else {
                            foodStyles.add(FoodPreference.DESSERT)
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
                    text = "패스트푸드",
                    icon = Badges.fast_food_badge,
                    iconSize = 48.dp,
                    isSelected = FoodPreference.FAST_FOOD in foodStyles,
                    tint = if(FoodPreference.FAST_FOOD in foodStyles) Color.White else MatetripColor.Blue_01,
                    onClick = {
                        if (FoodPreference.FAST_FOOD in foodStyles){
                            foodStyles.remove(FoodPreference.FAST_FOOD)
                        } else {
                            foodStyles.add(FoodPreference.FAST_FOOD)
                        }
                    }
                )
                Spacer(Modifier.width(10.dp))
                OnboardingElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    text = "스트릿푸드",
                    icon = Badges.street_food_badge,
                    iconSize = 48.dp,
                    isSelected = FoodPreference.STREET_FOOD in foodStyles,
                    tint = if(FoodPreference.STREET_FOOD in foodStyles) Color.White else MatetripColor.Blue_01,
                    onClick = {
                        if (FoodPreference.STREET_FOOD in foodStyles){
                            foodStyles.remove(FoodPreference.STREET_FOOD)
                        } else {
                            foodStyles.add(FoodPreference.STREET_FOOD)
                        }
                    }
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        MateTripButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onNextClick(foodStyles) },
            buttonText = "다음" ,
            enabled = isEnabled.value
        )
    }
}

@Preview
@Composable
private fun SelectFoodStyleUiTest(){
    SelectFoodPreferenceScreen(
        onBackClick = {},
        onNextClick = {}
    )
}