package com.materip.feature_mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_model.ui_model.FoodPreference
import com.materip.core_model.ui_model.Gender
import com.materip.core_model.ui_model.TravelStyle
import com.materip.feature_mypage.R
import com.materip.matetrip.component.CustomClickableTag
import com.materip.matetrip.component.ImageLoadView
import com.materip.matetrip.component.NormalTopBar
import com.materip.matetrip.component.SelectableDialog
import com.materip.matetrip.component.Spinner
import com.materip.matetrip.component.UnderlinedTextField
import com.materip.matetrip.icon.Badges
import com.materip.matetrip.icon.Icons
import com.materip.matetrip.theme.MatetripColor

@Composable
fun EditProfileRoute(){
    EditProfileScreen()
}

@Composable
fun EditProfileScreen(){
    val scrollState = rememberScrollState()
    var name by remember{mutableStateOf("찬란한 바닷가")} /** initial nickname */
    var introduction by remember{mutableStateOf("")}
    var birth by remember{mutableStateOf(2024)}
    var gender by remember{mutableStateOf(Gender.FEMALE)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
    ){
        NormalTopBar(
            title = "프로필 수정",
            onBackClick = { /** 뒤로 가기 */ },
            onClick = { /** 수정 완료 */ },
            menuText = "확인"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ){
            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(color = MatetripColor.Blue_04, shape = CircleShape)
                        .clickable {
                            /** 갤러리 or 카메라 에 연결 */
                        },
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        painter = painterResource(Icons.camera_icon),
                        contentDescription = "Camera Icon"
                    )
                }
            }
            Spacer(Modifier.height(30.dp))
            NicknameEdit(
                nickname = name,
                onNicknameUpdate = {
                    if(name.length <= 6){name = it}
                }
            )
            Spacer(Modifier.height(40.dp))
            MyIntroductionEdit(
                introduction = introduction,
                onIntroductionUpdate = {introduction = it}
            )
            Spacer(Modifier.height(40.dp))
            BirthAndGenderEdit(
                birth = birth.toString(),
                onBirthUpdate = {birth = it.toInt()},
                gender = if(gender == Gender.FEMALE) "여성" else "남성",
                onGenderUpdate = {
                    gender = if(it == "여성") Gender.FEMALE else Gender.MALE
                }
            )
            Spacer(Modifier.height(40.dp))
            TravelInterestEdit(
                onTravelInterestClick = {/** 여행 성향 수정 화면 navigation **/}
            )
            Spacer(Modifier.height(40.dp))
            TravelStyleEdit()
            Spacer(Modifier.height(40.dp))
            FoodPreferenceEdit()
            Spacer(Modifier.height(40.dp))
            SNSEdit()
            Spacer(Modifier.height(40.dp))
            MyImages(pictures = emptyList())
        }
    }
}

@Composable
private fun NicknameEdit(
    nickname: String,
    onNicknameUpdate: (String) -> Unit,
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "닉네임",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(700),
            color = MatetripColor.Gray_11
        )
        Spacer(Modifier.height(12.dp))
        UnderlinedTextField(
            value = nickname,
            onValueChange = onNicknameUpdate,
            placeholder = null,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(400),
            textColor = MatetripColor.Gray_10,
            underlineColor = MatetripColor.Gray_10
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "닉네임은 6글자 이내만 가능합니다.",
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(400),
            color = MatetripColor.gray_06
        )
    }
}

@Composable
private fun MyIntroductionEdit(
    introduction: String,
    onIntroductionUpdate: (String) -> Unit,
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "내 소개",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(700),
            color = MatetripColor.Gray_11
        )
        Spacer(Modifier.height(12.dp))
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(102.dp)
                .background(color = MatetripColor.Blue_04, shape = RoundedCornerShape(size = 10.dp))
                .padding(12.dp),
            value = introduction,
            onValueChange = onIntroductionUpdate,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(400),
                color = MatetripColor.Gray_11
            ),
        ){
            if(introduction.isEmpty()){
                Text(
                    text = "나는 어떤 사람인지, 어떤 여행을 좋아하는 지에 대해서 작성해주세요.(최대 50자)",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400),
                    color = MatetripColor.Gray_07
                )
            } else {
                it()
            }
        }
    }
}

