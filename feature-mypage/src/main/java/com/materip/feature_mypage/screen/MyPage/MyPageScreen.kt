package com.materip.feature_mypage.screen.MyPage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_designsystem.component.TempTopBar
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.core_model.ui_model.MyPageTab

@Composable
fun MyPageRoute(
    navEditProfile: () -> Unit,
    navProfileDescription: () -> Unit,
    navQuiz100: () -> Unit,
    navPreview: () -> Unit,
    navSendApplication: () -> Unit
){
    MyPageScreen(
        navEditProfile = navEditProfile,
        navProfileDescription = navProfileDescription,
        navQuiz100 = navQuiz100,
        navPreview = navPreview,
        navSendApplication = navSendApplication
    )
}

@Composable
fun MyPageScreen(
    navEditProfile: () -> Unit,
    navProfileDescription: () -> Unit,
    navQuiz100: () -> Unit,
    navPreview: () -> Unit,
    navSendApplication: () -> Unit,
){
    var selectedTab by remember{mutableStateOf(MyPageTab.Profile)}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
    ){
        TempTopBar(title = "마이페이지")
        Spacer(Modifier.height(24.dp))
        CustomPager(
            selectedTab = selectedTab,
            onTabChange = {selectedTab = it}
        )
        Spacer(Modifier.height(20.dp))
        CustomPagerContent(
            selectedTab = selectedTab,
            navEditProfile = navEditProfile,
            navProfileDescription = navProfileDescription,
            navQuiz100 = navQuiz100,
            navPreview = navPreview,
            navSendApplication = navSendApplication
        )
    }
}
@Composable
fun CustomPager(
    selectedTab: MyPageTab,
    onTabChange: (newTab: MyPageTab) -> Unit,
){
    val textStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
        fontWeight = FontWeight(700)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
    ){
        Column(
            modifier = Modifier
                .width(60.dp)
                .height(30.dp)
                .clickable {
                    if (selectedTab != MyPageTab.Profile) {
                        onTabChange(MyPageTab.Profile)
                    }
                },
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "프로필",
                style = textStyle,
                color = if(selectedTab == MyPageTab.Profile) MateTripColors.Primary else MateTripColors.Gray_06
            )
            if(selectedTab == MyPageTab.Profile) {
                HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp), thickness = 3.dp, color = MateTripColors.Primary)
            }
        }
        Spacer(Modifier.width(20.dp))
        Column(
            modifier = Modifier
                .width(60.dp)
                .height(30.dp)
                .clickable {
                    if (selectedTab != MyPageTab.TravelPost) {
                        onTabChange(MyPageTab.TravelPost)
                    }
                },
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "동행글",
                style = textStyle,
                color = if(selectedTab == MyPageTab.TravelPost) MateTripColors.Primary else MateTripColors.Gray_06
            )
            if(selectedTab == MyPageTab.TravelPost) {
                HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp), thickness = 3.dp, color = MateTripColors.Primary)
            }
        }
        Spacer(Modifier.width(20.dp))
        Column(
            modifier = Modifier
                .width(60.dp)
                .height(30.dp)
                .clickable {
                    if (selectedTab != MyPageTab.TravelHistory) {
                        onTabChange(MyPageTab.TravelHistory)
                    }
                },
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "동행내역",
                style = textStyle,
                color = if(selectedTab == MyPageTab.TravelHistory) MateTripColors.Primary else MateTripColors.Gray_06
            )
            if(selectedTab == MyPageTab.TravelHistory) {
                HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp), thickness = 3.dp, color = MateTripColors.Primary)
            }
        }
    }
}
@Composable
fun CustomPagerContent(
    selectedTab: MyPageTab,
    navEditProfile: () -> Unit,
    navProfileDescription: () -> Unit,
    navQuiz100: () -> Unit,
    navPreview: () -> Unit,
    navSendApplication: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        when(selectedTab){
            MyPageTab.Profile -> {
                ProfileMainContentRoute(
                    navEditProfile = navEditProfile,
                    navProfileDescription = navProfileDescription,
                    navQuiz100 = navQuiz100,
                    navPreview = navPreview,
                )
            }
            MyPageTab.TravelPost -> TravelPostContent()
            MyPageTab.TravelHistory -> TravelHistoryContent(
                navSendApplication = navSendApplication
            )
        }
    }
}
@Preview
@Composable
private fun MyPageUITest(){
    MyPageScreen(
        navPreview = {},
        navProfileDescription = {},
        navQuiz100 = {},
        navEditProfile = {},
        navSendApplication = {}
    )
}