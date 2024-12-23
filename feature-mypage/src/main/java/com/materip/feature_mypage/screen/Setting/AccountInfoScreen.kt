package com.materip.feature_mypage.screen.Setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.materip.core_common.ErrorState
import com.materip.core_designsystem.component.ConfirmationDialog
import com.materip.core_designsystem.component.NormalTopBar
import com.materip.core_designsystem.icon.Badges
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.core_model.response.GetProfileResponseDto
import com.materip.core_model.ui_model.AccountInfoClass
import com.materip.feature_mypage.view_models.Setting.AccountInfoUiState
import com.materip.feature_mypage.view_models.Setting.AccountInfoViewModel
import com.materip.matetrip.component.DefaultLoadingComponent
import com.materip.matetrip.toast.ErrorView

@Composable
fun AccountInfoRoute(
    navSmsVerification: () -> Unit,
    navBack: () -> Unit,
    navLogout: () -> Unit,
    navDeleteAccount: () -> Unit,
    viewModel: AccountInfoViewModel = hiltViewModel()
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorState.collectAsStateWithLifecycle()
    val logout = remember{{viewModel.logout(onSuccess = navLogout)}}

    AccountInfoScreen(
        uiState = uiState.value,
        errState = errState.value,
        navDeleteAccount = navDeleteAccount,
        navSmsVerification = navSmsVerification,
        navBack = navBack,
        logout = logout
    )
}

@Composable
fun AccountInfoScreen(
    uiState: AccountInfoUiState,
    errState: ErrorState,
    navDeleteAccount: () -> Unit,
    navSmsVerification: () -> Unit,
    navBack: () -> Unit,
    logout: () -> Unit,
){
    when(uiState){
        AccountInfoUiState.Loading -> {
            DefaultLoadingComponent()
        }
        AccountInfoUiState.Error -> ErrorView(errState = errState, navBack = navBack)
        is AccountInfoUiState.Success -> {
            AccountInfoMainContent(
                logout = logout,
                navSmsVerification = navSmsVerification,
                navDeleteAccount = navDeleteAccount,
                navBack = navBack
            )
        }
    }
}

@Composable
private fun AccountInfoMainContent(
    navSmsVerification: () -> Unit,
    navDeleteAccount: () -> Unit,
    navBack: () -> Unit,
    logout: () -> Unit
){
    val dummyData = AccountInfoClass(
        kakaoAccount = "asdfasdf@kakao.com",
        isSecondAuthDone = false,
        phoneNumber = null,
        isSnsLinked = false,
        instagram = null
    )

    var showLogoutDialog by remember{mutableStateOf(false)}

    if (showLogoutDialog){
        ConfirmationDialog(
            dialogMsg = "정말 로그아웃 하시나요?",
            onOkClick = logout,
            onDismissRequest = {showLogoutDialog = false}
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
    ){
        NormalTopBar(
            title = "개인 정보",
            titleFontWeight = FontWeight(700),
            onBackClick = navBack,
            onClick = { /* 미사용 */}
        )
        Spacer(Modifier.height(30.dp))
        AccountView(
            kakaoAccount = dummyData.kakaoAccount
        )
        Spacer(Modifier.height(40.dp))
        SecondAuthView(
            isSecondAuthDone = dummyData.isSecondAuthDone,
            phoneNumber = dummyData.phoneNumber,
            navSmsVerification = navSmsVerification
        )
        Spacer(Modifier.height(40.dp))
        LinkSNSView(
            isSnsLinked = dummyData.isSnsLinked,
            instagram = dummyData.instagram
        )
        Spacer(Modifier.height(40.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .clickable { showLogoutDialog = true },
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "로그아웃",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(400),
                color = MateTripColors.Gray_11
            )
        }
        Spacer(Modifier.height(40.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .clickable { navDeleteAccount() },
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "탈퇴하기",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(400),
                color = MateTripColors.Gray_11
            )
        }
    }
}

@Composable
private fun AccountView(
    kakaoAccount: String
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "계정",
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(400),
            color = MateTripColors.Gray_11
        )
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column{
                Text(
                    text = "카카오 로그인",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400),
                    color = MateTripColors.Gray_11
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = kakaoAccount,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400),
                    color = MateTripColors.Gray_10
                )
            }
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape),
                painter = painterResource(Badges.kakaotalk_badge),
                contentDescription = "Kakao Badge"
            )
        }
    }
}

@Composable
private fun SecondAuthView(
    isSecondAuthDone: Boolean,
    phoneNumber: String?,
    navSmsVerification: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth(),
    ){
        Text(
            text = "2차 인증",
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(400),
            color = MateTripColors.Gray_11
        )
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            if(isSecondAuthDone){

            } else {
                Column{
                    Text(
                        text = "휴대폰 SMS 인증",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontWeight = FontWeight(400),
                        color = MateTripColors.Gray_11
                    )
                    Text(
                        text = "미등록",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontWeight = FontWeight(400),
                        color = MateTripColors.Gray_11,
                    )
                }
                Text(
                    modifier = Modifier.clickable{
                        navSmsVerification()
                    },
                    text = "등록",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400),
                    color = MateTripColors.Gray_06
                )
            }
        }
    }
}

@Composable
private fun LinkSNSView(
    isSnsLinked: Boolean,
    instagram: String?
){
    var currentInstagram by remember{mutableStateOf(instagram ?: "")}
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "SNS 연동",
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(400),
            color = MateTripColors.Gray_11
        )
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column{
                Text(
                    text = "인스타그램",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400),
                    color = MateTripColors.Gray_11
                )
                Text(
                    text = if(isSnsLinked) "등록" else "미등록",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400),
                    color = if(isSnsLinked) Color.Black else MateTripColors.Gray_06
                )
            }
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape),
                painter = painterResource(Badges.instagram_badge),
                contentDescription = "Instagram Badge"
            )
        }
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = currentInstagram,
            onValueChange = {currentInstagram = it},
            textStyle = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(400)
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MateTripColors.border_color,
                unfocusedIndicatorColor = MateTripColors.border_color,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            singleLine = true,
            shape = RoundedCornerShape(size = 10.dp),
            placeholder = {
                Text(
                    text = "https://www.instagram.com/",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400),
                    color = MateTripColors.Gray_06
                )
            },
            trailingIcon = {
                if(isSnsLinked){
                    Icon(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(Icons.check_icon),
                        contentDescription = "Check Icon"
                    )
                } else {
                    Text(
                        text = "확인",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontWeight = FontWeight(400),
                        color = if(currentInstagram.isNotEmpty()) Color.Black else MateTripColors.Gray_06
                    )
                }
            }
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "인스타그램 연동을 위해 링크 뒤에 프로필을 입력해주세요.",
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(400),
            color = MateTripColors.Gray_06
        )
    }
}

@Preview
@Composable
private fun AccountInfoUITest(){
    AccountInfoScreen(
        logout = {},
        navDeleteAccount = {},
        navSmsVerification = {},
        navBack = {},
        uiState = AccountInfoUiState.Loading,
        errState = ErrorState.Loading
    )
}