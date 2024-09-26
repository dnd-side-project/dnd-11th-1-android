package com.materip.feature_mypage.screen.MyPage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.materip.core_common.ErrorState
import com.materip.core_common.toDisplayAgeString
import com.materip.core_common.toDisplayString
import com.materip.core_designsystem.component.CircleImageView
import com.materip.core_designsystem.component.ConfirmationDialog
import com.materip.core_designsystem.component.CustomButton
import com.materip.core_designsystem.component.NormalTopBar
import com.materip.core_designsystem.component.ProfileTag
import com.materip.core_designsystem.component.TravelPostItem
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.core_model.accompany_board.all.BoardItem
import com.materip.core_model.request.AccompanyApplicationResponseDto
import com.materip.core_model.request.AccompanyResponseInfo
import com.materip.core_model.response.GetProfileDetailsResponseDto
import com.materip.core_model.ui_model.Gender
import com.materip.core_model.ui_model.Region
import com.materip.core_model.ui_model.TravelStyle
import com.materip.feature_mypage.view_models.MyPage.ReceivedApplicationUiState
import com.materip.feature_mypage.view_models.MyPage.ReceivedApplicationViewModel
import com.materip.matetrip.toast.CommonToastView
import com.materip.matetrip.toast.ErrorView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ReceivedApplicationRoute(
    id: Int?,
    navBack: () -> Unit,
    navProfileDetail: () -> Unit,
    viewModel: ReceivedApplicationViewModel = hiltViewModel()
){
    viewModel.setId(id)
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorState.collectAsStateWithLifecycle()
    val isDone = viewModel.isDone.collectAsStateWithLifecycle()
    var showToast by remember{mutableStateOf(false)}

    ReceivedApplicationScreen(
        isDone = isDone.value,
        uiState = uiState.value,
        errState = errState.value,
        onAcceptClick = { viewModel.handleApplication("accept") },
        onRejectClick = { viewModel.handleApplication("reject") },
        navBack = navBack,
        navProfileDetail = navProfileDetail
    )
    LaunchedEffect(isDone.value) {
        if(isDone.value) showToast = true
    }
    if(showToast){
        CommonToastView(message = "완료됐습니다.")
    }
}

@Composable
fun ReceivedApplicationScreen(
    isDone: Boolean,
    uiState: ReceivedApplicationUiState,
    errState: ErrorState,
    onAcceptClick: () -> Unit,
    onRejectClick: () -> Unit,
    navBack: () -> Unit,
    navProfileDetail: () -> Unit
){
    when(uiState){
        ReceivedApplicationUiState.Loading -> {
            CircularProgressIndicator()
        }
        ReceivedApplicationUiState.Error -> {
            ErrorView(
                errState = errState,
                navBack = navBack
            )
        }
        is ReceivedApplicationUiState.Success -> {
            SendApplicationContent(
                isDone = isDone,
                data = uiState.data,
                onAcceptClick = onAcceptClick,
                onRejectClick = onRejectClick,
                navProfileDetail = navProfileDetail,
                navBack = navBack,
            )
        }
    }
}

@Composable
private fun SendApplicationContent(
    isDone: Boolean,
    data: AccompanyApplicationResponseDto,
    onAcceptClick: () -> Unit,
    onRejectClick: () -> Unit,
    navProfileDetail: () -> Unit,
    navBack: () -> Unit
){
    val scrollState = rememberScrollState()
    val userInfo = data.profileInfo
    val boardInfo = data.boardThumbnail
    val requestInfo = data.requestInfo
    val alterText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Black)){
            append("동행 수락 후 카카오톡 오픈채팅")
        }
        withStyle(style = SpanStyle(color = MateTripColors.Gray_06)){
            append("에서 대화해 보세요!\n상대방의 동의 없는 링크 공유는")
        }
        withStyle(style = SpanStyle(color = Color.Black)){
            append("서비스 이용 제재의 사유")
        }
        withStyle(style = SpanStyle(color = MateTripColors.Gray_06)){
            append("가 됩니다.")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
            .padding(bottom = 50.dp)
    ){
        NormalTopBar(
            title = "받은 신청서",
            titleFontWeight = FontWeight(700),
            onBackClick = navBack,
            onClick = {/* 미사용 */}
        )
        Spacer(Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ){
            TravelPostItem(
                destination = boardInfo.region.toDisplayString(),
                period = boardInfo.getDuration(),
                title = boardInfo.title,
                startDate = boardInfo.startDate,
                endDate = boardInfo.endDate,
                postImage = boardInfo.imageUrls[0],
                onClick = {/* 미사용 */}
            )
            Spacer(Modifier.height(40.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "동행을 수락하기 전,\n동행자의 정보를 확인해 보세요!",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(700),
                lineHeight = 30.sp,
            )
            Spacer(Modifier.height(20.dp))
            ProfileView(
                userInfo = userInfo,
                navProfileDetail = navProfileDetail
            )
            Spacer(Modifier.height(14.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(
                        color = MateTripColors.Blue_03,
                        shape = RoundedCornerShape(size = 10.dp)
                    )
                    .padding(12.dp),
            ){
                Text(
                    text = "From. ${boardInfo.nickname}",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500),
                    color = MateTripColors.Gray_11
                )
                Spacer(Modifier.height(18.dp))
                Text(
                    text = requestInfo.introduce,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400),
                    color = MateTripColors.Gray_10
                )
            }
            Spacer(Modifier.height(14.dp))
            Text(
                text = alterText,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(400)
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(size = 10.dp))
                    .border(
                        width = 1.dp,
                        color = MateTripColors.Blue_03,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(vertical = 12.dp, horizontal = 10.dp)
            ){
                Text(
                    modifier = Modifier.clickable{
                        /** sns link 클릭 시 이동? */
                    },
                    text = requestInfo.chatLink,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400)
                )
            }
            Spacer(Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ){
                CustomButton(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(size = 8.dp),
                    btnText = "거절",
                    textColor = MateTripColors.Gray_06,
                    fontSize = 14.sp,
                    btnColor = MateTripColors.InactiveColor,
                    isEnabled = !isDone,
                    onClick = onRejectClick
                )
                Spacer(Modifier.width(10.dp))
                CustomButton(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(size = 8.dp),
                    btnText = "수락",
                    textColor = Color.White,
                    fontSize = 14.sp,
                    btnColor = Color.Black,
                    isEnabled = !isDone,
                    onClick = onAcceptClick
                )
            }
        }
    }
}

