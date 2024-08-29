@file:OptIn(ExperimentalLayoutApi::class)

package com.materip.feature_home3.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.materip.core_designsystem.component.ToggleButton
import com.materip.core_designsystem.icon.Icons.fold_icon
import com.materip.core_designsystem.icon.Icons.minus_icon
import com.materip.core_designsystem.icon.Icons.plus_icon
import com.materip.core_designsystem.theme.MateTripColors.ActivatedColor
import com.materip.core_designsystem.theme.MateTripColors.Blue_02
import com.materip.core_designsystem.theme.MateTripColors.Blue_03
import com.materip.core_designsystem.theme.MateTripColors.Blue_04
import com.materip.core_designsystem.theme.MateTripColors.Devider
import com.materip.core_designsystem.theme.MateTripColors.Gray_03
import com.materip.core_designsystem.theme.MateTripColors.Gray_06
import com.materip.core_designsystem.theme.MateTripColors.Gray_11
import com.materip.core_designsystem.theme.MateTripColors.Primary
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.core_model.accompany_board.create.Category
import com.materip.core_model.accompany_board.create.PreferredAge
import com.materip.core_model.accompany_board.create.PreferredGender
import com.materip.core_model.accompany_board.create.Region
import com.materip.feature_home3.intent.PostBoardIntent
import com.materip.feature_home3.state.ImageUploadState
import com.materip.feature_home3.state.PostBoardUiState
import com.materip.feature_home3.ui.component.ImagePicker
import com.materip.feature_home3.ui.component.TravelDateCalendar
import com.materip.feature_home3.viewModel.PostBoardViewModel
import java.time.LocalDate

// 동행글 작성 화면
@SuppressLint("UnrememberedMutableState")
@Composable
fun PostBoardScreen(
    viewModel: PostBoardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val imageUploadState by viewModel.imageUploadState.collectAsState()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(PreferredGender.ANY) }
    var age by remember { mutableStateOf(PreferredAge.ANY) }
    var tagInput by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf(listOf<String>()) }
    var selectedType by remember { mutableStateOf<Category?>(null) }
    var selectedRegion by remember { mutableStateOf(Region.SEOUL) }
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }
    var capacity by remember { mutableIntStateOf(2) }
    var imageUris by remember { mutableStateOf(listOf<String>()) }

    when (uiState) {
        is PostBoardUiState.Loading -> CircularProgressIndicator()
        is PostBoardUiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                // 카메라, 사진 가져오기 버튼
                ImagePicker(
                    imageUris = imageUris,
                    onImageUrisChange = {
                        imageUris = it
                        viewModel.handleIntent(PostBoardIntent.UpdateImageUris(it))
                    },
                    onUploadImage = { context, uri ->
                        viewModel.handleIntent(PostBoardIntent.UploadImage(context, uri))
                    },
                    onDeleteImage = { imagePath ->
                        viewModel.handleIntent(PostBoardIntent.DeleteImage(imagePath))
                    }
                )

                // 제목 입력
                AccompanyTitleInput(
                    title = title,
                    onTitleChange = {
                        title = it
                        viewModel.handleIntent(PostBoardIntent.UpdateTitle(it))
                    }
                )

                // 내용 입력
                AccompanyContentInput(
                    content = content,
                    onContentChange = {
                        content = it
                        viewModel.handleIntent(PostBoardIntent.UpdateContent(it))
                    }
                )

                // 태그 등록
                AccompanyTagInput(
                    tagInput = tagInput,
                    onTagInputChange = { tagInput = it },
                    tags = tags,
                    onTagAdd = { newTag ->
                        if (newTag.isNotEmpty() && tags.size < 5 && !tags.contains(newTag)) {
                            tags = tags + newTag
                            tagInput = ""
                            viewModel.handleIntent(PostBoardIntent.UpdateTagNames(tags))
                        }
                    },
                    onTagRemove = { tagToRemove ->
                        tags = tags.filter { it != tagToRemove }
                        viewModel.handleIntent(PostBoardIntent.UpdateTagNames(tags))
                    }
                )

                // 여행 지역
                AccompanyRegionButton(
                    selectedRegion = selectedRegion,
                    onRegionSelected = { newSelectedRegion ->
                        selectedRegion = newSelectedRegion
                        viewModel.handleIntent(PostBoardIntent.UpdateRegion(newSelectedRegion))
                    }
                )

                // 여행 일정
                TravelDateCalendar { start, end ->
                    startDate = start
                    endDate = end
                    viewModel.handleIntent(PostBoardIntent.UpdateStartDate(start.toString()))
                    viewModel.handleIntent(PostBoardIntent.UpdateEndDate(end.toString()))
                }

                // 동행 유형
                AccompanyTypeButton(
                    selectedType = selectedType,
                    onTypeSelected = { newSelectedType ->
                        selectedType = newSelectedType
                        viewModel.handleIntent(
                            PostBoardIntent.UpdateCategory(
                                listOfNotNull(
                                    newSelectedType
                                )
                            )
                        )
                    }
                )

                // 모집 인원
                SetCapacity(
                    capacity = capacity,
                    onCapacityChange = { newCapacity ->
                        capacity = newCapacity
                        viewModel.handleIntent(PostBoardIntent.UpdateCapacity(newCapacity))
                    }
                )

                // 모집 연령
                AccompanyAgeButton(
                    selectedAge = age,
                    onAgeChange = {
                        age = it
                        viewModel.handleIntent(PostBoardIntent.UpdateAge(it))
                    }
                )

                // 모집 성별
                AccompanyGenderButton(
                    selectedGender = gender,
                    onGenderChange = {
                        gender = it
                        viewModel.handleIntent(PostBoardIntent.UpdateGender(it))
                    }
                )

                Spacer(modifier = Modifier.height(50.dp))
            }
        }

        is PostBoardUiState.Error
        -> Text("오류: \${(uiState as HomeUiState.Error).message}")

        is PostBoardUiState.Initial -> {
            // Handle initial state if needed
        }
    }
    when (imageUploadState) {
        is ImageUploadState.Loading -> CircularProgressIndicator()
        is ImageUploadState.Error -> Text("이미지 업로드 오류: ${(imageUploadState as ImageUploadState.Error).error}")
        is ImageUploadState.Success -> {
            // Handle success state if needed
        }
        else -> {}
    }
}


