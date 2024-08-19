package com.materip.core_model.ui_model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val birthYear: String,
    val gender: String
)
