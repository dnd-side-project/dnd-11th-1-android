package com.materip.matetrip.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.R
import com.materip.core_model.ui_model.FoodPreference
import com.materip.core_model.ui_model.TravelInterest
import com.materip.core_model.ui_model.TravelStyle
import com.materip.matetrip.icon.Badges
import com.materip.matetrip.theme.MateTripTypographySet
import com.materip.matetrip.theme.MatetripColor
import com.materip.matetrip.theme.MatetripColor.Gray_11
import com.materip.matetrip.theme.MatetripColor.Primary

@Composable
fun RegionTag(
    regions: List<String>,
    onClick: (String) -> Unit,
) {
    var selectedRegion by remember { mutableStateOf("전체") }

    LazyRow(
        modifier = Modifier
            .background(color = Color.LightGray) // preview에서 보려고 추가 사용 시 수정
            .padding(horizontal = 16.dp)
    ) {
        items(regions) { region ->
            val isSelected = region == selectedRegion
            val backgroundColor = if (isSelected) Primary else Color.White
            val textColor = if (isSelected) Color.White else Gray_11

            Button(
                onClick = {
                    selectedRegion = region
                    onClick(region)
                },
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .height(36.dp)
                    .padding(horizontal = 4.dp)
                    .wrapContentHeight(align = Alignment.Top),
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColor,
                    contentColor = textColor,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Gray_11
                )
            ) {
                Text(
                    text = region,
                    style = MateTripTypographySet.homeTag,
                )
            }
        }
    }
}

@Composable
fun ProfileTag(
    tagName: String
){
    val tag = when(tagName){
        TravelStyle.RESTAURANT_TOUR.name -> Pair("맛집탐방", Badges.restaurant_badge)
        TravelStyle.DRIVE.name -> Pair("드라이브",Badges.drive_badge)
        TravelStyle.HEALING.name -> Pair("힐링",Badges.healing_badge)
        TravelStyle.ACTIVITY.name -> Pair("액티비티",Badges.activity_badge)
        TravelStyle.SHOPPING.name -> Pair("쇼핑",Badges.shopping_badge)
        TravelStyle.CAFE_TOUR.name -> Pair("카페투어",Badges.cafe_tour_badge)
        TravelStyle.CULTURE_AND_ARTS.name -> Pair("문화예술",Badges.art_badge)
        TravelStyle.LIFE_SHOT.name -> Pair("인생샷", Badges.image_badge)
        TravelStyle.PACKAGE_TOUR.name -> Pair("패키지 여행",Badges.package_badge)
        TravelStyle.TOURIST_DESTINATION.name -> Pair("관광지",Badges.tourist_badge)
        TravelInterest.PLANNED.name -> Pair("계획적",Badges.planned_badge)
        TravelInterest.UNPLANNED.name -> Pair("무계획",Badges.unplanned_badge)
        TravelInterest.QUICKLY.name -> Pair("빨리빨리",Badges.rush_badge)
        TravelInterest.LEISURELY.name -> Pair("느긋하게",Badges.leisurely_badge)
        TravelInterest.DRAWN_TO.name -> Pair("끌리는 곳",Badges.attractive_badge)
        TravelInterest.DUTCH_PAY.name -> Pair("더치페이",Badges.dutch_pay_badge)
        TravelInterest.LOOKING_FOR.name -> Pair("찾아본 곳",Badges.place_badge)
        TravelInterest.PUBLIC_MONEY.name -> Pair("공금",Badges.public_funds_badge)
        FoodPreference.FAST_FOOD.name -> Pair("패스트푸드",Badges.fast_food_badge)
        FoodPreference.STREET_FOOD.name -> Pair("스트릿푸드",Badges.street_food_badge)
        FoodPreference.RICE.name -> Pair("밥류",Badges.rice_badge)
        FoodPreference.MEAT.name -> Pair("육류",Badges.meat_badge)
        FoodPreference.DESSERT.name -> Pair("디저트",Badges.dessert_badge)
        FoodPreference.VEGETABLES.name -> Pair("채소",Badges.vegetarian_badge)
        FoodPreference.SEAFOOD.name -> Pair("해산물",Badges.seafood_badge)
        FoodPreference.COFFEE.name -> Pair("커피",Badges.coffee_badge)
        else -> Pair("Default",R.drawable.ic_app_logo_loading)
    }
    Row(
        modifier = Modifier.height(28.dp).wrapContentWidth()
            .background(color = MatetripColor.Blue_04, shape = RoundedCornerShape(size = 5.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier = Modifier.size(18.dp),
            painter = painterResource(tag.second),
            contentDescription = "Tag Icon"
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = tag.first,
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                fontWeight = FontWeight(500),
                color = MatetripColor.Gray_10,
                textAlign = TextAlign.Justify,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
    }
}

@Composable
fun CustomClickableTag(
    modifier: Modifier = Modifier,
    tagName: String,
    shape: Shape,
    fontSize: TextUnit,
    selectedTextColor: Color,
    notSelectedTextColor: Color,
    isSelected: Boolean,
    selectedColor: Color,
    notSelectedColor: Color,
    trailingIcon: Int? = null,
    selectedIconTint: Color = Color.Transparent,
    notSelectedIconTint: Color = Color.Transparent,
    onClick: () -> Unit,
){
    Row(
        modifier = modifier.background(color = if(isSelected) selectedColor else notSelectedColor, shape = shape)
            .clickable{onClick()}
            .padding(horizontal = 8.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        if(trailingIcon != null){
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(trailingIcon),
                tint = if(isSelected) selectedIconTint else notSelectedIconTint,
                contentDescription = "Trailing Icon"
            )
            Spacer(Modifier.width(10.dp))
        }
        Text(
            text = tagName,
            fontSize = fontSize,
            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
            fontWeight = FontWeight(500),
            color = if(isSelected) selectedTextColor else notSelectedTextColor
        )
    }
}

@Composable
fun NormalTag(
    modifier: Modifier,
    tagName: String,
    shape: Shape,
    color: Color,
    textStyle: TextStyle
){
    Row(
        modifier = modifier
            .background(color = color, shape = shape)
            .padding(10.dp)
    ){
        Text(
            text = tagName,
            style = textStyle
        )
    }
}

@Preview
@Composable
fun RegionTagPreview() {
    val regions = listOf("전체", "수도권", "경기도", "충청도", "강원도", "경상도", "전라도", "제주도", "해외")
    Column{
        RegionTag(regions = regions, onClick = {})
        ProfileTag(TravelStyle.RESTAURANT_TOUR.name)
    }
}