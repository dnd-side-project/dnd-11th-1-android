package com.materip.feature_home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.materip.core_model.CompanionPost
import com.materip.matetrip.component.MateTripSearchBar
import com.materip.matetrip.component.RegionTag
import com.materip.matetrip.icon.Badges
import com.materip.matetrip.icon.Badges.fab_add_badge
import com.materip.matetrip.icon.Logo
import com.materip.matetrip.theme.MateTripColors.Blue_02
import com.materip.matetrip.theme.MateTripColors.Blue_04
import com.materip.matetrip.theme.MateTripTypographySet


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val samplePosts = listOf(
        CompanionPost("image1.jpg", listOf("태그1", "태그2"), "닉네임", "슈퍼 J를 찾습니다!!!", "2024.07.20 ~ 2024-07-22"),
        CompanionPost("image1.jpg", listOf("태그1", "태그2"), "닉네임", "슈퍼 J를 찾습니다!!!", "2024.07.20 ~ 2024-07-22"),
        CompanionPost("image1.jpg", listOf("태그1", "태그2"), "닉네임", "슈퍼 J를 찾습니다!!!", "2024.07.20 ~ 2024-07-22"),
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White, Blue_04, Blue_02)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HomeTitle()
        MateTripSearchBar()
        CompanionLounge()
        Box(modifier = Modifier.weight(1f)) {
            PostList(samplePosts)
        }
    }
}


@Composable
fun HomeTitle() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 83.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "동행의 즐거움,\n당신의 여행을 특별하게!",
            style = MateTripTypographySet.displayLarge01,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}

@Composable
fun CompanionLounge() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "동행 라운지",
            style = MateTripTypographySet.headline06,
            modifier = Modifier.padding(start = 20.dp, end = 272.dp, bottom = 12.dp)
        )
        RegionTag(
            onClick = { /* 선택한 지역에 해당하는 동행글이 보이게 하기*/ },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PostItem(post: CompanionPost) {
    Row(
        modifier = Modifier
            .shadow(
                elevation = 10.dp,
                spotColor = Color(0x330E1537),
                ambientColor = Color(0x330E1537)
            )
            .width(360.dp)
            .height(148.dp)
            .background(color = Color.White, shape = RoundedCornerShape(size = 12.dp))
            .padding(18.dp),
        horizontalArrangement = Arrangement.spacedBy(50.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // 왼쪽 텍스트 부분
        Column(
            modifier = Modifier
                .width(155.dp)
                .height(111.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            // 태그 및 기간
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .width(108.dp)
                    .height(26.dp),
            ) {
                Text(
                    text = "부산",
                    color = Color.White,
                    style = MateTripTypographySet.title05,
                    modifier = Modifier
                        .width(42.dp)
                        .height(26.dp)
                        .background(
                            color = Color(0xFF3553F2),
                            shape = RoundedCornerShape(size = 5.dp)
                        )
                        .padding(start = 10.dp, top = 4.dp, end = 10.dp, bottom = 5.dp)
                )
                Text(
                    text = "2박3일",
                    style = MateTripTypographySet.title05,
                    modifier = Modifier
                        .width(56.dp)
                        .height(26.dp)
                        .background(
                            color = Color(0xFFEFF1FF),
                            shape = RoundedCornerShape(size = 5.dp)
                        )
                        .padding(start = 10.dp, top = 4.dp, end = 10.dp, bottom = 5.dp)
                )
            }
            Text(
                text = post.title,
                style = MateTripTypographySet.headline05,
            )
            Text(
                text = post.duration,
                style = MateTripTypographySet.title04,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = post.username,
                style = MateTripTypographySet.body06,
            )
        }

        Image(
            painter = painterResource(id = Logo.sample_image), // 이미지 리소스 ID로 교체 필요
            contentDescription = "image description", // 콘텐츠 설명
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 110.dp, height = 112.dp)
                .clip(RoundedCornerShape(10.dp))
        )
    }
}


@Composable
fun PostList(posts: List<CompanionPost>) {
    LazyColumn(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(posts) { post ->
            PostItem(post = post)
        }
    }
}

@Composable
fun FabButton(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = { navController.navigate("post") },
        containerColor = Color.Transparent,
        contentColor = Color.Transparent,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = fab_add_badge),
            contentDescription = "동행 게시글 작성 버튼"
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {

}