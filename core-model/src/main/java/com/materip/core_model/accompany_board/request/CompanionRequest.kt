package com.materip.core_model.accompany_board.request

import kotlinx.serialization.Serializable

// TODO: API 동행 신청할 때 보낸 시각도 추가해야 함. 그래야 알림 받을 때 시각 표시
@Serializable
data class CompanionRequest(
    val boardId: Int, // 어떤 동행글인지
    val introduce: String, // 동행 신청한 유저의 소개
    val chatLink: String // 동행 신청한 유저의 채팅 링크
) {
    // 알림 화면에서 보여줄 내용이 있는지 확인
    fun hasContent(): Boolean {
        return introduce.isNotEmpty() && chatLink.isNotEmpty()
    }
}