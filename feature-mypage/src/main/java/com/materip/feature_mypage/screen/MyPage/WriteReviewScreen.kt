package com.materip.feature_mypage.screen.MyPage

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.ItemSnapshotList
import com.materip.core_common.ErrorState
import com.materip.core_designsystem.component.CustomButton
import com.materip.core_designsystem.component.CustomClickableTag
import com.materip.core_designsystem.component.ImageLoadView
import com.materip.core_designsystem.component.NormalTag
import com.materip.core_designsystem.component.NormalTopBar
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.core_model.ui_model.CompanionType
import com.materip.core_model.ui_model.PersonalityType
import com.materip.core_model.ui_model.RecommendationStatus
import com.materip.core_model.ui_model.SatisfactionLevel
import com.materip.core_model.ui_model.TravelPreferenceForReview
import com.materip.core_model.ui_model.TravelStyleForReview
import com.materip.feature_mypage.view_models.MyPage.WriteReviewViewModel
import com.materip.matetrip.toast.ErrorView
import kotlinx.coroutines.launch

@Composable
fun WriteReviewRoute(
    id: Int?,
    navBack: () -> Unit,
    viewModel: WriteReviewViewModel = hiltViewModel()
){
    viewModel.setId(id)

    val context = LocalContext.current
    val errState = viewModel.errorState.collectAsStateWithLifecycle()
    val isDone by viewModel.isDone.collectAsStateWithLifecycle()

    WriteReviewScreen(
        errState = errState.value,
        onClickDone = { satisfactionLevel, recommendationStatus, companionType, personalityType, travelPreferences, travelStyle, detailContent, reviewImageUrls ->
            viewModel.writeReview(
                satisfactionLevel = satisfactionLevel,
                recommendationStatus = recommendationStatus,
                companionType = companionType,
                personalityType = personalityType,
                travelPreference = travelPreferences,
                travelStyle = travelStyle,
                detailContent = detailContent,
                imageUrl = reviewImageUrls
            )
        },
        onUploadImage = {viewModel.uploadImageToS3(context, it)},
        navBack = navBack
    )
    LaunchedEffect(isDone){
        if (isDone) {navBack()}
    }
}

@Composable
fun WriteReviewScreen(
    errState: ErrorState,
    onClickDone: (SatisfactionLevel, RecommendationStatus, CompanionType,
                  List<PersonalityType>, List<TravelPreferenceForReview>, List<TravelStyleForReview>, String, List<String>) -> Unit,
    onUploadImage: suspend (uri: Uri) -> String,
    navBack: () -> Unit,
){
    if(errState is ErrorState.AuthError && errState.isInvalid()){
        ErrorView(
            errState = errState,
            navBack = navBack
        )
    } else {
        val scrollState = rememberScrollState()
        val context = LocalContext.current
        var selectedSatisfactionLevel by remember{mutableStateOf(SatisfactionLevel.GOOD)}
        var selectedRecommendationStatus by remember{mutableStateOf(RecommendationStatus.SUGGESTION)}
        var selectedCompanionType by remember{mutableStateOf(CompanionType.ALL_ACCOMPANYING)}
        val selectedPersonalityType = remember{mutableStateListOf<PersonalityType>()}
        val selectedTravelPreference = remember{mutableStateListOf<TravelPreferenceForReview>()}
        val selectedTravelStyle = remember{mutableStateListOf<TravelStyleForReview>()}
        var detailContent by remember{mutableStateOf("")}
        val imageUrls = remember{mutableStateListOf<String>()}
        var isEnabled = remember{
            derivedStateOf{
                selectedPersonalityType.isNotEmpty() && selectedTravelPreference.isNotEmpty()
                        && selectedTravelStyle.isNotEmpty()
            }
        }
        val coroutineScope = rememberCoroutineScope()
        val cameraLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(),
            onResult = {
                if(imageUrls.size + it.size <= 5){
                    it.forEach{
                        coroutineScope.launch{
                            val path = onUploadImage(it)
                            imageUrls.add(path)
                        }
                    }
                } else {
                    Toast.makeText(context, "사진은 5장까지 가능합니다.", Toast.LENGTH_SHORT).show()
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(horizontal = 20.dp)
                .padding(bottom = 35.dp)
        ){
            NormalTopBar(
                title = "동행 후기 작성",
                titleFontWeight = FontWeight(700),
                onBackClick = navBack,
                onClick = { /* 미사용 */ }
            )
            Spacer(Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = scrollState)
            ){
                TravelSatisfiedSurvey(
                    selectedSatisfactionLevel = selectedSatisfactionLevel,
                    onUpdateSatisfactionLevel = {selectedSatisfactionLevel = it}
                )
                Spacer(Modifier.height(60.dp))
                RecommendNextSurvey(
                    selectedRecommendationStatus = selectedRecommendationStatus,
                    onUpdateRecommendationStatus = {selectedRecommendationStatus = it}
                )
                Spacer(Modifier.height(60.dp))
                SelectTravelType(
                    selectedCompanionType = selectedCompanionType,
                    onUpdateCompanionType = {selectedCompanionType = it}
                )
                Spacer(Modifier.height(60.dp))
                SelectReviewTag(
                    selectedPersonalityType = selectedPersonalityType,
                    selectedTravelPreference = selectedTravelPreference,
                    selectedTravelStyle = selectedTravelStyle
                )
                Spacer(Modifier.height(60.dp))
                WriteReviewDetails(
                    detailContent = detailContent,
                    onUpdateDetailContent = {detailContent = it}
                )
                Spacer(Modifier.height(60.dp))
                SharePictures(
                    pictures = imageUrls,
                    onCameraClick = {cameraLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))},
                    onDeleteImage = {imageUrls.remove(it)}
                )
                Spacer(Modifier.height(60.dp))
                CustomButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(size = 8.dp),
                    btnText = "작성완료",
                    fontSize = 14.sp,
                    isEnabled = isEnabled.value,
                    btnColor = if(isEnabled.value) MateTripColors.ActivatedColor else MateTripColors.InactiveColor,
                    textColor = MateTripColors.Gray_06,
                    onClick = {
                        onClickDone(selectedSatisfactionLevel, selectedRecommendationStatus, selectedCompanionType, selectedPersonalityType, selectedTravelPreference, selectedTravelStyle, detailContent, imageUrls)
                    }
                )
            }
        }
    }
}

