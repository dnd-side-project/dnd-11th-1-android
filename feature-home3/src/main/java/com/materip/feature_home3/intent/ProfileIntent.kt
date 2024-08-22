package com.materip.feature_home3.intent

sealed class ProfileIntent {
    data object GetProfileDetails : ProfileIntent()
}
