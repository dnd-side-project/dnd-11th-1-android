package com.materip.core_model.response

import com.materip.core_model.ui_model.PersonalityType
import com.materip.core_model.ui_model.TravelPreferenceForReview
import com.materip.core_model.ui_model.TravelStyleForReview
import kotlinx.serialization.Serializable

@Serializable
data class EvaluationItem(
    val type: String,
    val count: Int
){
    fun getKoreanType(): String{
        return when(type){
            PersonalityType.KIND.name -> "친절해요"
            PersonalityType.BRIGHT.name -> "밝아요"
            PersonalityType.FUN.name -> "재밌어요"
            PersonalityType.COMFORTABLE.name -> "편안해요"
            PersonalityType.TRUSTWORTHY.name -> "신뢰가 가요"
            PersonalityType.POSITIVE.name -> "긍정적이에요"
            PersonalityType.SENSE.name -> "센스있어요"
            PersonalityType.EMOTIONAL.name -> "감성적이에요"
            PersonalityType.RATIONAL.name -> "이성적이에요"
            PersonalityType.PASSIONATE.name -> "열정적이에요"
            PersonalityType.GOOD_ATTACHMENT.name -> "붙임성이 좋아요"
            TravelPreferenceForReview.PLANNED.name -> "계획적이에요"
            TravelPreferenceForReview.SPONTANEOUS.name -> "즉흥적이에요"
            TravelPreferenceForReview.PUBLIC_MONEY_CONVENIENT.name -> "공금이 편해요"
            TravelPreferenceForReview.DUTCH_PAY.name -> "더치페이 해요"
            TravelPreferenceForReview.DILIGENT.name -> "부지런해요"
            TravelPreferenceForReview.RELAXED.name -> "느긋해요"
            TravelStyleForReview.LIKE_RESTAURANTS.name -> "맛집을 좋아해요"
            TravelStyleForReview.DOES_NOT_HAVE_TO_BE_RESTAURANT.name -> "맛집이 아니어도 돼요"
            TravelStyleForReview.PREFER_HOTPLE.name -> "핫플을 선호해요"
            TravelStyleForReview.LIKE_QUIET_PLACES.name -> "고즈넉한 곳을 좋아해요"
            TravelStyleForReview.LIKE_TAKING_PICTURES.name -> "사진 찍는 걸 좋아해요"
            TravelStyleForReview.PREFER_TOURIST_DESTINATIONS.name -> "관광지를 선호해요"
            TravelStyleForReview.PREFER_HEALING.name -> "힐링을 선호해요"
            TravelStyleForReview.ENJOY_ACTIVITY.name -> "액티비티를 즐겨요"
            TravelStyleForReview.LIKE_SHOPPING.name -> "쇼핑을 좋아해요"
            TravelStyleForReview.LIKE_CAFES.name -> "카페를 좋아해요"
            else -> "Tag Rending Error"
        }
    }
}