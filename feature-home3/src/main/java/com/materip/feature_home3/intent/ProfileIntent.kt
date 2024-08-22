package com.materip.feature_home3.intent

sealed class ProfileIntent {
    data object GetProfileDetails : ProfileIntent()
    data class GetUserId(val boardId: Int) : ProfileIntent()
    data class GetNickname(val userId: Int) : ProfileIntent()
}
