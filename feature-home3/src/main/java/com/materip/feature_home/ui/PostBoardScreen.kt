package com.materip.feature_home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.materip.feature_home.viewModel.HomeHiltViewModel
import com.materip.feature_home.viewModel.HomeUiState
import com.materip.matetrip.component.ToggleButton
import com.materip.matetrip.theme.MateTripColors.Blue_03
import com.materip.matetrip.theme.MateTripColors.Gray_06
import com.materip.matetrip.theme.MateTripColors.Gray_11
import com.materip.matetrip.theme.MateTripTypographySet

@Composable
fun PostBoardScreen(
    viewModel: HomeHiltViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        // 카메라, 사진 가져오기 버튼

        // 제목 입력
        AccompanyTitleInput(
            title = title,
            onTitleChange = { title = it }
        )
        // 내용 입력
        AccompanyContentInput(
            content = content,
            onContentChange = { content = it }
        )
        // 태그 등록

        // 여행 지역

        // 여행 일정

        // 동행 유형

        // 모집 인원

        // 모집 연령
        AccompanyAgeButton(
            selectedAge = age,
            onAgeChange = { age = it }
        )
        // 모집 성별
        AccompanyGenderButton(
            selectedGender = gender,
            onGenderChange = { gender = it }
        )

        when (uiState) {
            is HomeUiState.Loading -> CircularProgressIndicator()
            is HomeUiState.Success -> Text("게시글이 성공적으로 작성되었습니다.")
            is HomeUiState.Error -> Text("오류: ${(uiState as HomeUiState.Error).message}")
            else -> {}
        }
    }
}

@Composable
private fun AccompanyTitleInput(
    title: String,
    onTitleChange: (String) -> Unit
) {
    val isTitleEmpty = title.isEmpty()
    Text(
        text = "제목",
        color = Gray_11,
        modifier = Modifier.size(320.dp, 20.dp)
    )
    Spacer(modifier = Modifier.height(12.dp))
    BasicTextField(
        value = title,
        onValueChange = onTitleChange,
        modifier = Modifier
            .width(320.dp)
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

@Composable
private fun AccompanyContentInput(
    content: String,
    onContentChange: (String) -> Unit
) {
    val isContentEmpty = content.isEmpty()
    Text(
        text = "내용",
        color = Gray_11,
        modifier = Modifier.size(320.dp, 20.dp)
    )
    Spacer(modifier = Modifier.height(12.dp))
    BasicTextField(
        value = content,
        onValueChange = onContentChange,
        modifier = Modifier
            .width(320.dp)
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

@Composable
private fun AccompanyGenderButton(
    selectedGender: String,
    onGenderChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "모집 성별",
            color = Gray_11,
            modifier = Modifier.size(320.dp, 20.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ToggleButton(
                buttonText = "동일 성별",
                isSelected = selectedGender == "동일 성별",
                onClick = { onGenderChange("동일 성별") }
            )
            ToggleButton(
                buttonText = "상관없음",
                isSelected = selectedGender == "상관없음",
                onClick = { onGenderChange("상관없음") }
            )
        }
    }
}


@Composable
private fun AccompanyAgeButton(
    selectedAge: String,
    onAgeChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "모집 연령",
            color = Gray_11,
            modifier = Modifier.size(320.dp, 20.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ToggleButton(
                buttonText = "동일 나이대",
                isSelected = selectedAge == "동일 나이대",
                onClick = { onAgeChange("동일 나이대") }
            )
            ToggleButton(
                buttonText = "상관없음",
                isSelected = selectedAge == "상관없음",
                onClick = { onAgeChange("상관없음") }
            )
        }
    }
}

@Preview
@Composable
fun PreviewPostScreen() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // ui 확인용
    }
}