@Composable
private fun ProfileView(
    userInfo: GetProfileDetailsResponseDto,
    navProfileDetail: () -> Unit
){
    val tags = userInfo.getTags().subList(0,3)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .border(width = 1.dp, color = MateTripColors.Blue_03, shape = RoundedCornerShape(10.dp))
            .padding(12.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            CircleImageView(
                size = 42.dp,
                imageUrl = userInfo.profileImageUrl ?: ""
            )
            Spacer(Modifier.width(10.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = userInfo.nickname,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500),
                    color = MateTripColors.Gray_11
                )
                Text(
                    text = "${userInfo.birthYear.toDisplayAgeString()} · ${userInfo.gender.toDisplayString()}",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400),
                    color = MateTripColors.Gray_06
                )
            }
            Spacer(Modifier.weight(1f))
            Row{
                Text(
                    text = "프로필 보기",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400),
                    color = MateTripColors.Gray_11
                )
                IconButton(
                    modifier = Modifier.size(12.dp),
                    onClick = navProfileDetail
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(Icons.navigate_next_icon),
                        contentDescription = "Next Icon"
                    )
                }
            }
        }
        Spacer(Modifier.height(14.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            tags.forEach {
                ProfileTag(tagName = it)
                if(tags.lastIndex != tags.size - 1){
                    Spacer(Modifier.width(7.dp))
                }
            }
        }
    }
}

@Composable
@Preview
private fun ReceivedApplicationUITest(){
    ReceivedApplicationScreen(
        uiState = ReceivedApplicationUiState.Success(
            data = AccompanyApplicationResponseDto(
                boardThumbnail = BoardItem(
                    boardId = 0,
                    title = "슈퍼 J를 찾습니다!!!",
                    region = Region.BUSAN,
                    startDate = "2024.07.20:1234",
                    endDate = "2024.07.22:1234",
                    nickname = "메이트",
                    imageUrls = listOf()
                ),
                profileInfo = GetProfileDetailsResponseDto(
                    userId = 0,
                    nickname = "닉네임",
                    provider = "KAKAO",
                    profileImageUrl = "",
                    description = "",
                    gender = Gender.FEMALE,
                    birthYear = 2000,
                    socialMediaUrl = "http://www.kakao",
                    grade = "ELITE",
                    travelStyles = listOf(TravelStyle.RESTAURANT_TOUR.name, TravelStyle.LIFE_SHOT.name, TravelStyle.ACTIVITY.name),
                    foodPreferences = listOf(),
                    travelPreferences = listOf(),
                    userImageUrls = listOf()
                ),
                requestInfo = AccompanyResponseInfo(
                    boardId = 0,
                    userId = 0,
                    introduce = "안녕하세요! 저는 *** 이에요 저는 ~~~하는 사람이고 이런 여행 스타일을 가지고 있어요.\n\n음식취향은 해산물을 좋아해서 같이 여행 가게 된다면 해산물 먹으러 가고 싶어요!",
                    chatLink = "http://www.kakaotalkhttp://www.kakao",
                    received = false
                )
            )
        ),
        errState = ErrorState.Loading,
        navBack = {},
        navProfileDetail = {},
        onAcceptClick = {},
        onRejectClick = {},
        isDone = false
    )
}