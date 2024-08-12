package com.materip.feature_home.intent

sealed class HomeIntent {
    data class UpdateTitle(val title: String) : HomeIntent()
    data class UpdateContent(val content: String) : HomeIntent()
    data class UpdateTags(val tags: List<String>) : HomeIntent()
    data class UpdateRegion(val region: String) : HomeIntent()
    data class UpdateStartDate(val startDate: String) : HomeIntent()
    data class UpdateEndDate(val endDate: String) : HomeIntent()
    data class UpdateType(val type: String) : HomeIntent()
    data class UpdateAge(val age: String) : HomeIntent()
    data class UpdateGender(val gender: String) : HomeIntent()
    data class UpdateCapacity(val capacity: Int) : HomeIntent()
}