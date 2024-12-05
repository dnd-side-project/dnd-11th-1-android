package com.materip.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class DeleteAccountRequestDto(
    val reason: String
)