@Composable
private fun AccompanyTitleInput(
    title: String,
    onTitleChange: (String) -> Unit
) {
    val isTitleEmpty = title.isEmpty()
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "제목",
            color = Gray_11,
            modifier = Modifier.size(320.dp, 20.dp),
            style = MateTripTypographySet.title04
        )
        BasicTextField(
            value = title,
            onValueChange = onTitleChange,
            modifier = Modifier
                .width(370.dp)
                .height(44.dp)
                .border(
                    width = 1.dp,
                    color = Blue_03,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(12.dp),
            textStyle = MateTripTypographySet.title04,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (isTitleEmpty) {
                        Text(
                            text = "제목을 입력해주세요.(최대 30자)",
                            style = MateTripTypographySet.title04,
                            color = Gray_06
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
private fun AccompanyContentInput(
    content: String,
    onContentChange: (String) -> Unit
) {
    val isContentEmpty = content.isEmpty()
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "내용",
            color = Gray_11,
            modifier = Modifier.size(320.dp, 20.dp),
            style = MateTripTypographySet.title04
        )
        BasicTextField(
            value = content,
            onValueChange = onContentChange,
            modifier = Modifier
                .width(370.dp)
                .height(234.dp)
                .border(
                    width = 1.dp,
                    color = Blue_03,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(12.dp),
            textStyle = MateTripTypographySet.title04,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopStart
                ) {
                    if (isContentEmpty) {
                        Text(
                            text = "동행 모집 내용을 작성해주세요.\n\n최대한 자세하게 작성해주시면 좋아요.\nex)구체적인 장소, 여행목적, 동행을 구하는 이유 등\n(최대 500자)",
                            style = MateTripTypographySet.title04,
                            color = Gray_06
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
private fun AccompanyGenderButton(
    selectedGender: PreferredGender,
    onGenderChange: (PreferredGender) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "모집 성별",
            color = Gray_11,
            modifier = Modifier.size(320.dp, 20.dp),
            style = MateTripTypographySet.title04
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ToggleButton(
                buttonText = "동일 성별",
                isSelected = selectedGender == PreferredGender.SAME,
                onClick = { onGenderChange(PreferredGender.SAME) }
            )
            ToggleButton(
                buttonText = "상관없음",
                isSelected = selectedGender == PreferredGender.ANY,
                onClick = { onGenderChange(PreferredGender.ANY) }
            )
        }
    }
}


@Composable
private fun AccompanyAgeButton(
    selectedAge: PreferredAge,
    onAgeChange: (PreferredAge) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "모집 연령",
            color = Gray_11,
            modifier = Modifier.size(320.dp, 20.dp),
            style = MateTripTypographySet.title04
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ToggleButton(
                buttonText = "동일 나이대",
                isSelected = selectedAge == PreferredAge.SAME,
                onClick = { onAgeChange(PreferredAge.SAME) }
            )
            ToggleButton(
                buttonText = "상관없음",
                isSelected = selectedAge == PreferredAge.ANY,
                onClick = { onAgeChange(PreferredAge.ANY) }
            )
        }
    }
}

@Composable
fun AccompanyTagInput(
    tagInput: String,
    onTagInputChange: (String) -> Unit,
    tags: List<String>,
    onTagAdd: (String) -> Unit,
    onTagRemove: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "태그 등록",
            color = Gray_11,
            modifier = Modifier.size(320.dp, 20.dp),
            style = MateTripTypographySet.title04
        )
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 태그 입력
            BasicTextField(
                value = tagInput,
                onValueChange = onTagInputChange,
                modifier = Modifier
                    .width(330.dp)
                    .height(20.dp),
                textStyle = MateTripTypographySet.body04,
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (tagInput.isEmpty()) {
                            Text(
                                text = "관심사와 키워드를 입력해주세요. (최대 5개)",
                                style = MateTripTypographySet.body04,
                                color = Gray_06
                            )
                        }
                        innerTextField()
                    }
                }
            )
            Box(
                modifier = Modifier
                    .width(36.dp)
                    .height(28.dp)
                    .background(ActivatedColor, shape = RoundedCornerShape(6.dp))
                    .clickable {
                        if (tagInput.isNotEmpty() && tags.size < 5 && !tags.contains(tagInput)) {
                            onTagAdd(tagInput)
                        }
                    }
                    .padding(7.dp, 5.dp)
            ) {
                Text(
                    text = "입력",
                    style = MateTripTypographySet.title05,
                    color = Color.White
                )
            }
        }
        // 구분선
        SimpleDivider()
        // 태그 목록
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            maxItemsInEachRow = Int.MAX_VALUE
        ) {
            tags.forEach { tag ->
                Box(
                    modifier = Modifier
                        .height(28.dp)
                        .background(Blue_04, shape = RoundedCornerShape(6.dp))
                        .padding(12.dp, 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "#$tag", style = MateTripTypographySet.body04)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Remove tag",
                            modifier = Modifier
                                .size(16.dp)
                                .clickable { onTagRemove(tag) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AccompanyTypeButton(
    selectedType: Category?,
    onTypeSelected: (Category?) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val options = listOf("전체 동행", "부분 동행", "식사 동행", "투어 동행", "숙박 공유")

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "동행 유형",
            color = Gray_11,
            modifier = Modifier.size(320.dp, 20.dp),
            style = MateTripTypographySet.title04
        )
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                value = selectedType?.name ?: "",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .width(330.dp)
                    .height(20.dp),
                textStyle = MateTripTypographySet.body04,
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (selectedType == null) {
                            Text(
                                text = "동행 유형을 선택해주세요.",
                                style = MateTripTypographySet.body04,
                                color = Gray_06
                            )
                        }
                        innerTextField()
                    }
                }
            )
            Icon(
                painter = painterResource(fold_icon),
                contentDescription = "Open dialog",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { showDialog = true }
            )
        }
        SimpleDivider()
    }
    if (showDialog) {
        CustomRadioButtonDialog(
            options = options,
            selectedOption = selectedType,
            onOptionsSelected = {
                onTypeSelected(it)
                showDialog = false
            },
            onDismissRequest = { showDialog = false }
        )
    }
}

