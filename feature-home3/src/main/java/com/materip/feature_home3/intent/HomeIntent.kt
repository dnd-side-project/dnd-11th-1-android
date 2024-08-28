package com.materip.feature_home3.intent

sealed class HomeIntent {
    data class LoadBoardDetail(val boardId: Int) : HomeIntent()
    data class DeleteBoard(val boardId: Int) : HomeIntent()
}