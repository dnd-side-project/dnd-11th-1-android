package com.materip.feature_home3.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.materip.core_designsystem.component.MateTripHomeButton
import com.materip.core_designsystem.icon.Badges
import com.materip.core_designsystem.theme.MateTripColors.Blue_03
import com.materip.core_designsystem.theme.MateTripColors.Blue_04
import com.materip.core_designsystem.theme.MateTripColors.Gray_05
import com.materip.core_designsystem.theme.MateTripColors.Gray_07
import com.materip.core_designsystem.theme.MateTripColors.Gray_10
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.feature_home3.intent.FormIntent
import com.materip.feature_home3.state.FormUiState
import com.materip.feature_home3.viewModel.FormViewModel

@Composable
fun FormScreen(
    boardId: Int,
    onNavigateUp: () -> Unit,
    viewModel: FormViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isButtonEnabled by viewModel.isButtonEnabled.collectAsState()

    var showDialogState by remember { mutableStateOf(false) }

    if (showDialogState) {
        AlertDialog(
            onDismissRequest = { showDialogState = false },
            confirmButton = {
                MateTripHomeButton(
                    buttonText = "확인",
                    enabled = true,
                    onClick = {
                        showDialogState = false
                        onNavigateUp()
                    },
                    modifier = Modifier
                        .width(296.dp)
                        .height(54.dp)
                )
            },
            text = {
                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .width(320.dp)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "동행 신청을 보냈어요 :)",
                        style = MateTripTypographySet.title03,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(size = 10.dp)
        )
    }

    when (uiState) {
        is FormUiState.Initial -> {
            var introduce by remember { mutableStateOf("") }
            var chatLink by remember { mutableStateOf("") }

            // 동행신청에 대한 안내 문구
            FormOverview()

            // 소개를 받는 텍스트 필드
            FormContentInput(
                introduce = introduce,
                onIntroduceChange = {
                    introduce = it
                    viewModel.onFormIntent(FormIntent.UpdateIntroduce(it))
                }
            )

            // 경고 문구
            FormContentWarning()

            // 채팅 링크를 받는 텍스트 필드
            FormOpenChatLink(
                chatLink = chatLink,
                onChatLinkChange = {
                    chatLink = it
                    viewModel.onFormIntent(FormIntent.UpdateChatLink(it))
                }
            )

            // 보내기 버튼
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MateTripHomeButton(
                    buttonText = "보내기",
                    enabled = isButtonEnabled,
                    onClick = {
                        viewModel.onFormIntent(FormIntent.SubmitCompanionRequest(boardId))
                        showDialogState = true
                    },
                    modifier = Modifier
                        .width(370.dp)
                        .height(54.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
        }

        FormUiState.Loading -> {
            CircularProgressIndicator()
        }

        is FormUiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MateTripHomeButton(
                    buttonText = "보내기",
                    enabled = false,
                    onClick = { /* 버튼 비활성화로 클릭 불가 */ },
                    modifier = Modifier
                        .width(370.dp)
                        .height(54.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
        }

        is FormUiState.Error -> {
            Text("오류: \\${(uiState as FormUiState.Error).message}")
        }
    }
}

@Composable
private fun FormContentInput(
    introduce: String,
    onIntroduceChange: (String) -> Unit
) {
    val isContentEmpty = introduce.isEmpty()
    Column(
        modifier = Modifier.padding(start = 20.dp, top = 30.dp, end = 20.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BasicTextField(
            value = introduce,
            onValueChange = onIntroduceChange,
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
                            text = "자기소개와 동행신청 내용을 작성해주세요.\n\n최대한 자세하게 작성해주시면 좋아요.\nex)본인성향/여행스타일/음식취향 등\n(최대 500자)",
                            style = MateTripTypographySet.title04,
                            color = Gray_05
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
private fun FormOverview() {
    Column(
        modifier = Modifier.padding(start = 20.dp, top = 10.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "더 나은 동행을 위해 \n간단한 소개를 작성해주세요 !",
            style = MateTripTypographySet.headline03
        )
        Text(
            text = "작성한 자기소개는 동행자에게만 공개돼요.",
            style = MateTripTypographySet.body04,
            color = Gray_05
        )
    }
}

@Composable
private fun FormContentWarning() {
    Row(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 40.dp)
            .width(370.dp)
            .height(60.dp)
            .background(color = Blue_04, shape = RoundedCornerShape(size = 10.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.Start),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = Badges.information_badge),
            contentDescription = "information badge"
        )
        Text(
            text = "전화번호, 카카오톡 아이디 등 과도한 개인 정보를 요구하는 경우 가이드 위반 모임이므로 제재를 당할 수 있습니다.",
            style = MateTripTypographySet.body05,
            color = Gray_10
        )
    }
}

@Composable
private fun FormOpenChatLink(
    chatLink: String,
    onChatLinkChange: (String) -> Unit
) {
    val isContentEmpty = chatLink.isEmpty()
    Column(
        modifier = Modifier.padding(start = 20.dp, top = 30.dp, end = 20.dp, bottom = 60.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                append("동행자와 카카오톡 오픈채팅")
                withStyle(style = SpanStyle(color = Gray_07)) {
                    append("에서 대화해 보세요!")
                }
            },
            style = MateTripTypographySet.title04
        )
        BasicTextField(
            value = chatLink,
            onValueChange = onChatLinkChange,
            modifier = Modifier
                .width(370.dp)
                .height(48.dp)
                .border(
                    width = 1.dp,
                    color = Blue_03,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(14.dp),
            textStyle = MateTripTypographySet.title04,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopStart
                ) {
                    if (isContentEmpty) {
                        Text(
                            text = "카카오톡 오픈채팅 링크를 입력해주세요.",
                            style = MateTripTypographySet.title04,
                            color = Gray_05
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

//@Preview
@Composable
fun FormScreenPreview() {
    var introduce by remember { mutableStateOf("") }
    var chatLink by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        // 동행신청에 대한 안내 문구
        FormOverview()

        // 소개를 받는 텍스트 필드
        FormContentInput(
            introduce = introduce,
            onIntroduceChange = { introduce = it }
        )
        FormContentWarning()
        FormOpenChatLink(
            chatLink = chatLink,
            onChatLinkChange = { chatLink = it }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MateTripHomeButton(
                buttonText = "보내기",
                enabled = true,
                onClick = { },
                modifier = Modifier
                    .width(370.dp)
                    .height(54.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlertDialogPreview() {
    var showDialogState by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = { showDialogState = false },
        confirmButton = {
            MateTripHomeButton(
                buttonText = "확인",
                enabled = true,
                onClick = { showDialogState = false },
                modifier = Modifier
                    .width(296.dp)
                    .height(54.dp)
            )
        },
        text = {
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .width(320.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "동행 신청을 보냈어요 :)",
                    style = MateTripTypographySet.title03,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(size = 10.dp)
    )
}