@Composable
fun AccompanyRegionButton(
    selectedRegion: Region,
    onRegionSelected: (Region) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val options = listOf("서울", "경기·인천", "충청·대전·세종", "강원", "전라·광주", "경상·대구", "부산", "제주")

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "여행 지역",
            color = Gray_11,
            modifier = Modifier.size(320.dp, 20.dp),
            style = MateTripTypographySet.title04
        )
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                value = selectedRegion.name,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .width(330.dp)
                    .height(20.dp),
                textStyle = MateTripTypographySet.body04,
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (selectedRegion.name.isEmpty()) {
                            Text(
                                text = "여행 지역을 선택해주세요.",
                                style = MateTripTypographySet.body04,
                                color = Gray_06
                            )
                        }
                        innerTextField()
                    }
                }
            )
            Icon(
                painter = painterResource(fold_icon),
                contentDescription = "Open dialog",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { showDialog = true }
            )
        }
        SimpleDivider()
    }
    if (showDialog) {
        RegionRadioButtonDialog(
            options = options,
            selectedOption = selectedRegion,
            onOptionsSelected = {
                onRegionSelected(it)
                showDialog = false
            },
            onDismissRequest = { showDialog = false }
        )
    }
}

@Composable
fun RegionRadioButtonDialog(
    options: List<String>,
    selectedOption: Region,
    onOptionsSelected: (Region) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .width(320.dp)
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .padding(10.dp)
        ) {
            options.forEachIndexed { index, option ->
                val region = Region.entries[index]
                Row(
                    horizontalArrangement = Arrangement.spacedBy(48.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(320.dp)
                        .height(47.dp)
                        .clickable { onOptionsSelected(region) }
                        .padding(start = 10.dp, top = 10.dp, end = 12.dp, bottom = 11.dp)
                ) {
                    OptionText(option)
                    RadioButton(
                        selected = selectedOption == region,
                        onClick = { onOptionsSelected(region) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Primary,
                            unselectedColor = Blue_02
                        )
                    )
                }
                if (index < options.size - 1) {
                    SimpleDivider(Gray_03)
                }
            }
        }
    }
}

