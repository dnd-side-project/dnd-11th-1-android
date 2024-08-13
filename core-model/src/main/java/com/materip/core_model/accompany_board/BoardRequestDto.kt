package com.materip.core_model.accompany_board

import kotlinx.serialization.Serializable

// 동행글 생성 BoardRequestDto
@Serializable
data class BoardRequestDto(
    val title: String,
    val content: String,
    val region: String,
    val startDate: String,
    val endDate: String,
    val capacity: Int,
    val category: String,
    val preferredAge: String,
    val preferredGender: String,
    val imageUrls: List<String>,
    val tagNames: List<String>
)

// 임시로 만든 데이터 클래스
data class CompanionPost(
    val image: String,
    val tags: List<String>,
    val username: String,
    val title: String,
    val duration: String
)