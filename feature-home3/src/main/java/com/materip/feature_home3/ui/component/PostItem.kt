package com.materip.feature_home3.ui.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.materip.core_common.toDisplayString
import com.materip.core_designsystem.icon.Logo
import com.materip.core_designsystem.theme.MateTripTypographySet
import com.materip.core_model.accompany_board.all.BoardItem

@Composable
fun PostItem(
    boardItem: BoardItem,
    onBoardItemClick: (Int) -> Unit
) {
    val duration = calculateDuration(boardItem.startDate, boardItem.endDate)

    val firstImage: Any = if (boardItem.imageUrls.isNotEmpty()) {
        val imageUrl = boardItem.imageUrls.first()
        if (imageUrl.startsWith("http")) {
            imageUrl
        } else if (imageUrl.startsWith("content://") || imageUrl.startsWith("file://")) {
            Uri.parse(imageUrl)
        } else {
            Logo.default_image.toString()
        }
    } else {
        Logo.default_image
    }

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
            .padding(18.dp)
            .clickable { onBoardItemClick(boardItem.boardId) },
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
                modifier = Modifier.height(26.dp),
            ) {
                Text(
                    text = boardItem.region.toDisplayString(),
                    color = Color.White,
                    style = MateTripTypographySet.title05,
                    modifier = Modifier
                        .height(26.dp)
                        .background(
                            color = Color(0xFF3553F2),
                            shape = RoundedCornerShape(size = 5.dp)
                        )
                        .padding(start = 10.dp, top = 4.dp, end = 10.dp, bottom = 5.dp)
                )
                Text(
                    text = duration,
                    style = MateTripTypographySet.title05,
                    modifier = Modifier
                        .height(26.dp)
                        .background(
                            color = Color(0xFFEFF1FF),
                            shape = RoundedCornerShape(size = 5.dp)
                        )
                        .padding(start = 10.dp, top = 4.dp, end = 10.dp, bottom = 5.dp)
                )
            }
            Text(
                text = boardItem.title,
                style = MateTripTypographySet.headline05,
            )
            Text(
                text = "${boardItem.getStartDateText()} ~ ${boardItem.getEndDateText()}",
                style = MateTripTypographySet.title04,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = boardItem.nickname,
                style = MateTripTypographySet.body06,
            )
        }

        Image(
            painter = rememberAsyncImagePainter(firstImage),
            contentDescription = "image description",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 110.dp, height = 112.dp)
                .clip(RoundedCornerShape(10.dp))
        )
    }
}