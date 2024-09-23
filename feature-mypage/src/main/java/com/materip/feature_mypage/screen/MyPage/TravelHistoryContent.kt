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
import androidx.paging.ItemSnapshotList
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.materip.core_common.toDisplayDateString
import com.materip.core_common.toDisplayString
import com.materip.core_designsystem.MatetripGrade
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
import com.materip.core_model.response.AccompanyReceivedItem
import com.materip.core_model.response.BoardItemWithRequestId
import com.materip.core_model.response.BoardItemWithReviewId
import com.materip.core_model.ui_model.GradeTag
import com.materip.core_model.ui_model.TravelHistoryTag
import com.materip.feature_mypage.view_models.MyPage.ReceiveTravelApplicationUiState
import com.materip.feature_mypage.view_models.MyPage.ReceiveTravelApplicationViewModel
import com.materip.feature_mypage.view_models.MyPage.SendTravelApplicationUiState
import com.materip.feature_mypage.view_models.MyPage.SendTravelApplicationViewModel
import com.materip.feature_mypage.view_models.MyPage.TravelRecordViewModel
import com.materip.feature_mypage.view_models.MyPage.TravelRecordsUiState
import com.materip.matetrip.toast.ErrorView

@Composable
fun TravelHistoryContent(
    navReviewWrite: (Int) -> Unit,
    navSendApplication: (Int) -> Unit,
    navReceivedApplication: (Int) -> Unit,
    navBack: () -> Unit
){
    var selectedTag by remember{mutableStateOf(TravelHistoryTag.RECORD)}

    TagList(
        selectedTag = selectedTag,
        onTagChange = {selectedTag = it}
    )
    Spacer(Modifier.height(20.dp))
    when(selectedTag){
        TravelHistoryTag.RECORD -> TravelRecords(
            navReviewWrite = navReviewWrite,
            navBack = navBack
        )
        TravelHistoryTag.SEND_APPLICATION -> {
            SendTravelApplication(
                navSendApplication = navSendApplication,
                navBack = navBack
            )
        }
        TravelHistoryTag.RECEIVE_APPLICATION -> {
            ReceiveTravelApplication(
                navReceivedApplication = navReceivedApplication,
                navBack = navBack
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
            fontSize = 13.sp,
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
            fontSize = 13.sp,
            shape = RoundedCornerShape(size = 60.dp),
            selectedTextColor = Color.White,
            notSelectedTextColor = MateTripColors.Gray_06,
            isSelected = selectedTag == TravelHistoryTag.SEND_APPLICATION,
            selectedColor = MateTripColors.ActivatedColor,
            notSelectedColor = MateTripColors.InactiveColor,
            onClick = {onTagChange(TravelHistoryTag.SEND_APPLICATION)}
        )
        Spacer(Modifier.width(8.dp))
        CustomClickableTag(
            tagName = "받은 동행 신청서",
            fontSize = 13.sp,
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
private fun TravelRecords(
    navReviewWrite: (Int) -> Unit,
    navBack: () -> Unit,
    viewModel: TravelRecordViewModel = hiltViewModel()
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorState.collectAsStateWithLifecycle()
    when(uiState.value){
        TravelRecordsUiState.Loading -> {
            CircularProgressIndicator()
        }
        TravelRecordsUiState.Error -> {
            ErrorView(
                errState = errState.value,
                navBack = navBack
            )
        }
        TravelRecordsUiState.Success -> {
            val records = viewModel.recordPagingSource().collectAsLazyPagingItems()
            TravelRecordsContent(
                records = records.itemSnapshotList,
                navReviewWrite = navReviewWrite
            )
        }
    }
}

@Composable
private fun TravelRecordsContent(
    records: ItemSnapshotList<BoardItemWithReviewId>,
    navReviewWrite: (Int) -> Unit
){
    if(records.isEmpty()){
        NoDataContent("아직 동행 기록이 없어요.\n즐거운 동행 경험을 만들어 보세요.")
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            items(records){record ->
                if (record != null){
                    val isEnabled = record.reviewId == null
                    TravelPostItem(
                        destination = record.region.toDisplayString(),
                        period = record.getDuration(),
                        title = record.title,
                        startDate = record.startDate.toDisplayDateString(),
                        endDate = record.endDate.toDisplayDateString(),
                        postImage = record.imageUrls[0],
                        onClick = {/** 해당 글로 navigation  */}
                    )
                    Spacer(Modifier.height(8.dp))
                    CustomButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(38.dp),
                        shape = RoundedCornerShape(size = 8.dp),
                        btnText = if(isEnabled) "동행 후기 작성" else "동행 후기 작성 완료",
                        isEnabled = isEnabled,
                        fontSize = 14.sp,
                        btnColor = if(isEnabled) MateTripColors.Blue_04 else MateTripColors.InactiveColor,
                        textColor = MateTripColors.Gray_08,
                        trailingIcon = if(isEnabled) Icons.review_icon else null,
                        onClick = { navReviewWrite(record.boardId) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SendTravelApplication(
    navSendApplication: (Int) -> Unit,
    navBack: () -> Unit,
    viewModel: SendTravelApplicationViewModel = hiltViewModel()
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val errState = viewModel.errorState.collectAsStateWithLifecycle().value
    when(uiState){
        SendTravelApplicationUiState.Loading -> {
            CircularProgressIndicator()
        }
        SendTravelApplicationUiState.Error -> {
            ErrorView(
                errState = errState,
                navBack = navBack
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
    applications: LazyPagingItems<BoardItemWithRequestId>,
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
                        destination = application.region.toDisplayString(),
                        period = application.getDuration(),
                        title = application.title,
                        startDate = application.startDate.toDisplayDateString(),
                        endDate = application.endDate.toDisplayDateString(),
                        postImage = application.imageUrls[0],
                        /** 여기에는 application에서 받아온 board id 를 기반으로 navigation 해야 함 */
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
                        onClick = { navSendApplication(application.requestId) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ReceiveTravelApplication(
    navBack: () -> Unit,
    navReceivedApplication: (Int) -> Unit,
    viewModel: ReceiveTravelApplicationViewModel = hiltViewModel()
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorState.collectAsStateWithLifecycle()
    when(uiState.value){
        ReceiveTravelApplicationUiState.Loading -> {
            CircularProgressIndicator()
        }
        ReceiveTravelApplicationUiState.Error -> {
            ErrorView(
                errState = errState.value,
                navBack = navBack
            )
        }
        ReceiveTravelApplicationUiState.Success -> {
            val applications = viewModel.applicationPagingSource().collectAsLazyPagingItems()
            ReceiveTravelApplicationContent(
                applications = applications.itemSnapshotList,
                navReceivedApplication = navReceivedApplication
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ReceiveTravelApplicationContent(
    applications: ItemSnapshotList<AccompanyReceivedItem>,
    navReceivedApplication: (Int) -> Unit
){
    if (applications.isEmpty()){
        NoDataContent(message = "아직 동행 신청서가 없어요.")
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(applications){application ->
                if (application != null){
                    val levelIcon = when(application.grade){
                        GradeTag.ROOKIE.name -> MatetripGrade.level_1
                        GradeTag.ELITE.name -> MatetripGrade.level_2
                        GradeTag.PASSIONATE.name -> MatetripGrade.level_3
                        else -> MatetripGrade.level_4
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
                                imageUrl = application.profileImageUrl ?: ""
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
                                        painter = painterResource(levelIcon.badge),
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
                            application.getTags().forEach{
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
                        onClick = { navReceivedApplication(application.requestId) }
                    )
                }
            }
        }
    }
}