package com.materip.feature_home3.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.materip.core_common.toDisplayString
import com.materip.core_designsystem.component.DeleteButton
import com.materip.core_designsystem.component.MateTripHomeButton
import com.materip.core_designsystem.theme.MateTripColors.NoColor
import com.materip.core_designsystem.theme.MateTripColors.NoTextColor
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.feature_home3.intent.HomeIntent
import com.materip.feature_home3.state.HomeUiState
import com.materip.feature_home3.ui.component.BoardDetailTopAppBar
import com.materip.feature_home3.ui.component.ShowCategory
import com.materip.feature_home3.ui.component.ShowImageList
import com.materip.feature_home3.ui.component.ShowPreferredPerson
import com.materip.feature_home3.ui.component.ShowRecruitment
import com.materip.feature_home3.ui.component.ShowSchedule
import com.materip.feature_home3.ui.component.ShowUserBoardInfo
import com.materip.feature_home3.ui.component.ShowUserProfile
import com.materip.feature_home3.viewModel.HomeViewModel

/**
 * 동행글 상세 화면
 * param: onNavigateToForm 동행신청
 * param: onNavigateToUserProfile 유저프로필상세보기
 * */
@Composable
fun NavigateToPostScreen(
    boardId: Int,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToForm: () -> Unit,
    onNavigateToUserProfile: () -> Unit,
    onNavigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(boardId) {
        viewModel.onHomeIntent(HomeIntent.LoadBoardDetail(boardId))
    }

    when (uiState) {
        is HomeUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is HomeUiState.SuccessLoad -> {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                val boardInfo = (uiState as HomeUiState.SuccessLoad).boardDetail.boardInfo
                val profileInfo = (uiState as HomeUiState.SuccessLoad).boardDetail.profileThumbnail

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                ) {
                    ShowImageList(imageUris = boardInfo.imageUrls)

                    BoardDetailTopAppBar(
                        onNavigateUp = onNavigateUp,
                        showDialogState = viewModel.showDialogState,
                    )
                }

                ShowUserProfile(
                    nickname = profileInfo.nickname,
                    birthYear = profileInfo.birthYear,
                    gender = profileInfo.gender.toDisplayString(),
                    profileImageUrl = profileInfo.profileImageUrl,
                    onNavigateToProfile = onNavigateToUserProfile
                )

                ShowUserBoardInfo(
                    title = boardInfo.title,
                    content = boardInfo.content,
                    tagNames = boardInfo.tagNames
                )

                ShowSchedule(
                    region = boardInfo.region.toDisplayString(),
                    startDate = boardInfo.startDate,
                    endDate = boardInfo.endDate
                )

                ShowCategory(category = boardInfo.categories.map { it.toDisplayString() })

                ShowPreferredPerson(
                    preferredAge = boardInfo.preferredAge.toDisplayString(),
                    preferredGender = boardInfo.preferredGender.toDisplayString(),
                    birthYear = profileInfo.birthYear,
                    userGender = profileInfo.gender.toDisplayString()
                )

                ShowRecruitment(boardInfo.headCount, boardInfo.capacity)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MateTripHomeButton(
                        buttonText = "동행 신청",
                        enabled = true,
                        onClick = { onNavigateToForm() },
                        modifier = Modifier
                            .width(370.dp)
                            .height(54.dp)
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
        is HomeUiState.SuccessDelete -> {
            LaunchedEffect(Unit) {
                onNavigateUp()
            }
        }
        is HomeUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("오류: ${(uiState as HomeUiState.Error).message}")
            }
        }
        else -> {}
    }

    if (viewModel.showDialogState.value) {
        Dialog(onDismissRequest = { viewModel.showDialogState.value = false }) {
            Box(
                modifier = Modifier
                    .size(width = 320.dp, height = 172.dp)
                    .background(Color.White, shape = RoundedCornerShape(size = 10.dp))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "정말 게시글을 삭제하시나요?",
                        style = MateTripTypographySet.title03,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        DeleteButton(
                            buttonText = "아니요",
                            enabled = true,
                            onClick = { viewModel.showDialogState.value = false },
                            containerColor = NoColor,
                            contentColor = NoTextColor
                        )
                        DeleteButton(
                            buttonText = "예",
                            enabled = true,
                            onClick = {
                                viewModel.onHomeIntent(HomeIntent.DeleteBoard(boardId))
                                viewModel.showDialogState.value = false
                            },
                            containerColor = Color.Black,
                            contentColor = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomDialog() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        var showDialogState by remember { mutableStateOf(true) }

        if (showDialogState) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .width(320.dp)
                    .height(172.dp)
                    .background(Color.White, shape = RoundedCornerShape(size = 10.dp)),
            ) {
                Text(
                    text = "정말 게시글을 삭제하시나요?",
                    style = MateTripTypographySet.title03,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 41.dp, bottom = 41.dp)
                )
                Row(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    DeleteButton(
                        buttonText = "아니요",
                        enabled = true,
                        onClick = { showDialogState = false },
                        containerColor = NoColor,
                        contentColor = NoTextColor
                    )
                    DeleteButton(
                        buttonText = "예",
                        enabled = true,
                        onClick = { showDialogState = false },
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                }
            }
        }
    }
}