package com.materip.feature_home3.intent

import com.materip.core_model.accompany_board.Pageable

sealed class BoardListIntent {
    data class LoadBoardList(val pageable: Pageable) : BoardListIntent()
}