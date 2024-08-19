package com.materip.feature_home.intent

sealed class FormIntent {
    data class UpdateIntroduce(val introduce: String) : FormIntent()
    data class UpdateChatLink(val chatLink: String) : FormIntent()
    data class SubmitCompanionRequest(val boardId: Int) : FormIntent()
}