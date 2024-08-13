package com.materip.core_model.ui_model

data class TempHumanClass(
    val nickname: String,
    val level: Int,
    val profileUrl: String,
    val tags: List<String>,
    val age: String
)

data class TempTravelPost(
    val postImage: String?,
    val title: String,
    val destination: String,
    val period: String,
    val startDate: String,
    val endDate: String,
)

data class TempHumanDescClass(
    val nickname: String,
    val level: Int,
    val profileUrl: String,
    val interests: List<String>,
    val styles: List<String>,
    val foodPreferences: List<String>,
    val age: String,
    val gender: String,
    val introduction: String,
    val authenticatorType: String,
    val images: List<String>
)