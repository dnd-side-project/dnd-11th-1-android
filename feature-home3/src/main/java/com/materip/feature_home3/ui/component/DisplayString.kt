package com.materip.feature_home3.ui.component

import com.materip.core_model.accompany_board.create.Category
import com.materip.core_model.accompany_board.create.PreferredAge
import com.materip.core_model.accompany_board.create.PreferredGender
import com.materip.core_model.accompany_board.create.Region
import com.materip.core_model.ui_model.Gender

fun PreferredGender.toDisplayString(): String {
    return when (this) {
        PreferredGender.SAME -> "동일 성별"
        PreferredGender.ANY -> "상관없음"
    }
}

fun Gender.toDisplayString(): String {
    return when (this) {
        Gender.FEMALE -> "여성"
        Gender.MALE -> "남성"
    }
}

fun PreferredAge.toDisplayString(): String {
    return when (this) {
        PreferredAge.SAME -> "동일 나이대"
        PreferredAge.ANY -> "상관없음"
    }
}

fun Region.toDisplayString(): String {
    return when (this) {
        Region.SEOUL -> "서울"
        Region.GYEONGGI_INCHEON -> "경기/인천"
        Region.CHUNGCHEONG_DAEJEON_SEJONG -> "충청/대전/세종"
        Region.GANGWON -> "강원"
        Region.JEOLLA_GWANGJU -> "전라/광주"
        Region.GYEONGSANG_DAEGU_ULSAN -> "경상/대구/울산"
        Region.BUSAN -> "부산"
        Region.JEJU -> "제주"
    }
}

fun Category.toDisplayString(): String {
    return when (this) {
        Category.FULL -> "전체 동행"
        Category.PART -> "부분 동행"
        Category.LODGING -> "숙박 동행"
        Category.TOUR -> "투어 동행"
        Category.MEAL -> "식사 공유"
    }
}