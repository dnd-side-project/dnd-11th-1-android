package com.materip.feature_home3.ui.profile_content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.theme.MateTripColors.Gray_04
import com.materip.core_designsystem.theme.MateTripTypographySet

//import com.materip.feature_home3.viewModel.ReviewViewModel

@Composable
fun ReviewContent(
//    viewModel: ReviewViewModel = hiltViewModel()
) {
    ReviewContentInitial()
//    val uiState by viewModel.uiState.collectAsState()

//    when (uiState) {
//        is ReviewUiState.Loading -> {
//            CircularProgressIndicator()
//        }
//
//        is ReviewUiState.Success -> {
//            // 동행 후기 api 완료되면 그때 다른 UI로 변경
//            ReviewContentInitial()
//        }
//
//        is ReviewUiState.Error -> {
//            Text("오류: ${(uiState as ReviewUiState.Error).message}")
//        }
//
//        ReviewUiState.Initial -> {
//            ReviewContentInitial()
//        }
//    }
}


// ReviewContent의 상태가 Initial일 때의 UI (후기가 없을 때와 동일)
@Composable
fun ReviewContentInitial() {
    Column(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(180.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "받은 동행 평가", style = MateTripTypographySet.headline05)
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painter = painterResource(id = Icons.enter_24_icon),
                        contentDescription = "받은 동행 평가 더보기"
                    )
                }
            }
            Spacer(modifier = Modifier.height(240.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(180.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "받은 동행 후기", style = MateTripTypographySet.headline05)
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painter = painterResource(id = Icons.enter_24_icon),
                        contentDescription = "받은 동행 후기 더보기"
                    )
                }
            }
            Spacer(modifier = Modifier.height(170.dp))
        }
        Text(
            text = "아직 받은 동행 평가와 후기가 없어요.",
            style = MateTripTypographySet.body03,
            color = Gray_04,
            modifier = Modifier.height(24.dp)
        )
        Spacer(modifier = Modifier.height(170.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewContentPreview() {
    ReviewContentInitial()
}