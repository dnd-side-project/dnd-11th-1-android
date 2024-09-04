package com.materip.feature_mypage.screen.MyPage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import com.materip.core_common.ErrorState
import com.materip.core_designsystem.component.NormalTag
import com.materip.core_designsystem.component.NormalTopBar
import com.materip.core_designsystem.icon.Badges
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.core_model.response.GetReviewDescriptionResponseDto
import com.materip.core_model.ui_model.ReviewDescClass
import com.materip.feature_mypage.view_models.MyPage.ReviewDescriptionUiState
import com.materip.feature_mypage.view_models.MyPage.ReviewDescriptionViewModel

@Composable
fun ReviewDescriptionRoute(
    id: Int?,
    navBack: () -> Unit,
    viewModel: ReviewDescriptionViewModel = hiltViewModel()
){
    viewModel.setReviewId(id)
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorState.collectAsStateWithLifecycle()

    ReviewDescriptionScreen(
        uiState = uiState.value,
        errState = errState.value,
        navBack = navBack
    )
}

@Composable
fun ReviewDescriptionScreen(
    uiState: ReviewDescriptionUiState,
    errState: ErrorState,
    navBack: () -> Unit
){
    when(uiState){
        ReviewDescriptionUiState.Loading -> {
            CircularProgressIndicator()
        }
        ReviewDescriptionUiState.Error -> {
            Text(
                text = "Error",
                fontSize = 100.sp,
                color = Color.Red
            )
        }
        is ReviewDescriptionUiState.Success -> {
            val data = uiState.result
            ReviewDescriptionMainContent(
                nickname = data.writerNickname,
                destination = data.getRegionText(),
                period = data.getDuration(),
                startDate = data.getStartDateText(),
                endDate = data.getEndDateText(),
                images = data.reviewImageUrls,
                mate = "메이트", /** 나의 닉네임 정보가 있어야 함 */
                reviewContent = data.detailContent,
                tags = data.getTags(),
                navBack = navBack
            )
        }
    }
}

@Composable
private fun ReviewDescriptionMainContent(
    nickname: String,
    destination: String,
    period: String,
    startDate: String,
    endDate: String,
    images: List<String>,
    mate: String,
    reviewContent: String,
    tags: List<String>,
    navBack: () -> Unit
){
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.BottomEnd
    ){
        Image(
            modifier = Modifier.size(140.dp),
            painter = painterResource(Badges.background_badge),
            contentDescription = "Background Badge"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
        ){
            NormalTopBar(
                title = "동행 후기",
                onBackClick = navBack,
                onClick = {/* 미사용 */}
            )
            Spacer(Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = scrollState)
            ){
                Text(
                    text = "${nickname} 님이 보내주신\n따끈 따끈한 동행 후기에요!",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(700),
                )
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Row{
                        Row(
                            modifier = Modifier
                                .height(27.dp)
                                .background(
                                    color = MateTripColors.Primary,
                                    shape = RoundedCornerShape(size = 5.dp)
                                )
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                        ){
                            Text(
                                text = destination,
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                                fontWeight = FontWeight(500),
                                color = Color.White,
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        Row(
                            modifier = Modifier
                                .height(27.dp)
                                .background(
                                    color = MateTripColors.Blue_04,
                                    shape = RoundedCornerShape(size = 5.dp)
                                )
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                        ){
                            Text(
                                text = period,
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                                fontWeight = FontWeight(500),
                                color = Color.Black,
                            )
                        }
                    }
                    Text(
                        text = "${startDate} - ${endDate}",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.roboto_medium)),
                        fontWeight = FontWeight(400),
                        color = MateTripColors.Gray_06
                    )
                }
                if(images.isNotEmpty()){
                    Spacer(Modifier.height(10.dp))
                    ImagePagerView(images)
                }
                Spacer(Modifier.height(17.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            color = MateTripColors.Blue_04,
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .padding(12.dp)
                ){
                    Text(
                        text = "From. ${mate}",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontWeight = FontWeight(500),
                        color = MateTripColors.Gray_11
                    )
                    Spacer(Modifier.height(20.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = reviewContent,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontWeight = FontWeight(400),
                        color = MateTripColors.Gray_10,
                        softWrap = true
                    )
                }
                Spacer(Modifier.height(20.dp))
                TagList(tags = tags)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ImagePagerView(
    images: List<String>
){
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { images.size })
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color = MateTripColors.Blue_04, shape = RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.BottomCenter
    ){
        HorizontalPager(state = pagerState) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = images[it],
                contentDescription = "Picture"
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp),
            horizontalArrangement = Arrangement.Center
        ){
            images.forEachIndexed{idx, image ->
                val isSelected = idx == pagerState.currentPage
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(
                            if (isSelected) MateTripColors.icon_color else MateTripColors.icon_loading_color,
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                )
                if(idx != images.size-1){
                    Spacer(Modifier.width(4.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TagList(tags: List<String>){
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        tags.forEach{ tag ->
            NormalTag(
                modifier = Modifier.height(38.dp),
                tagName = tag,
                shape = RoundedCornerShape(size = 6.dp),
                color = MateTripColors.Blue_04,
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400)
                )
            )
        }
    }
}

@Preview
@Composable
fun ReviewDescriptionUITest(){
    ReviewDescriptionScreen(
        uiState = ReviewDescriptionUiState.Success(
            result = GetReviewDescriptionResponseDto(
                writerNickname = "닉네임",
                region = "부산",
                startDate = "2024.07.20",
                endDate = "2024.07.22",
                companionType = "ALL_ACCOMPANYING",
                reviewImageUrls = listOf(),
                personalityType = listOf("KIND"),
                travelPreference = listOf("PLANNED"),
                travelStyle = listOf("LIKE_RESTAURANTS"),
                detailContent = "같이 여행해서 좋았다는 내용 같이 여행해서 좋았다는 내용 같이 여행해서 좋았다는 내용 "
            )
        ),
        errState = ErrorState.Loading,
        navBack = {}
    )
}