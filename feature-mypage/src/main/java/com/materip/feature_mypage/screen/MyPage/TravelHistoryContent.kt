package com.materip.feature_mypage.screen.MyPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.materip.core_designsystem.R
import com.materip.core_designsystem.component.CircleImageView
import com.materip.core_designsystem.component.CustomButton
import com.materip.core_designsystem.component.CustomClickableTag
import com.materip.core_designsystem.component.NoDataContent
import com.materip.core_designsystem.component.ProfileTag
import com.materip.core_designsystem.component.TravelPostItem
import com.materip.core_designsystem.icon.Badges
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.core_model.accompany_board.BoardItem
import com.materip.core_model.ui_model.TempHumanClass
import com.materip.core_model.ui_model.TempTravelPost
import com.materip.core_model.ui_model.TravelHistoryTag
import com.materip.core_model.ui_model.TravelStyle
import com.materip.feature_mypage.view_models.MyPage.SendTravelApplicationUiState
import com.materip.feature_mypage.view_models.MyPage.SendTravelApplicationViewModel

@Composable
fun TravelHistoryContent(
    navSendApplication: (Int) -> Unit,
){
    var selectedTag by remember{mutableStateOf(TravelHistoryTag.RECORD)}
    val dummyData = listOf(
        TempTravelPost(
            postImage = null,
            title = "슈퍼 J를 찾습니다.",
            destination = "부산",
            period = "2박3일",
            startDate = "2024.07.20",
            endDate = "2024.07.22"
        ),
        TempTravelPost(
            postImage = null,
            title = "슈퍼 J를 찾습니다.",
            destination = "부산",
            period = "2박3일",
            startDate = "2024.07.20",
            endDate = "2024.07.22"
        )
    )
    TagList(
        selectedTag = selectedTag,
        onTagChange = {selectedTag = it}
    )
    Spacer(Modifier.height(20.dp))
    when(selectedTag){
        TravelHistoryTag.RECORD -> TravelHistories(
            records = dummyData
            /** dummy */ /** dummy */
        )
        TravelHistoryTag.SEND_APPLICATION -> {
            SendTravelApplication(
                navSendApplication = navSendApplication
            )
        }
        TravelHistoryTag.RECEIVE_APPLICATION -> {
            ReceiveTravelApplication(
                navReceivedApplication = {/** 받은 동행 신청서 navigation */}
            )
        }
    }
}

@Composable
private fun TagList(
    selectedTag: TravelHistoryTag,
    onTagChange: (TravelHistoryTag) -> Unit,
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        CustomClickableTag(
            tagName = "동행 기록",
            fontSize = 14.sp,
            shape = RoundedCornerShape(size = 60.dp),
            selectedTextColor = Color.White,
            notSelectedTextColor = MateTripColors.Gray_06,
            isSelected = selectedTag == TravelHistoryTag.RECORD,
            selectedColor = MateTripColors.ActivatedColor,
            notSelectedColor = MateTripColors.InactiveColor,
            onClick = {onTagChange(TravelHistoryTag.RECORD)}
        )
        Spacer(Modifier.width(8.dp))
        CustomClickableTag(
            tagName = "보낸 동행 신청서",
            fontSize = 14.sp,
            shape = RoundedCornerShape(size = 60.dp),
            selectedTextColor = Color.White,
            notSelectedTextColor = MateTripColors.Gray_06,
            isSelected = selectedTag == TravelHistoryTag.SEND_APPLICATION,
            selectedColor = MateTripColors.ActivatedColor,
            notSelectedColor = MateTripColors.InactiveColor,
            onClick = {onTagChange(TravelHistoryTag.SEND_APPLICATION)}
        )
        Spacer(Modifier.width(12.dp))
        CustomClickableTag(
            tagName = "받은 동행 신청서",
            fontSize = 14.sp,
            shape = RoundedCornerShape(size = 60.dp),
            selectedTextColor = Color.White,
            notSelectedTextColor = MateTripColors.Gray_06,
            isSelected = selectedTag == TravelHistoryTag.RECEIVE_APPLICATION,
            selectedColor = MateTripColors.ActivatedColor,
            notSelectedColor = MateTripColors.InactiveColor,
            onClick = {onTagChange(TravelHistoryTag.RECEIVE_APPLICATION)}
        )
    }
}

@Composable
private fun TravelHistories(records: List<TempTravelPost>){
    if(records.isEmpty()){
        NoDataContent("아직 동행 기록이 없어요.\n즐거운 동행 경험을 만들어 보세요.")
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            items(records){record ->
                TravelPostItem(
                    destination = record.destination,
                    period = record.period,
                    title = record.title,
                    startDate = record.startDate,
                    endDate = record.endDate,
                    postImage = record.postImage,
                    onClick = {/** 해당 글로 navigation  */}
                )
                Spacer(Modifier.height(8.dp))
                CustomButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp),
                    shape = RoundedCornerShape(size = 8.dp),
                    btnText = "동행 후기 작성",
                    fontSize = 14.sp,
                    btnColor = MateTripColors.Blue_04,
                    textColor = MateTripColors.Gray_08,
                    trailingIcon = Icons.review_icon,
                    onClick = { /** 동행 후기 작성하는 곳으로 navigation */ }
                )
            }
        }
    }
}