@Composable
private fun TravelSatisfiedSurvey(
    selectedSatisfactionLevel: SatisfactionLevel,
    onUpdateSatisfactionLevel: (SatisfactionLevel) -> Unit,
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "여행은 만족 하셨나요?",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(700),
                    color = MateTripColors.Gray_11,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                )
            )
            Spacer(Modifier.width(12.dp))
            NormalTag(
                modifier = Modifier
                    .width(42.dp)
                    .height(21.dp),
                tagName = "필수",
                shape = RoundedCornerShape(size = 55.dp),
                color = MateTripColors.Gray_11,
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500),
                    color = Color.White,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                innerPaddingValues = PaddingValues(0.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            CustomClickableTag(
                tagName = "만족",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = selectedSatisfactionLevel == SatisfactionLevel.GOOD,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onUpdateSatisfactionLevel(SatisfactionLevel.GOOD)}
            )
            Spacer(Modifier.width(10.dp))
            CustomClickableTag(
                tagName = "보통",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = selectedSatisfactionLevel == SatisfactionLevel.COMMONLY,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onUpdateSatisfactionLevel(SatisfactionLevel.COMMONLY)}
            )
            Spacer(Modifier.width(10.dp))
            CustomClickableTag(
                tagName = "불만족",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = selectedSatisfactionLevel == SatisfactionLevel.DISSATISFACTION,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = { onUpdateSatisfactionLevel(SatisfactionLevel.DISSATISFACTION) }
            )
        }
    }
}

