package com.materip.core_model.accompany_board.create

import kotlinx.serialization.Serializable

// 동행글 생성 BoardRequestDto
@Serializable
data class BoardRequestDto(
    val title: String,
    val content: String,
    val region: Region,
    val startDate: String,
    val endDate: String,
    val capacity: Int,
    val boardStatus: BoardStatus,
    val categories: List<Category>,
    val preferredAge: PreferredAge,
    val preferredGender: PreferredGender,
    val imageUrls: List<String>,
    val tagNames: List<String>
)

enum class Region {
    SEOUL,
    GYEONGGI_INCHEON,
    CHUNGCHEONG_DAEJEON_SEJONG,
    GANGWON,
    JEOLLA_GWANGJU,
    GYEONGSANG_DAEGU_ULSAN,
    BUSAN,
    JEJU
}

enum class BoardStatus {
    RECRUITING,
    RECRUITMENT_COMPLETED,
    REMOVED
}

enum class Category {
    FULL, PART, LODGING, TOUR, MEAL
}

enum class PreferredAge {
    SAME, ANY
}

enum class PreferredGender {
    SAME, ANY
}