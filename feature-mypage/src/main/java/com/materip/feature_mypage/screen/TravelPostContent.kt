package com.materip.feature_mypage.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materip.core_model.ui_model.TempTravelPost
import com.materip.matetrip.component.TravelPostItem

@Composable
fun TravelPostContent(){
    val dummyData = listOf(
        TempTravelPost(
            postImage = null,
            title = "슈퍼 J를 찾습니다.",
            destination = "부산",
            period = "2박3일",
            startDate = "2024.07.20",
            endDate = "2024.07.22"
        ),
        TempTravelPost(
            postImage = null,
            title = "슈퍼 J를 찾습니다.",
            destination = "부산",
            period = "2박3일",
            startDate = "2024.07.20",
            endDate = "2024.07.22"
        )
    )
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(dummyData){post ->
            TravelPostItem(
                modifier = Modifier.fillMaxWidth(),
                destination = post.destination,
                period = post.period,
                title = post.title,
                startDate = post.startDate,
                endDate = post.endDate,
                postImage = post.postImage,
                onClick = { /** 동행글 description으로 이동? */ }
            )
        }
    }
}