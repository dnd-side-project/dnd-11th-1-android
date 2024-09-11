package com.materip.feature_home3.intent

import com.materip.core_model.request.PagingRequestDto

sealed class BoardListIntent {
    data class LoadBoardList(val pagingRequestDto: PagingRequestDto) : BoardListIntent()
    data class SearchBoardList(val query: String) : BoardListIntent()
}