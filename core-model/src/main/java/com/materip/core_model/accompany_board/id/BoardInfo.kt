package com.materip.core_model.accompany_board.id

import com.materip.core_model.accompany_board.create.Category
import com.materip.core_model.accompany_board.create.PreferredAge
import com.materip.core_model.accompany_board.create.PreferredGender
import com.materip.core_model.accompany_board.create.Region
import kotlinx.serialization.Serializable

// 동행글 상세 조회 BoardInfo
@Serializable
data class BoardInfo(
    val boardId: Int, // 게시글 ID
    val title: String, // 게시글 제목
    val content: String, // 게시글 내용
    val imageUrls: List<String>, // 이미지 URI 리스트
    val region: Region, // 여행 지역
    val startDate: String, // 여행 시작 날짜
    val endDate: String, // 여행 종료 날짜
    val headCount: Int,  // 동행원 수
    val capacity: Int, // 최대 인원 수
    val categories: List<Category>, // 여행 카테고리
    val tagNames: List<String>, // 태그 리스트
    val preferredAge: PreferredAge, // 동행 선호 나이
    val preferredGender: PreferredGender, // 동행 선호 성별
)