@Composable
fun CustomRadioButtonDialog(
    options: List<String>,
    selectedOption: Category?,
    onOptionsSelected: (Category?) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .width(320.dp)
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .padding(10.dp)
        ) {
            options.forEach { option ->
                val category = Category.valueOf(option)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(48.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(320.dp)
                        .height(47.dp)
                        .clickable { onOptionsSelected(category) }
                        .padding(start = 10.dp, top = 10.dp, end = 12.dp, bottom = 11.dp)
                ) {
                    OptionText(option)
                    RadioButton(
                        selected = selectedOption == category,
                        onClick = { onOptionsSelected(category) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Primary,
                            unselectedColor = Blue_02
                        )
                    )
                }
                if (options.indexOf(option) < options.size - 1) {
                    SimpleDivider(Gray_03)
                }
            }
        }
    }
}

@Composable
fun SimpleDivider(
    dividerColor: Color = Devider
) {
    Box(
        modifier = Modifier
            .padding(0.dp)
            .width(370.dp)
            .height(1.dp)
            .background(dividerColor)
    )
}

@Composable
fun OptionText(text: String) {
    Text(
        text = text,
        style = MateTripTypographySet.body02,
        modifier = Modifier
            .width(230.dp)
            .height(26.dp),
        color = Gray_11
    )
}

@Composable
fun SetCapacity(
    capacity: Int,
    onCapacityChange: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "모집 인원",
            color = Gray_11,
            modifier = Modifier.size(320.dp, 20.dp),
            style = MateTripTypographySet.title04
        )
        Text(
            text = "본인을 포함한 인원을 설정해주세요.  (최대 6명)",
            style = MateTripTypographySet.body06,
            modifier = Modifier.padding(bottom = 8.dp),
            color = Gray_06
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = {
                    if (capacity > 2) onCapacityChange(capacity - 1)
                },
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .background(
                        color = Color(0xFFD9D9D9),
                        shape = RoundedCornerShape(size = 12.dp)
                    )
                    .padding(3.dp)
            ) {
                Image(
                    painter = painterResource(id = minus_icon),
                    contentDescription = "Minus icon"
                )
            }
            Text(text = capacity.toString(), style = MateTripTypographySet.numberBold1)
            IconButton(
                onClick = {
                    if (capacity < 6) onCapacityChange(capacity + 1)
                },
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .background(
                        color = Color.Black,
                        shape = RoundedCornerShape(size = 12.dp)
                    )
                    .padding(3.dp)
            ) {
                Image(
                    painter = painterResource(id = plus_icon),
                    contentDescription = "Plus icon"
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        SimpleDivider(Gray_03)
        Spacer(modifier = Modifier.height(20.dp))
    }
}