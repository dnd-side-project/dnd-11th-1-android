package com.materip.core_model.ui_model

import kotlinx.serialization.Serializable

@Serializable
data class SelectStyles(
    val travelStyle: List<TravelStyle>
)
