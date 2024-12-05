package com.materip.feature_mypage.screen.Setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.materip.core_designsystem.component.MateTripButton
import com.materip.core_designsystem.component.NormalTopBar
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.feature_mypage.view_models.Setting.DeleteAccountViewModel

@Composable
fun AccountDeletionNoticeRoute(
    reason: String?,
    navDeleteAccountDone: () -> Unit,
    navSetting: () -> Unit,
    navBack: () -> Unit,
    viewModel: DeleteAccountViewModel = hiltViewModel()
){
    val deleteAccount = remember{{viewModel.deleteAccount(reason, navDeleteAccountDone)}}
    AccountDeletionNoticeScreen(
        onDeleteAccountClick = deleteAccount,
        navSetting = navSetting,
        navBack = navBack
    )
}

@Composable
fun AccountDeletionNoticeScreen(
    onDeleteAccountClick: () -> Unit,
    navSetting: () -> Unit,
    navBack: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
            .padding(bottom = 40.dp)
    ){
        NormalTopBar(
            title = "탈퇴하기",
            onBackClick = navBack,
            onClick = {},
            titleFontWeight = FontWeight(700)
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text = "탈퇴하기 전에 꼭 확인하세요!",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(700),
            color = MateTripColors.Gray_11
        )
        Spacer(Modifier.height(30.dp))
        Text(
            text = " · 탈퇴한 휴대폰 번호로는 일주일 간 재인증이 불가능합니다.",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(400),
            color = MateTripColors.Gray_07
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = " · 개인정보, 서비스 이용 기록 등은 복원이 불가능하며, 일정 기간 후 영구 삭제됩니다.",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(400),
            color = MateTripColors.Gray_07
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = " · 달성한 레벨, 동행 기록 등이 영구 소멸됩니다.",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(400),
            color = MateTripColors.Gray_07
        )
        Spacer(Modifier.weight(1f))
        MateTripButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            onClick = navSetting,
            enabled = true,
            buttonText = "돌아가기",
        )
        Spacer(Modifier.height(13.dp))
        MateTripButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            onClick = onDeleteAccountClick,
            enabled = true,
            buttonText = "탈퇴하기",
        )
    }
}

@Composable
@Preview
private fun AccountDeletionNoticeUITest(){
    AccountDeletionNoticeScreen(
        onDeleteAccountClick = {},
        navSetting = {},
        navBack = {}
    )
}