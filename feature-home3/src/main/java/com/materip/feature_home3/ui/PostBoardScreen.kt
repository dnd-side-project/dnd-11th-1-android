package com.materip.feature_home3.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.materip.core_model.ui_model.Category
import com.materip.core_model.ui_model.PreferredAge
import com.materip.core_model.ui_model.PreferredGender
import com.materip.core_model.ui_model.Region
import com.materip.feature_home3.intent.PostBoardIntent
import com.materip.feature_home3.state.ImageUploadState
import com.materip.feature_home3.state.PostBoardUiState
import com.materip.feature_home3.ui.component.AccompanyAgeButton
import com.materip.feature_home3.ui.component.AccompanyContentInput
import com.materip.feature_home3.ui.component.AccompanyGenderButton
import com.materip.feature_home3.ui.component.AccompanyRegionButton
import com.materip.feature_home3.ui.component.AccompanyTagInput
import com.materip.feature_home3.ui.component.AccompanyTitleInput
import com.materip.feature_home3.ui.component.AccompanyTypeButton
import com.materip.feature_home3.ui.component.ImagePicker
import com.materip.feature_home3.ui.component.SetCapacity
import com.materip.feature_home3.ui.component.TravelDateCalendar
import com.materip.feature_home3.viewModel.PostBoardViewModel
import java.time.LocalDate

// 동행글 작성 화면
@SuppressLint("UnrememberedMutableState")
@Composable
fun PostBoardScreen(
    viewModel: PostBoardViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val imageUploadState by viewModel.imageUploadState.collectAsState()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(PreferredGender.ANY) }
    var age by remember { mutableStateOf(PreferredAge.ANY) }
    var tagInput by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf(listOf<String>()) }
    var selectedType by remember { mutableStateOf(listOf<Category>()) }
    var selectedRegion by remember { mutableStateOf(Region.SEOUL) }
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }
    var capacity by remember { mutableIntStateOf(2) }
    var imageUris by remember { mutableStateOf(listOf<String>()) }

    when (imageUploadState) {
        is ImageUploadState.Loading -> CircularProgressIndicator()
        is ImageUploadState.Error -> Text("이미지 업로드 오류: ${(imageUploadState as ImageUploadState.Error).error}")
        is ImageUploadState.Success -> {
            // Handle success state if needed
        }
        else -> {}
    }

    when (uiState) {
        is PostBoardUiState.Initial -> {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = 20.dp, end = 20.dp)
                    .verticalScroll(rememberScrollState()),
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
                    selectedCategories = selectedType,
                    onCategorySelected = { newSelectedCategories ->
                        selectedType = newSelectedCategories
                        viewModel.handleIntent(PostBoardIntent.UpdateCategory(newSelectedCategories))
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
        is PostBoardUiState.Loading -> CircularProgressIndicator()
        is PostBoardUiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = 20.dp, end = 20.dp)
                    .verticalScroll(rememberScrollState()),
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
                    selectedCategories = selectedType,
                    onCategorySelected = { newSelectedCategories ->
                        selectedType = newSelectedCategories
                        viewModel.handleIntent(PostBoardIntent.UpdateCategory(newSelectedCategories))
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
        is PostBoardUiState.Error -> Text("오류: ${(uiState as PostBoardUiState.Error).message}")
    }
}

@Preview(showBackground = true)
@Composable
fun PostBoardScreenPreview() {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(PreferredGender.ANY) }
    var age by remember { mutableStateOf(PreferredAge.ANY) }
    var tagInput by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf(listOf<String>()) }
    var selectedType by remember { mutableStateOf(listOf<Category>()) }
    var selectedRegion by remember { mutableStateOf(Region.SEOUL) }
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }
    var capacity by remember { mutableIntStateOf(2) }
    var imageUris by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        // 카메라, 사진 가져오기 버튼
        ImagePicker(
            imageUris = imageUris,
            onImageUrisChange = {
                imageUris = it
            },
            onUploadImage = { _, _ -> },
            onDeleteImage = { }
        )

        // 제목 입력
        AccompanyTitleInput(
            title = title,
            onTitleChange = {
                title = it
            }
        )

        // 내용 입력
        AccompanyContentInput(
            content = content,
            onContentChange = {
                content = it
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
                }
            },
            onTagRemove = { tagToRemove ->
                tags = tags.filter { it != tagToRemove }
            }
        )

        // 여행 지역
        AccompanyRegionButton(
            selectedRegion = selectedRegion,
            onRegionSelected = { newSelectedRegion ->
                selectedRegion = newSelectedRegion
            }
        )

        // 여행 일정
        TravelDateCalendar { start, end ->
            startDate = start
            endDate = end
        }

        // 동행 유형
        AccompanyTypeButton(
            selectedCategories = selectedType,
            onCategorySelected = { newSelectedType ->
                selectedType = newSelectedType
            }
        )

        // 모집 인원
        SetCapacity(
            capacity = capacity,
            onCapacityChange = { newCapacity ->
                capacity = newCapacity
            }
        )

        // 모집 연령
        AccompanyAgeButton(
            selectedAge = age,
            onAgeChange = {
                age = it
            }
        )

        // 모집 성별
        AccompanyGenderButton(
            selectedGender = gender,
            onGenderChange = {
                gender = it
            }
        )

        Spacer(modifier = Modifier.height(50.dp))
    }
}