package com.materip.core_model.response

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
    val gender: String,
    val birthYear: Int,
    val socialMediaUrl: String,
    val grade: String,
    val travelPreferences: List<String>,
    val travelStyle: List<String>,
    val foodPreferences: List<String>,
    val userImageUrl: List<String>
){
    fun getAgeText(): String{
        val age = LocalDate.now().year - birthYear
        val temp = when(age%10){
            in 0..4 -> "초반"
            in 5..6 -> "중반"
            else -> "후반"
        }
        val ageText = "${age/10}대 ${temp}"
        return ageText
    }

    fun getGenderText(): String{
        return if (gender == "MALE") "남성" else "여성"
    }
    fun getTags(): List<String>{
        return travelPreferences.plus(travelStyle).plus(foodPreferences)
    }
}
