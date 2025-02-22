package com.materip.feature_onboarding.screens

import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.component.MateTripButton
import com.materip.core_designsystem.component.OnboardingElevatedCard
import com.materip.core_designsystem.component.ProgressIndicator
import com.materip.core_designsystem.icon.Badges
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.core_designsystem.theme.OnboardingMessage
import com.materip.core_model.ui_model.SelectInterests
import com.materip.core_model.ui_model.TravelInterest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun SelectTripInterestRoute(
    userInfo: String?,
    onBackClick: () -> Unit,
    onNextClick: (userInfo: String?, interests: String) -> Unit,
){
    SelectTripInterestScreen(
        onBackClick = onBackClick,
        onNextClick = {plan, money, place, speed ->
            val tripInterests = listOf(plan, money, place, speed)
            val tripInterestJson = Json.encodeToString(SelectInterests(tripInterests))
            onNextClick(userInfo, tripInterestJson)
        }
    )
}

@Composable
fun SelectTripInterestScreen(
    onBackClick: () -> Unit,
    onNextClick: (TravelInterest, TravelInterest, TravelInterest, TravelInterest) -> Unit,
){
    val scrollState = rememberScrollState()
    var planned by remember{mutableStateOf<TravelInterest?>(null)}
    var money by remember{mutableStateOf<TravelInterest?>(null)}
    var place by remember{mutableStateOf<TravelInterest?>(null)}
    var speed by remember{mutableStateOf<TravelInterest?>(null)}
    var isEnabled = remember{derivedStateOf{planned != null && money != null && place != null && speed != null}}
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
            currentProgress = 0.5f
        )
        Spacer(Modifier.height(10.dp))
        Spacer(Modifier.height(20.dp))
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = onBackClick
        ){
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(com.materip.core_designsystem.R.drawable.arrow_back_24px),
                contentDescription = "Back Button"
            )
        }
        Spacer(Modifier.height(20.dp))
        Column(
            modifier = Modifier.weight(1f).verticalScroll(state = scrollState)
        ){
            Text(
                text = OnboardingMessage.MESSAGE_2.title,
                style = MateTripTypographySet.onboardingTitle
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = OnboardingMessage.MESSAGE_2.message,
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
                    text = "계획적",
                    icon = Badges.planned_badge,
                    iconSize = 48.dp,
                    isSelected = planned == TravelInterest.PLANNED,
                    tint = if(planned == TravelInterest.PLANNED) Color.White else MateTripColors.Blue_01,
                    onClick = {planned = TravelInterest.PLANNED}
                )
                Spacer(Modifier.width(10.dp))
                OnboardingElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    text = "무계획",
                    icon = Badges.unplanned_badge,
                    iconSize = 48.dp,
                    isSelected = planned == TravelInterest.UNPLANNED,
                    tint = if(planned == TravelInterest.UNPLANNED) Color.White else MateTripColors.Blue_01,
                    onClick = {planned = TravelInterest.UNPLANNED}
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
                    text = "공금",
                    icon = Badges.public_funds_badge,
                    iconSize = 48.dp,
                    isSelected = money == TravelInterest.PUBLIC_MONEY,
                    tint = if(money == TravelInterest.PUBLIC_MONEY) Color.White else MateTripColors.Blue_01,
                    onClick = {money = TravelInterest.PUBLIC_MONEY}
                )
                Spacer(Modifier.width(10.dp))
                OnboardingElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    text = "더치페이",
                    icon = Badges.dutch_pay_badge,
                    iconSize = 48.dp,
                    isSelected = money == TravelInterest.DUTCH_PAY,
                    tint = if(money == TravelInterest.DUTCH_PAY) Color.White else MateTripColors.Blue_01,
                    onClick = {money = TravelInterest.DUTCH_PAY}
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
                    text = "찾아본 곳",
                    icon = Badges.place_badge,
                    iconSize = 48.dp,
                    isSelected = place == TravelInterest.LOOKING_FOR,
                    tint = if(place == TravelInterest.LOOKING_FOR) Color.White else MateTripColors.Blue_01,
                    onClick = {place = TravelInterest.LOOKING_FOR}
                )
                Spacer(Modifier.width(10.dp))
                OnboardingElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    text = "끌리는 곳",
                    icon = Badges.attractive_badge,
                    iconSize = 48.dp,
                    isSelected = place == TravelInterest.DRAWN_TO,
                    tint = if(place == TravelInterest.DRAWN_TO) Color.White else MateTripColors.Blue_01,
                    onClick = {place = TravelInterest.DRAWN_TO}
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
                    text = "빨리빨리",
                    icon = Badges.rush_badge,
                    iconSize = 48.dp,
                    isSelected = speed == TravelInterest.QUICKLY,
                    tint = if(speed == TravelInterest.QUICKLY) Color.White else MateTripColors.Blue_01,
                    onClick = {speed = TravelInterest.QUICKLY}
                )
                Spacer(Modifier.width(10.dp))
                OnboardingElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    text = "느긋하게",
                    icon = Badges.leisurely_badge,
                    iconSize = 48.dp,
                    isSelected = speed == TravelInterest.LEISURELY,
                    tint = if(speed == TravelInterest.LEISURELY) Color.White else MateTripColors.Blue_01,
                    onClick = {speed = TravelInterest.LEISURELY}
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        MateTripButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onNextClick(planned!!, money!!, place!!, speed!!) },
            buttonText = "다음" ,
            enabled = isEnabled.value
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectTripInterestUITest(){
    val context = LocalContext.current
    SelectTripInterestScreen(onBackClick = {}, onNextClick = {planned, money, place, speed ->
        Toast.makeText(context, "${planned} / ${money} / ${place} / ${speed}", Toast.LENGTH_LONG).show()
    })
}