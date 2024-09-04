package com.materip.feature_home3.state

import com.materip.core_designsystem.icon.Logo.app_icon_60
import com.materip.core_model.accompany_board.BoardItem
import com.materip.core_model.accompany_board.BoardListResponse
import com.materip.core_model.ui_model.Region

sealed class BoardListUiState {
    data object Initial : BoardListUiState() {
        val dummyData = listOf(
            BoardItem(
                boardId = 999,
                title = "제주 여행 같이 가실 분 구합니다.",
                region = Region.SEOUL,
                startDate = "2024-01-01",
                endDate = "2024-01-04",
                nickname = "안말린망고",
                imageUrls = listOf(
                    app_icon_60.toString(),
                    app_icon_60.toString()
                )
            ),
            BoardItem(
                boardId = 998,
                title = "부산 여행 같이 가실 분 구합니다.",
                region = Region.SEOUL,
                startDate = "2024-03-01",
                endDate = "2024-03-12",
                nickname = "거부할수없는루시퍼",
                imageUrls = listOf(
                    app_icon_60.toString(),
                    app_icon_60.toString()
                )
            ),
            BoardItem(
                boardId = 997,
                title = "서울 카페 같이 가요",
                region = Region.SEOUL,
                startDate = "2024-08-24",
                endDate = "2024-08-28",
                nickname = "조용한링딩동",
                imageUrls = listOf(
                    app_icon_60.toString(),
                    app_icon_60.toString()
                )
            )
        )
    }

    data object Loading : BoardListUiState() {
        val dummyData = listOf(
            BoardItem(
                boardId = 999,
                title = "제주 여행 같이 가실 분 구합니다.",
                region = Region.SEOUL,
                startDate = "2024-01-01",
                endDate = "2024-01-04",
                nickname = "안말린망고",
                imageUrls = listOf(
                    app_icon_60.toString(),
                    app_icon_60.toString()
                )
            ),
            BoardItem(
                boardId = 998,
                title = "부산 여행 같이 가실 분 구합니다.",
                region = Region.SEOUL,
                startDate = "2024-03-01",
                endDate = "2024-03-12",
                nickname = "거부할수없는루시퍼",
                imageUrls = listOf(
                    app_icon_60.toString(),
                    app_icon_60.toString()
                )
            ),
            BoardItem(
                boardId = 997,
                title = "서울 카페 같이 가요",
                region = Region.SEOUL,
                startDate = "2024-08-24",
                endDate = "2024-08-28",
                nickname = "조용한링딩동",
                imageUrls = listOf(
                    app_icon_60.toString(),
                    app_icon_60.toString()
                )
            )
        )
    }

    data class Success(val boardList: BoardListResponse) : BoardListUiState() {
        val dummyData = listOf(
            BoardItem(
                boardId = 999,
                title = "제주 여행 같이 가실 분 구합니다.",
                region = Region.SEOUL,
                startDate = "2024-01-01",
                endDate = "2024-01-04",
                nickname = "안말린망고",
                imageUrls = listOf(
                    app_icon_60.toString(),
                    app_icon_60.toString()
                )
            ),
            BoardItem(
                boardId = 998,
                title = "부산 여행 같이 가실 분 구합니다.",
                region = Region.SEOUL,
                startDate = "2024-03-01",
                endDate = "2024-03-12",
                nickname = "거부할수없는루시퍼",
                imageUrls = listOf(
                    app_icon_60.toString(),
                    app_icon_60.toString()
                )
            ),
            BoardItem(
                boardId = 997,
                title = "서울 카페 같이 가요",
                region = Region.SEOUL,
                startDate = "2024-08-24",
                endDate = "2024-08-28",
                nickname = "조용한링딩동",
                imageUrls = listOf(
                    app_icon_60.toString(),
                    app_icon_60.toString()
                )
            )
        )
    }

    data class Error(val message: String) : BoardListUiState() {
        val dummyData = listOf(
            BoardItem(
                boardId = 999,
                title = "제주 여행 같이 가실 분 구합니다.",
                region = Region.SEOUL,
                startDate = "2024-01-01",
                endDate = "2024-01-04",
                nickname = "안말린망고",
                imageUrls = listOf(
                    app_icon_60.toString(),
                    app_icon_60.toString()
                )
            ),
            BoardItem(
                boardId = 998,
                title = "부산 여행 같이 가실 분 구합니다.",
                region = Region.SEOUL,
                startDate = "2024-03-01",
                endDate = "2024-03-12",
                nickname = "거부할수없는루시퍼",
                imageUrls = listOf(
                    app_icon_60.toString(),
                    app_icon_60.toString()
                )
            ),
            BoardItem(
                boardId = 997,
                title = "서울 카페 같이 가요",
                region = Region.SEOUL,
                startDate = "2024-08-24",
                endDate = "2024-08-28",
                nickname = "조용한링딩동",
                imageUrls = listOf(
                    app_icon_60.toString(),
                    app_icon_60.toString()
                )
            )
        )
    }
}