@Composable
private fun BirthAndGenderEdit(
    birth: String,
    onBirthUpdate: (String) -> Unit,
    gender: String,
    onGenderUpdate: (String) -> Unit,
){
    val birthRange = (1950..2024).toMutableList().map{it.toString()}.toMutableList().apply{
        this.add(0, "출생연도 선택")
    }
    val genderRange = listOf("성별", "남성", "여성")
    var isBirthDialogOpen by remember{mutableStateOf(false)}
    var isGenderDialogOpen by remember{mutableStateOf(false)}
    if(isBirthDialogOpen){
        SelectableDialog(
            value = birth,
            onValueChange = onBirthUpdate,
            options = birthRange,
            onDismissRequest = {isBirthDialogOpen = false}
        )
    } else if (isGenderDialogOpen){
        SelectableDialog(
            value = gender,
            onValueChange = onGenderUpdate,
            options = genderRange,
            onDismissRequest = {isGenderDialogOpen = false}
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth()
    ){
        Column(
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = "출생연도",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(700),
                color = MatetripColor.Gray_11
            )
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isBirthDialogOpen = true },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = birth.toString(),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.roboto_medium)),
                    fontWeight = FontWeight(500),
                    color = MatetripColor.Gray_10
                )
                Icon(
                    painter = painterResource(Icons.fold_icon),
                    contentDescription = "Fold Icon"
                )
            }
        }
        Spacer(Modifier.width(40.dp))
        Column(
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = "성별",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(700),
                color = MatetripColor.Gray_11
            )
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isGenderDialogOpen = true },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = gender,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.roboto_medium)),
                    fontWeight = FontWeight(500),
                    color = MatetripColor.Gray_10
                )
                Icon(
                    painter = painterResource(Icons.fold_icon),
                    contentDescription = "Fold Icon"
                )
            }
        }
    }
}

