package com.materip.core_model.accompany_board

import kotlinx.serialization.Serializable

@Serializable
data class Pageable(
    val page: Int, // 현재 페이지
    val size: Int, // 페이지 사이즈
    val sort: List<String> // 정렬
)
