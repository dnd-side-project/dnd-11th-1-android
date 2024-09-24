package com.materip.feature_home3.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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

// 동행글 작성 화면
@SuppressLint("UnrememberedMutableState")
@Composable
fun PostBoardScreen(
    viewModel: PostBoardViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val imageUploadState by viewModel.imageUploadState.collectAsState()
    var tagInput by remember { mutableStateOf("") }

    val title by viewModel.title.collectAsState()
    val content by viewModel.content.collectAsState()
    val gender by viewModel.preferredGender.collectAsState()
    val age by viewModel.preferredAge.collectAsState()
    val tags by viewModel.tagNames.collectAsState()
    val selectedType by viewModel.category.collectAsState()
    val selectedRegion by viewModel.region.collectAsState()
    val startDate by viewModel.startDate.collectAsState()
    val endDate by viewModel.endDate.collectAsState()
    val capacity by viewModel.capacity.collectAsState()
    val imageUris by viewModel.imageUris.collectAsState()

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
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp)
                    .background(Color.White)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                // 카메라, 사진 가져오기 버튼
                ImagePicker(
                    imageUris = imageUris,
                    onImageUrisChange = {
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
                    onTitleChange = { viewModel.handleIntent(PostBoardIntent.UpdateTitle(it)) }
                )

                // 내용 입력
                AccompanyContentInput(
                    content = content,
                    onContentChange = { viewModel.handleIntent(PostBoardIntent.UpdateContent(it)) }
                )

                // 태그 등록
                AccompanyTagInput(
                    tagInput = tagInput,
                    onTagInputChange = { tagInput = it },
                    tags = tags,
                    onTagAdd = { newTag ->
                        if (newTag.isNotEmpty() && tags.size < 5 && !tags.contains(newTag)) {
                            viewModel.handleIntent(PostBoardIntent.UpdateTagNames(tags + newTag))
                            tagInput = ""
                        }
                    },
                    onTagRemove = { tagToRemove ->
                        viewModel.handleIntent(PostBoardIntent.UpdateTagNames(tags.filter { it != tagToRemove }))
                    }
                )

                // 여행 지역
                AccompanyRegionButton(
                    selectedRegion = selectedRegion,
                    onRegionSelected = { viewModel.handleIntent(PostBoardIntent.UpdateRegion(it)) }
                )

                // 여행 일정
                TravelDateCalendar(
                    startDateString = startDate,
                    endDateString = endDate
                ) { start, end ->
                    viewModel.handleIntent(PostBoardIntent.UpdateStartDate(start))
                    viewModel.handleIntent(PostBoardIntent.UpdateEndDate(end))
                }

                // 동행 유형
                AccompanyTypeButton(
                    selectedCategories = selectedType,
                    onCategorySelected = {
                        viewModel.handleIntent(PostBoardIntent.UpdateCategory(it))
                    }
                )

                // 모집 인원
                SetCapacity(
                    capacity = capacity,
                    onCapacityChange = {
                        viewModel.handleIntent(PostBoardIntent.UpdateCapacity(it))
                    }
                )

                // 모집 연령
                AccompanyAgeButton(
                    selectedAge = age,
                    onAgeChange = {
                        viewModel.handleIntent(PostBoardIntent.UpdateAge(it))
                    }
                )

                // 모집 성별
                AccompanyGenderButton(
                    selectedGender = gender,
                    onGenderChange = {
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
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp)
                    .background(Color.White)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                // 카메라, 사진 가져오기 버튼
                ImagePicker(
                    imageUris = imageUris,
                    onImageUrisChange = {
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
                    onTitleChange = { viewModel.handleIntent(PostBoardIntent.UpdateTitle(it)) }
                )

                // 내용 입력
                AccompanyContentInput(
                    content = content,
                    onContentChange = { viewModel.handleIntent(PostBoardIntent.UpdateContent(it)) }
                )

                // 태그 등록
                AccompanyTagInput(
                    tagInput = tagInput,
                    onTagInputChange = { tagInput = it },
                    tags = tags,
                    onTagAdd = { newTag ->
                        if (newTag.isNotEmpty() && tags.size < 5 && !tags.contains(newTag)) {
                            viewModel.handleIntent(PostBoardIntent.UpdateTagNames(tags + newTag))
                            tagInput = ""
                        }
                    },
                    onTagRemove = { tagToRemove ->
                        viewModel.handleIntent(PostBoardIntent.UpdateTagNames(tags.filter { it != tagToRemove }))
                    }
                )

                // 여행 지역
                AccompanyRegionButton(
                    selectedRegion = selectedRegion,
                    onRegionSelected = { viewModel.handleIntent(PostBoardIntent.UpdateRegion(it)) }
                )

                // 여행 일정
                TravelDateCalendar(
                    startDateString = startDate,
                    endDateString = endDate
                ) { start, end ->
                    viewModel.handleIntent(PostBoardIntent.UpdateStartDate(start))
                    viewModel.handleIntent(PostBoardIntent.UpdateEndDate(end))
                }

                // 동행 유형
                AccompanyTypeButton(
                    selectedCategories = selectedType,
                    onCategorySelected = {
                        viewModel.handleIntent(PostBoardIntent.UpdateCategory(it))
                    }
                )

                // 모집 인원
                SetCapacity(
                    capacity = capacity,
                    onCapacityChange = {
                        viewModel.handleIntent(PostBoardIntent.UpdateCapacity(it))
                    }
                )

                // 모집 연령
                AccompanyAgeButton(
                    selectedAge = age,
                    onAgeChange = {
                        viewModel.handleIntent(PostBoardIntent.UpdateAge(it))
                    }
                )

                // 모집 성별
                AccompanyGenderButton(
                    selectedGender = gender,
                    onGenderChange = {
                        viewModel.handleIntent(PostBoardIntent.UpdateGender(it))
                    }
                )
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
        is PostBoardUiState.Error -> Text("오류: ${(uiState as PostBoardUiState.Error).message}")
    }
}