@Composable
private fun SendTravelApplication(
    navSendApplication: (Int) -> Unit,
    viewModel: SendTravelApplicationViewModel = hiltViewModel()
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val errState = viewModel.errorState.collectAsStateWithLifecycle().value
    when(uiState){
        SendTravelApplicationUiState.Loading -> {
            CircularProgressIndicator()
        }
        SendTravelApplicationUiState.Error -> {
            Text(
                text = "Error",
                fontSize = 100.sp,
                color = Color.Red
            )
        }
        is SendTravelApplicationUiState.Success -> {
            val applications = viewModel.applicationPagingSource().collectAsLazyPagingItems()
            SendTravelApplicationContent(
                applications = applications,
                navSendApplication = navSendApplication
            )
        }
    }
}

@Composable
private fun SendTravelApplicationContent(
    applications: LazyPagingItems<BoardItem>,
    navSendApplication: (Int) -> Unit
){
    val applications = applications.itemSnapshotList
    if(applications.isEmpty()){
        NoDataContent(message = "나와 맞는 동행자에게\n동행 신청서를 보내 보세요.")
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            items(applications){application ->
                if(application != null){
                    TravelPostItem(
                        destination = application.region,
                        period = application.getDuration(),
                        title = application.title,
                        startDate = application.getStartDateText(),
                        endDate = application.getEndDateText(),
                        postImage = application.imageUrls[0],
                        onClick = {/** 해당 글 navigation */}
                    )
                    Spacer(Modifier.height(8.dp))
                    CustomButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(38.dp),
                        shape = RoundedCornerShape(size = 8.dp),
                        btnText = "보낸 신청서 보기",
                        fontSize = 14.sp,
                        btnColor = MateTripColors.Blue_04,
                        textColor = MateTripColors.Gray_08,
                        onClick = { navSendApplication(application.boardId) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ReceiveTravelApplication(
    navReceivedApplication: () -> Unit
){
    val dummyData = listOf(
        TempHumanClass(
            nickname = "닉네임",
            level = 1,
            profileUrl = "",
            tags = listOf(
                TravelStyle.RESTAURANT_TOUR.name,
                TravelStyle.LIFE_SHOT.name,
                TravelStyle.ACTIVITY.name,
                TravelStyle.HEALING.name,
                TravelStyle.DRIVE.name
            ),
            age = "20대 초반 여자"
        )
    )
    if (dummyData.isEmpty()){
        NoDataContent(message = "아직 동행 신청서가 없어요.")
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(dummyData){application ->
                val levelIcon = when(application.level){
                    1 -> Badges.level_1_badge
                    2 -> Badges.level_2_badge
                    3 -> Badges.level_3_badge
                    else -> Badges.level_4_badge
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .border(
                            width = 1.dp,
                            color = MateTripColors.Blue_03,
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .padding(16.dp)
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        CircleImageView(
                            size = 40.dp,
                            imageUrl = ""
                            /** image profile 받아야 함 */
                        )
                        Spacer(Modifier.width(13.dp))
                        Column (
                            modifier = Modifier.weight(1f)
                        ) {
                            Row {
                                Text(
                                    text = application.nickname,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                                    fontWeight = FontWeight(700),
                                )
                                Image(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(levelIcon),
                                    contentDescription = "Level Badge"
                                )
                            }
                            Text(
                                text = "20대 초반·여자",
                                fontSize = 12.sp,
                                color = MateTripColors.Gray_06,
                                fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                                fontWeight = FontWeight(500)
                            )
                        }
                        Row(
                            modifier = Modifier.width(50.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            IconButton(
                                modifier = Modifier.size(20.dp),
                                onClick = { /** kakao 연결 */ },
                            ) {
                                Image(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painterResource(Badges.kakaotalk_badge),
                                    contentDescription = "Kakao Badge"
                                )
                            }
                            Spacer(Modifier.width(4.dp))
                            IconButton(
                                modifier = Modifier.size(20.dp),
                                onClick = { /** message 연결 */ },
                            ) {
                                Image(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painterResource(Badges.sms_badge),
                                    contentDescription = "Message Badge"
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(14.dp))
                    FlowRow(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ){
                        application.tags.forEach{
                            ProfileTag(it)
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                CustomButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp),
                    shape = RoundedCornerShape(size = 8.dp),
                    btnText = "받은 신청서 보기",
                    textColor = MateTripColors.Gray_08,
                    fontSize = 14.sp,
                    btnColor = MateTripColors.Blue_04,
                    onClick = navReceivedApplication
                )
            }
        }
    }
}