@Composable
private fun TravelInterestEdit(
    onTravelInterestClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = "여행 성향",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(700),
            color = MatetripColor.Gray_11
        )
        IconButton(
            modifier = Modifier.size(16.dp),
            onClick = {onTravelInterestClick()} /** 여행 성향 수정 화면 navigation */
        ){
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Icons.enter_16_icon),
                contentDescription = "Navigation Icon"
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TravelStyleEdit(){
    val dummyData = remember{
        mutableStateListOf(
            TravelStyle.RESTAURANT_TOUR,
            TravelStyle.ACTIVITY,
            TravelStyle.DRIVE,
            TravelStyle.CAFE_TOUR,
            TravelStyle.HEALING
        )
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "여행 스타일",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(700),
            color = Color.Black
        )
        Spacer(Modifier.height(12.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            CustomClickableTag(
                tagName = "맛집탐방",
                shape = RoundedCornerShape(size = 60.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MatetripColor.Blue_01,
                isSelected = TravelStyle.RESTAURANT_TOUR in dummyData,
                selectedColor = MatetripColor.Primary,
                notSelectedColor =MatetripColor.Blue_04,
                trailingIcon = Badges.restaurant_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MatetripColor.Blue_01,
                onClick = {
                    if(TravelStyle.RESTAURANT_TOUR in dummyData){
                        dummyData.remove(TravelStyle.RESTAURANT_TOUR)
                    } else {
                        dummyData.add(TravelStyle.RESTAURANT_TOUR)
                    }
                }
            )
            CustomClickableTag(
                tagName = "인생샷",
                shape = RoundedCornerShape(size = 60.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MatetripColor.Blue_01,
                isSelected = TravelStyle.LIFE_SHOT in dummyData,
                selectedColor = MatetripColor.Primary,
                notSelectedColor = MatetripColor.Blue_04,
                trailingIcon = Badges.image_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MatetripColor.Blue_01,
                onClick = {
                    if(TravelStyle.LIFE_SHOT in dummyData){
                        dummyData.remove(TravelStyle.LIFE_SHOT)
                    } else {
                        dummyData.add(TravelStyle.LIFE_SHOT)
                    }
                }
            )
            CustomClickableTag(
                tagName = "액티비티",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 60.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MatetripColor.Blue_01,
                isSelected = TravelStyle.ACTIVITY in dummyData,
                selectedColor = MatetripColor.Primary,
                notSelectedColor = MatetripColor.Blue_04,
                trailingIcon = Badges.activity_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MatetripColor.Blue_01,
                onClick = {
                    if(TravelStyle.ACTIVITY in dummyData){
                        dummyData.remove(TravelStyle.ACTIVITY)
                    } else {
                        dummyData.add(TravelStyle.ACTIVITY)
                    }
                }
            )
            CustomClickableTag(
                tagName = "드라이브",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 60.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MatetripColor.Blue_01,
                isSelected = TravelStyle.DRIVE in dummyData,
                selectedColor = MatetripColor.Primary,
                notSelectedColor = MatetripColor.Blue_04,
                trailingIcon = Badges.drive_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MatetripColor.Blue_01,
                onClick = {
                    if(TravelStyle.DRIVE in dummyData){
                        dummyData.remove(TravelStyle.DRIVE)
                    } else {
                        dummyData.add(TravelStyle.DRIVE)
                    }
                }
            )
            CustomClickableTag(
                tagName = "카페투어",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 60.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MatetripColor.Blue_01,
                isSelected = TravelStyle.CAFE_TOUR in dummyData,
                selectedColor = MatetripColor.Primary,
                notSelectedColor = MatetripColor.Blue_04,
                trailingIcon = Badges.cafe_tour_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MatetripColor.Blue_01,
                onClick = {
                    if(TravelStyle.CAFE_TOUR in dummyData){
                        dummyData.remove(TravelStyle.CAFE_TOUR)
                    } else {
                        dummyData.add(TravelStyle.CAFE_TOUR)
                    }
                }
            )
            CustomClickableTag(
                tagName = "힐링",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 60.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MatetripColor.Blue_01,
                isSelected = TravelStyle.HEALING in dummyData,
                selectedColor = MatetripColor.Primary,
                notSelectedColor = MatetripColor.Blue_04,
                trailingIcon = Badges.healing_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MatetripColor.Blue_01,
                onClick = {
                    if(TravelStyle.HEALING in dummyData){
                        dummyData.remove(TravelStyle.HEALING)
                    } else {
                        dummyData.add(TravelStyle.HEALING)
                    }
                }
            )
            CustomClickableTag(
                tagName = "문화예술",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 60.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MatetripColor.Blue_01,
                isSelected = TravelStyle.CULTURE_AND_ARTS in dummyData,
                selectedColor = MatetripColor.Primary,
                notSelectedColor = MatetripColor.Blue_04,
                trailingIcon = Badges.art_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MatetripColor.Blue_01,
                onClick = {
                    if(TravelStyle.CULTURE_AND_ARTS in dummyData){
                        dummyData.remove(TravelStyle.CULTURE_AND_ARTS)
                    } else {
                        dummyData.add(TravelStyle.CULTURE_AND_ARTS)
                    }
                }
            )
            CustomClickableTag(
                tagName = "패키지 여행",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 60.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MatetripColor.Blue_01,
                isSelected = TravelStyle.PACKAGE_TOUR in dummyData,
                selectedColor = MatetripColor.Primary,
                notSelectedColor = MatetripColor.Blue_04,
                trailingIcon = Badges.package_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MatetripColor.Blue_01,
                onClick = {
                    if(TravelStyle.PACKAGE_TOUR in dummyData){
                        dummyData.remove(TravelStyle.PACKAGE_TOUR)
                    } else {
                        dummyData.add(TravelStyle.PACKAGE_TOUR)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FoodPreferenceEdit(){
    val dummyData = remember{
        mutableStateListOf(
            FoodPreference.MEAT,
            FoodPreference.RICE,
            FoodPreference.COFFEE,
            FoodPreference.DESSERT
        )
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "음식 취향",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(700),
        )
        Spacer(Modifier.height(12.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            CustomClickableTag(
                tagName = "육류",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 60.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MatetripColor.Blue_01,
                isSelected = FoodPreference.MEAT in dummyData,
                selectedColor = MatetripColor.Primary,
                notSelectedColor = MatetripColor.Blue_04,
                trailingIcon = Badges.meat_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MatetripColor.Blue_01,
                onClick = {
                    if(FoodPreference.MEAT in dummyData){
                        dummyData.remove(FoodPreference.MEAT)
                    } else {
                        dummyData.add(FoodPreference.MEAT)
                    }
                }
            )
            CustomClickableTag(
                tagName = "밥류",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 60.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MatetripColor.Blue_01,
                isSelected = FoodPreference.RICE in dummyData,
                selectedColor = MatetripColor.Primary,
                notSelectedColor = MatetripColor.Blue_04,
                trailingIcon = Badges.rice_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MatetripColor.Blue_01,
                onClick = {
                    if(FoodPreference.RICE in dummyData){
                        dummyData.remove(FoodPreference.RICE)
                    } else {
                        dummyData.add(FoodPreference.RICE)
                    }
                }
            )
            CustomClickableTag(
                tagName = "커피",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 60.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MatetripColor.Blue_01,
                isSelected = FoodPreference.COFFEE in dummyData,
                selectedColor = MatetripColor.Primary,
                notSelectedColor = MatetripColor.Blue_04,
                trailingIcon = Badges.coffee_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MatetripColor.Blue_01,
                onClick = {
                    if(FoodPreference.COFFEE in dummyData){
                        dummyData.remove(FoodPreference.COFFEE)
                    } else {
                        dummyData.add(FoodPreference.COFFEE)
                    }
                }
            )
            CustomClickableTag(
                tagName = "패스트 푸드",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 60.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MatetripColor.Blue_01,
                isSelected = FoodPreference.FAST_FOOD in dummyData,
                selectedColor = MatetripColor.Primary,
                notSelectedColor = MatetripColor.Blue_04,
                trailingIcon = Badges.fast_food_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MatetripColor.Blue_01,
                onClick = {
                    if(FoodPreference.FAST_FOOD in dummyData){
                        dummyData.remove(FoodPreference.FAST_FOOD)
                    } else {
                        dummyData.add(FoodPreference.FAST_FOOD)
                    }
                }
            )
            CustomClickableTag(
                tagName = "해산물",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 60.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MatetripColor.Blue_01,
                isSelected = FoodPreference.SEAFOOD in dummyData,
                selectedColor = MatetripColor.Primary,
                notSelectedColor = MatetripColor.Blue_04,
                trailingIcon = Badges.seafood_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MatetripColor.Blue_01,
                onClick = {
                    if(FoodPreference.SEAFOOD in dummyData){
                        dummyData.remove(FoodPreference.SEAFOOD)
                    } else {
                        dummyData.add(FoodPreference.SEAFOOD)
                    }
                }
            )
            CustomClickableTag(
                tagName = "채소",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 60.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MatetripColor.Blue_01,
                isSelected = FoodPreference.VEGETABLES in dummyData,
                selectedColor = MatetripColor.Primary,
                notSelectedColor = MatetripColor.Blue_04,
                trailingIcon = Badges.vegetarian_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MatetripColor.Blue_01,
                onClick = {
                    if(FoodPreference.VEGETABLES in dummyData){
                        dummyData.remove(FoodPreference.VEGETABLES)
                    } else {
                        dummyData.add(FoodPreference.VEGETABLES)
                    }
                }
            )
            CustomClickableTag(
                tagName = "디저트",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 60.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MatetripColor.Blue_01,
                isSelected = FoodPreference.DESSERT in dummyData,
                selectedColor = MatetripColor.Primary,
                notSelectedColor = MatetripColor.Blue_04,
                trailingIcon = Badges.dessert_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MatetripColor.Blue_01,
                onClick = {
                    if(FoodPreference.DESSERT in dummyData){
                        dummyData.remove(FoodPreference.DESSERT)
                    } else {
                        dummyData.add(FoodPreference.DESSERT)
                    }
                }
            )
            CustomClickableTag(
                tagName = "스트릿 푸드",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 60.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MatetripColor.Blue_01,
                isSelected = FoodPreference.STREET_FOOD in dummyData,
                selectedColor = MatetripColor.Primary,
                notSelectedColor = MatetripColor.Blue_04,
                trailingIcon = Badges.street_food_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MatetripColor.Blue_01,
                onClick = {
                    if(FoodPreference.STREET_FOOD in dummyData){
                        dummyData.remove(FoodPreference.STREET_FOOD)
                    } else {
                        dummyData.add(FoodPreference.STREET_FOOD)
                    }
                }
            )
        }
    }
}

@Composable
private fun SNSEdit(){
    var snsLink by remember{mutableStateOf("")}
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "SNS",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(700)
        )
        Spacer(Modifier.height(7.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .border(width = 1.dp, color = MatetripColor.Blue_03, shape = CircleShape),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Badges.instagram_outlined_badge),
                    contentDescription = "Instagram Badge",
                    tint = MatetripColor.Blue_01
                )
            }
            Spacer(Modifier.width(10.dp))
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(size = 10.dp))
                    .border(
                        width = 1.dp,
                        color = MatetripColor.Blue_03,
                        shape = RoundedCornerShape(size = 10.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 13.dp),
                value = snsLink,
                onValueChange = {snsLink = it},
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400),
                    color = Color.Black
                ),
                maxLines = 1
            ){
                if(snsLink.isEmpty()){
                    Text(
                        text = "www.instagram.com/계정입력",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontWeight = FontWeight(400),
                        color = MatetripColor.gray_06
                    )
                } else {
                    it()
                }
            }
        }
    }
}

@Composable
private fun MyImages(pictures: List<String>){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "나를 표현하는 이미지",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(700),
            color = MatetripColor.Gray_11
        )
        Spacer(Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            item{
                Column(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            color = MatetripColor.Blue_04,
                            shape = RoundedCornerShape(size = 10.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Icons.camera_icon),
                        contentDescription = "Camera Icon"
                    )
                    Text(
                        text = "${pictures.size}/4",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontWeight = FontWeight(400),
                        color = MatetripColor.icon_color
                    )
                }
            }
            items(pictures){picture ->
                ImageLoadView(
                    shape = RoundedCornerShape(size = 10.dp),
                    size = 60.dp,
                    imageUrl = picture,
                    backgroundColor = MatetripColor.icon_loading_color,
                    isEnabledClose = true,
                    onCloseClick = { /** 사진 삭제 */ }
                )
            }
        }
    }
}

@Preview
@Composable
private fun EditProfileUITest(){
    EditProfileScreen()
}