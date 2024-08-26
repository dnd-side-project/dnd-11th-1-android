package com.materip.core_model.accompany_board

import com.materip.core_model.ui_model.Region
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.temporal.ChronoUnit

// 동행글 목록 조회 Data
@Serializable
data class BoardItem(
    val boardId: Int,
    val title: String,
    val region: String,
    var startDate: String,
    var endDate: String,
    val nickname: String,
    val imageUrls: List<String>
){
    fun getStartDateText(): String{
        return startDate.substring(0, 10)
    }
    fun getEndDateText(): String{
        return endDate.substring(0, 10)
    }
    fun getDuration(): String{
        val start = LocalDate.parse(startDate.substring(0,10))
        val end = LocalDate.parse(endDate.substring(0,10))
        val duration = ChronoUnit.DAYS.between(start, end)
        return "${duration}박 ${duration + 1}일"
    }
    fun getRegionText(): String{
        return when(region){
            Region.SEOUL.name -> "수도권"
            Region.GYEONGGI_INCHEON.name -> "경기·인천"
            Region.GANGWON.name -> "강원도"
            Region.CHUNGCHEONG_DAEJON_SEJONG.name -> "충청도"
            Region.GYEONGSANG_DAEGU_ULSAN.name -> "경상도"
            Region.BUSAN.name -> "부산"
            Region.JEOLLA_GWANGJU.name -> "전라도"
            Region.JEJU.name -> "제주도"
            else -> "태그 오류"
        }
    }
}