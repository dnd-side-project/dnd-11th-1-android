package com.materip.feature_mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materip.core_model.ui_model.QuizClass
import com.materip.matetrip.component.NormalTopBar
import com.materip.matetrip.component.QuizEditItem
import com.materip.matetrip.component.QuizItem
import com.materip.matetrip.icon.Icons
import com.materip.matetrip.theme.MatetripColor

@Composable
fun Quiz100Route() {
    Quiz100Screen()
}

@Composable
fun Quiz100Screen(){
    var isEditable by remember{mutableStateOf(false)}
    val dummyQuiz = remember{
        mutableStateListOf(
            QuizClass(
                title = "고기 vs 해산물",
                answer = ""
            ),
            QuizClass(
                title = "액티비티 vs 힐링",
                answer = ""
            ),
            QuizClass(
                title = "낮 vs 밤 활동성이 많은 시간",
                answer = ""
            ),
        )
    }
    val removeQuiz = remember{ mutableStateListOf<QuizClass>() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
    ){
        NormalTopBar(
            title = "백문백답 챌린지",
            menuText = if(isEditable) "삭제" else "편집",
            menuTextColor = MatetripColor.gray_06,
            titleFontWeight = FontWeight(700),
            onBackClick = { /** 뒤로가기 */ },
            onClick = {
                if(isEditable){
                    dummyQuiz.removeAll(removeQuiz)
                }
                isEditable = !isEditable
            }
        )
        Spacer(Modifier.height(10.dp))
        if (isEditable){
            EditableQuiz(
                dummyQuiz = dummyQuiz,
                removeQuiz = removeQuiz,
                addAll = {removeQuiz.addAll(dummyQuiz)}
            )
        } else {
            ReadOnlyQuiz(
                dummyQuiz = dummyQuiz,
                addDummyQuiz = {dummyQuiz.add(it)}
            )
        }
    }
}

@Composable
private fun EditableQuiz(
    dummyQuiz: SnapshotStateList<QuizClass>,
    removeQuiz: SnapshotStateList<QuizClass>,
    addAll: () -> Unit,
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "총 ${dummyQuiz.size}개",
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
                color = MatetripColor.gray_06
            )
        }
        Spacer(Modifier.height(20.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            itemsIndexed(dummyQuiz){idx, quiz ->
                QuizEditItem(
                    indexNumber = idx + 1,
                    title = quiz.title,
                    isClicked = quiz in removeQuiz,
                    onClick = {
                        if(quiz in removeQuiz){removeQuiz.remove(quiz)}
                        else {removeQuiz.add(quiz)}
                    }
                )
            }
        }
    }
}

@Composable
private fun ReadOnlyQuiz(
    dummyQuiz: List<QuizClass>,
    addDummyQuiz: (QuizClass) -> Unit,
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "총 ${dummyQuiz.size}개",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(500)
            )
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = { addDummyQuiz(QuizClass("","")) }
            ){
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(Icons.add_icon),
                    contentDescription = "Add Icon"
                )
            }
        }
        Spacer(Modifier.height(20.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            itemsIndexed(dummyQuiz){idx, quiz ->
                QuizItem(
                    indexNumber = idx + 1,
                    title = quiz.title,
                    onUpdateTitle = { quiz.title = it },
                    answer = quiz.answer,
                    onUpdateAnswer = {quiz.answer = it}
                )
            }
        }
    }
}

@Preview
@Composable
private fun Quiz100UITest(){
    Quiz100Screen()
}