@Composable
private fun RecommendNextSurvey(
    selectedRecommendationStatus: RecommendationStatus,
    onUpdateRecommendationStatus: (RecommendationStatus) -> Unit,
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "다음 동행자에게 추천하시나요?",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(700),
                    color = MateTripColors.Gray_11,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                )
            )
            Spacer(Modifier.width(12.dp))
            NormalTag(
                modifier = Modifier
                    .width(42.dp)
                    .height(21.dp),
                tagName = "필수",
                shape = RoundedCornerShape(size = 55.dp),
                color = MateTripColors.Gray_11,
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500),
                    color = Color.White,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
                innerPaddingValues = PaddingValues(0.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            CustomClickableTag(
                tagName = "추천",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = selectedRecommendationStatus == RecommendationStatus.SUGGESTION,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onUpdateRecommendationStatus(RecommendationStatus.SUGGESTION)}
            )
            Spacer(Modifier.width(10.dp))
            CustomClickableTag(
                tagName = "비추천",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = selectedRecommendationStatus == RecommendationStatus.NOT_RECOMMENDED,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onUpdateRecommendationStatus(RecommendationStatus.NOT_RECOMMENDED)}
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SelectTravelType(
    selectedCompanionType: CompanionType,
    onUpdateCompanionType: (CompanionType) -> Unit,
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "동행 유형을 선택해 주세요",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(700),
                    color = MateTripColors.Gray_11,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                )
            )
            Spacer(Modifier.width(12.dp))
            NormalTag(
                modifier = Modifier
                    .width(42.dp)
                    .height(21.dp),
                tagName = "필수",
                shape = RoundedCornerShape(size = 55.dp),
                color = MateTripColors.Gray_11,
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500),
                    color = Color.White,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                innerPaddingValues = PaddingValues(0.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ){
            CustomClickableTag(
                tagName = "전체동행",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = selectedCompanionType == CompanionType.ALL_ACCOMPANYING,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onUpdateCompanionType(CompanionType.ALL_ACCOMPANYING)}
            )
            Spacer(Modifier.width(10.dp))
            CustomClickableTag(
                tagName = "부분동행",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = selectedCompanionType == CompanionType.PARTIAL_COMPANIONSHIP,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onUpdateCompanionType(CompanionType.PARTIAL_COMPANIONSHIP)}
            )
            Spacer(Modifier.width(10.dp))
            CustomClickableTag(
                tagName = "숙소공유",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = selectedCompanionType == CompanionType.ACCOMMODATION_SHARING,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onUpdateCompanionType(CompanionType.ACCOMMODATION_SHARING)}
            )
            Spacer(Modifier.width(10.dp))
            CustomClickableTag(
                tagName = "투어동행",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = selectedCompanionType == CompanionType.ACCOMPANYING_TOUR,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onUpdateCompanionType(CompanionType.ACCOMPANYING_TOUR)}
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SelectReviewTag(
    selectedPersonalityType: SnapshotStateList<PersonalityType>,
    selectedTravelPreference: SnapshotStateList<TravelPreferenceForReview>,
    selectedTravelStyle: SnapshotStateList<TravelStyleForReview>
){
    val onClickPersonalityType: (PersonalityType) -> Unit = {
        if(it in selectedPersonalityType) selectedPersonalityType.remove(it)
        else selectedPersonalityType.add(it)
    }
    val onClickTravelPreference: (TravelPreferenceForReview) -> Unit = {
        if(it in selectedTravelPreference) selectedTravelPreference.remove(it)
        else selectedTravelPreference.add(it)
    }
    val onClickTravelStyle: (TravelStyleForReview) -> Unit = {
        if(it in selectedTravelStyle) selectedTravelStyle.remove(it)
        else selectedTravelStyle.add(it)
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "어떤 점이 좋았나요?",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(700),
                color = MateTripColors.Gray_11
            )
            Spacer(Modifier.width(12.dp))
            NormalTag(
                modifier = Modifier
                    .width(42.dp)
                    .height(21.dp),
                tagName = "필수",
                shape = RoundedCornerShape(size = 55.dp),
                color = MateTripColors.Gray_11,
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500),
                    color = Color.White,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
                innerPaddingValues = PaddingValues(0.dp)
            )
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = "동행자의 성격, 성향, 스타일을 알려주세요",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(500),
            color = MateTripColors.Gray_06
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = "성격",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(500),
            color = MateTripColors.Gray_11
        )
        Spacer(Modifier.height(10.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            CustomClickableTag(
                tagName = "친절해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = PersonalityType.KIND in selectedPersonalityType,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickPersonalityType(PersonalityType.KIND)}
            )
            CustomClickableTag(
                tagName = "밝아요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = PersonalityType.BRIGHT in selectedPersonalityType,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickPersonalityType(PersonalityType.BRIGHT)}
            )
            CustomClickableTag(
                tagName = "재밌어요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = PersonalityType.FUN in selectedPersonalityType,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickPersonalityType(PersonalityType.FUN)}
            )
            CustomClickableTag(
                tagName = "편안해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = PersonalityType.COMFORTABLE in selectedPersonalityType,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickPersonalityType(PersonalityType.COMFORTABLE)}
            )
            CustomClickableTag(
                tagName = "신뢰가 가요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = PersonalityType.TRUSTWORTHY in selectedPersonalityType,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickPersonalityType(PersonalityType.TRUSTWORTHY)}
            )
            CustomClickableTag(
                tagName = "긍정적이에요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = PersonalityType.POSITIVE in selectedPersonalityType,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickPersonalityType(PersonalityType.POSITIVE)}
            )
            CustomClickableTag(
                tagName = "센스있어요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = PersonalityType.SENSE in selectedPersonalityType,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickPersonalityType(PersonalityType.SENSE)}
            )
            CustomClickableTag(
                tagName = "감성적이에요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = PersonalityType.EMOTIONAL in selectedPersonalityType,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickPersonalityType(PersonalityType.EMOTIONAL)}
            )
            CustomClickableTag(
                tagName = "이성적이에요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = PersonalityType.RATIONAL in selectedPersonalityType,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickPersonalityType(PersonalityType.RATIONAL)}
            )
            CustomClickableTag(
                tagName = "열정적이에요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = PersonalityType.PASSIONATE in selectedPersonalityType,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickPersonalityType(PersonalityType.PASSIONATE)}
            )
            CustomClickableTag(
                tagName = "붙임성이 좋아요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = PersonalityType.GOOD_ATTACHMENT in selectedPersonalityType,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickPersonalityType(PersonalityType.GOOD_ATTACHMENT)}
            )
        }
        Spacer(Modifier.height(46.dp))
        Text(
            text = "여행 성향",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(500),
            color = MateTripColors.Gray_11
        )
        Spacer(Modifier.height(10.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            CustomClickableTag(
                tagName = "계획적이에요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelPreferenceForReview.PLANNED in selectedTravelPreference,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickTravelPreference(TravelPreferenceForReview.PLANNED)}
            )
            CustomClickableTag(
                tagName = "즉흥적이에요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelPreferenceForReview.SPONTANEOUS in selectedTravelPreference,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickTravelPreference(TravelPreferenceForReview.SPONTANEOUS)}
            )
            CustomClickableTag(
                tagName = "공금이 편해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelPreferenceForReview.PUBLIC_MONEY_CONVENIENT in selectedTravelPreference,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickTravelPreference(TravelPreferenceForReview.PUBLIC_MONEY_CONVENIENT)}
            )
            CustomClickableTag(
                tagName = "더치페이 해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelPreferenceForReview.DUTCH_PAY in selectedTravelPreference,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickTravelPreference(TravelPreferenceForReview.DUTCH_PAY)}
            )
            CustomClickableTag(
                tagName = "부지런해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelPreferenceForReview.DILIGENT in selectedTravelPreference,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickTravelPreference(TravelPreferenceForReview.DILIGENT)}
            )
            CustomClickableTag(
                tagName = "느긋해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelPreferenceForReview.RELAXED in selectedTravelPreference,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickTravelPreference(TravelPreferenceForReview.RELAXED)}
            )
        }
        Spacer(Modifier.height(46.dp))
        Text(
            text = "여행 스타일",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(500),
            color = MateTripColors.Gray_11
        )
        Spacer(Modifier.height(10.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            CustomClickableTag(
                tagName = "맛집을 좋아해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelStyleForReview.LIKE_RESTAURANTS in selectedTravelStyle,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickTravelStyle(TravelStyleForReview.LIKE_RESTAURANTS)}
            )
            CustomClickableTag(
                tagName = "맛집이 아니어도 돼요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelStyleForReview.DOES_NOT_HAVE_TO_BE_RESTAURANT in selectedTravelStyle,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickTravelStyle(TravelStyleForReview.DOES_NOT_HAVE_TO_BE_RESTAURANT)}
            )
            CustomClickableTag(
                tagName = "핫플을 선호해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelStyleForReview.PREFER_HOTPLE in selectedTravelStyle,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickTravelStyle(TravelStyleForReview.PREFER_HOTPLE)}
            )
            CustomClickableTag(
                tagName = "고즈넉한 곳을 좋아해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelStyleForReview.LIKE_QUIET_PLACES in selectedTravelStyle,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickTravelStyle(TravelStyleForReview.LIKE_QUIET_PLACES)}
            )
            CustomClickableTag(
                tagName = "사진 찍는 걸 좋아해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelStyleForReview.LIKE_TAKING_PICTURES in selectedTravelStyle,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickTravelStyle(TravelStyleForReview.LIKE_TAKING_PICTURES)}
            )
            CustomClickableTag(
                tagName = "관광지를 선호해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelStyleForReview.PREFER_TOURIST_DESTINATIONS in selectedTravelStyle,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickTravelStyle(TravelStyleForReview.PREFER_TOURIST_DESTINATIONS)}
            )
            CustomClickableTag(
                tagName = "힐링을 선호해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelStyleForReview.PREFER_HEALING in selectedTravelStyle,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickTravelStyle(TravelStyleForReview.PREFER_HEALING)}
            )
            CustomClickableTag(
                tagName = "액티비티를 즐겨요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelStyleForReview.ENJOY_ACTIVITY in selectedTravelStyle,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickTravelStyle(TravelStyleForReview.ENJOY_ACTIVITY)}
            )
            CustomClickableTag(
                tagName = "쇼핑을 좋아해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelStyleForReview.LIKE_SHOPPING in selectedTravelStyle,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickTravelStyle(TravelStyleForReview.LIKE_SHOPPING)}
            )
            CustomClickableTag(
                tagName = "카페를 좋아해요",
                shape = RoundedCornerShape(size = 6.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelStyleForReview.LIKE_CAFES in selectedTravelStyle,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                onClick = {onClickTravelStyle(TravelStyleForReview.LIKE_CAFES)}
            )
        }
    }
}

@Composable
private fun WriteReviewDetails(
    detailContent: String,
    onUpdateDetailContent: (String) -> Unit,
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "상세 리뷰를 작성해 주세요",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(700),
                color = MateTripColors.Gray_11
            )
            Spacer(Modifier.width(12.dp))
            NormalTag(
                modifier = Modifier
                    .width(42.dp)
                    .height(21.dp),
                tagName = "선택",
                shape = RoundedCornerShape(size = 55.dp),
                color = MateTripColors.Gray_03,
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500),
                    color = MateTripColors.Gray_08,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
                innerPaddingValues = PaddingValues(0.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            value = detailContent,
            onValueChange = onUpdateDetailContent,
            shape = RoundedCornerShape(size = 10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MateTripColors.Blue_03,
                unfocusedBorderColor = MateTripColors.Blue_03,
                focusedPlaceholderColor = MateTripColors.Gray_05,
                unfocusedPlaceholderColor = MateTripColors.Gray_05
            ),
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Justify,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            ),
            placeholder = {
                Text(
                    text = "메이트와 함께한 소중한 동행 경험을 알려주세요 :>",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontWeight = FontWeight(400),
                        textAlign = TextAlign.Justify,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
            },
        )
    }
}

