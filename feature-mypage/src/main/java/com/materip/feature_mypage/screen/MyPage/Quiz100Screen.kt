package com.materip.feature_mypage.screen.MyPage

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.materip.core_common.ErrorState
import com.materip.core_designsystem.component.NormalTopBar
import com.materip.core_designsystem.component.QuizEditItem
import com.materip.core_designsystem.component.QuizItem
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.core_model.request.QnARequestDto
import com.materip.core_model.request.QnARequestItem
import com.materip.core_model.response.QnAResponseDto
import com.materip.core_model.ui_model.QuizClass
import com.materip.feature_mypage.R
import com.materip.feature_mypage.view_models.MyPage.QnAUiState
import com.materip.feature_mypage.view_models.MyPage.QnAViewModel
import com.materip.matetrip.component.DefaultLoadingComponent
import com.materip.matetrip.toast.ErrorView

@Composable
fun Quiz100Route(
    navBack: () -> Unit,
    viewModel: QnAViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errState by viewModel.errorState.collectAsStateWithLifecycle()
    Quiz100Screen(
        uiState = uiState,
        errState = errState,
        deleteQuiz = viewModel::deleteQna,
        postQuiz = viewModel::postQnA,
        navBack = navBack
    )
}

@Composable
fun Quiz100Screen(
    uiState: QnAUiState,
    errState: ErrorState,
    deleteQuiz: (ids: List<Int?>) -> Unit,
    postQuiz: (List<QnARequestItem>) -> Unit,
    navBack: () -> Unit
){
    when(uiState){
        QnAUiState.Loading -> DefaultLoadingComponent()
        QnAUiState.Error -> {
            ErrorView(errState = errState, navBack = navBack)
        }
        is QnAUiState.Success -> {
            Quiz100Content(
                quizs = uiState.data,
                onDeleteClick = deleteQuiz,
                onPostQuizs = postQuiz,
                navBack = navBack
            )
        }
    }
}

@Composable
private fun Quiz100Content(
    quizs: List<QnARequestItem>,
    onDeleteClick: (ids: List<Int?>) -> Unit,
    onPostQuizs: (List<QnARequestItem>) -> Unit,
    navBack: () -> Unit,
){
    var isEditable by remember{mutableStateOf(false)}
    val quizs = remember{mutableStateListOf(*quizs.toTypedArray())}
    val removeQuiz = remember{ mutableStateListOf<Int?>() }

    BackHandler(
        enabled = true,
        onBack = {
            onPostQuizs(quizs)
            navBack()
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
    ){
        NormalTopBar(
            title = "백문백답 챌린지",
            menuText = if(isEditable) "삭제" else "편집",
            menuTextColor = if(isEditable && removeQuiz.isNotEmpty()) Color.Black else MateTripColors.Gray_06,
            titleFontWeight = FontWeight(700),
            onBackClick = navBack,
            onClick = {
                if(isEditable){
                    quizs.filter{it.id !in removeQuiz}
                    onDeleteClick(removeQuiz)
                    removeQuiz.clear()
                }
                isEditable = !isEditable
            }
        )
        Spacer(Modifier.height(10.dp))
        if (isEditable){
            EditableQuiz(
                quizs = quizs,
                removeQuiz = removeQuiz,
                addAll = {removeQuiz.addAll(quizs.map{it.id})}
            )
        } else {
            ReadOnlyQuiz(
                quizs = quizs,
                addQuiz = {quizs.add(it)}
            )
        }
    }
}

@Composable
private fun EditableQuiz(
    quizs: SnapshotStateList<QnARequestItem>,
    removeQuiz: SnapshotStateList<Int?>,
    addAll: () -> Unit,
){
    val size by remember{mutableStateOf(quizs.size)}
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "총 ${size}개",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(500)
            )
            Text(
                modifier = Modifier.clickable{addAll()},
                text = "전체선택",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(400),
                color = MateTripColors.Gray_06
            )
        }
        Spacer(Modifier.height(20.dp))
        if (quizs.isNotEmpty()){
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ){
                itemsIndexed(quizs){idx, quiz ->
                    val isClicked = quiz.id in removeQuiz
                    QuizEditItem(
                        indexNumber = idx + 1,
                        title = quiz.questions,
                        isClicked = isClicked,
                        onClick = {
                            if(isClicked){removeQuiz.remove(quiz.id)}
                            else { if(quiz.id != null) {removeQuiz.add(quiz.id!!)} }
                        }
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "나만의 백문백답을 만들고",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontSize = 16.sp,
                        color = MateTripColors.Gray_04,
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "나와 비슷한 동행자를 찾아 보세요!",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontSize = 16.sp,
                        color = MateTripColors.Gray_04,
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun ReadOnlyQuiz(
    quizs: List<QnARequestItem>,
    addQuiz: (QnARequestItem) -> Unit,
){
    val size by remember{ mutableStateOf(quizs.size) }
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "총 ${size}개",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(500)
            )
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = { addQuiz(QnARequestItem(id = null, questions = "", answers = "")) }
            ){
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(Icons.add_icon),
                    contentDescription = "Add Icon"
                )
            }
        }
        Spacer(Modifier.height(20.dp))
        if(quizs.isNotEmpty()){
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ){
                itemsIndexed(quizs){idx, quiz ->
                    QuizItem(
                        indexNumber = idx + 1,
                        title = quiz.questions,
                        onUpdateTitle = { quiz.questions = it },
                        answer = quiz.answers,
                        onUpdateAnswer = {quiz.answers = it}
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "나만의 백문백답을 만들고",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontSize = 16.sp,
                        color = MateTripColors.Gray_04,
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "나와 비슷한 동행자를 찾아 보세요!",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontSize = 16.sp,
                        color = MateTripColors.Gray_04,
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
private fun Quiz100UITest(){
    val quizs = listOf(
        QnARequestItem(
            questions = "고기 vs 해물",
            answers = "",
            id = null,
        ),
        QnARequestItem(
            questions = "액티비티 vs 힐링",
            answers = "",
            id = null,
        ),
        QnARequestItem(
            questions = "낮 vs 밤 활동성이 많은 시간",
            answers = "",
            id = null,
        )
    )
    Quiz100Content(
        quizs = quizs,
        onDeleteClick = {},
        onPostQuizs = {},
        navBack = {}
    )
}