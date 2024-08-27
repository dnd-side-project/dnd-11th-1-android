package com.materip.core_model.accompany_board.request

import kotlinx.serialization.Serializable

@Serializable
data class CompanionRequest(
    val boardId: Int, // 어떤 동행글인지
    val introduce: String, // 동행 신청한 유저의 소개
    val chatLink: String // 동행 신청한 유저의 채팅 링크
) {
    fun hasContent(): Boolean {
        return introduce.isNotEmpty() && chatLink.isNotEmpty()
    }
}