@Composable
private fun SharePictures(
    pictures: SnapshotStateList<String>,
    onDeleteImage: (String) -> Unit,
    onCameraClick: () -> Unit,
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "여행의 특별한 순간을 공유해 주세요",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(700),
                color = MateTripColors.Gray_11
            )
            Spacer(Modifier.width(12.dp))
            NormalTag(
                modifier = Modifier
                    .width(42.dp)
                    .height(21.dp),
                tagName = "선택",
                shape = RoundedCornerShape(size = 55.dp),
                color = MateTripColors.Gray_03,
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500),
                    color = MateTripColors.Gray_08,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
                innerPaddingValues = PaddingValues(0.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            item{
                Column(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            color = MateTripColors.Blue_04,
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .clickable {onCameraClick()},
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Icons.camera_icon),
                        contentDescription = "Camera Icon"
                    )
                    Text(
                        text = "${pictures.size}/5",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontWeight = FontWeight(400),
                        color = MateTripColors.icon_color
                    )
                }
            }
            items(pictures){picture ->
                ImageLoadView(
                    shape = RoundedCornerShape(size = 10.dp),
                    size = 60.dp,
                    imageUrl = picture,
                    backgroundColor = MateTripColors.icon_loading_color,
                    isEnabledClose = true,
                    onCloseClick = { onDeleteImage(picture) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun WriteReviewUITest(){
    WriteReviewScreen(
        navBack = {},
        errState = ErrorState.Loading,
        onClickDone = {a, b, c, d, e, f, g, h ->
        },
        onUploadImage = {
            ""
        }
    )
}