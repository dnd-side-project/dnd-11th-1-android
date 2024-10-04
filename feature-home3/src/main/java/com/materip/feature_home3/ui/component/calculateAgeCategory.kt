package com.materip.feature_home3.ui.component

fun calculateAgeCategory(birthYear: Int): String {
    val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
    val age = currentYear - birthYear

    if (age < 10 || age >= 60) {
        return "기타"
    }

    val decade = (age / 10) * 10
    val ageInDecade = age % 10

    val ageGroup = when (ageInDecade) {
        in 0..3 -> "초반"
        in 4..6 -> "중반"
        in 7..9 -> "후반"
        else -> "기타"
    }

    return "${decade}대 $ageGroup"
}
