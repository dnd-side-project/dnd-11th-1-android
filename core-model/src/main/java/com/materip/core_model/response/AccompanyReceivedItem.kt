package com.materip.core_model.response

import com.materip.core_model.ui_model.Gender
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class AccompanyReceivedItem(
    val requestId: Int,
    val userId: Int,
    val nickname: String,
    val provider: String,
    val profileImageUrl: String?,
    val description: String?,
    val gender: Gender,
    val birthYear: Int,
    val socialMediaUrl: String,
    val grade: String,
    val travelPreferences: List<String>,
    val travelStyle: List<String>,
    val foodPreferences: List<String>,
    val userImageUrl: List<String>
){
    fun getTags(): List<String>{
        return travelPreferences.plus(travelStyle).plus(foodPreferences)
    }
}
