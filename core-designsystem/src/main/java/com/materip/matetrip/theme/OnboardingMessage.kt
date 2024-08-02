package com.materip.matetrip.theme

enum class OnboardingMessage(val title: String, val message: String) {
    MESSAGE_1(
        title = "즐거운 동행을 위해\n입력해주세요!",
        message = "출생연도와 성별을 설정하면,\n나와 비슷한 사람들을 만날 수 있어요."
    ),
    MESSAGE_2(
        title = "여행 성향을\n선택해주세요!",
        message = "여행 성향을 선택하면,\n더 나은 추천을 받을 수 있어요."
    )
}