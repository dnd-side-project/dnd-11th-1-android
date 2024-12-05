package com.materip.feature_home3.intent

import com.materip.core_model.accompany_board.search.QueryRequestDto
import com.materip.core_model.request.PagingRequestDto

sealed class BoardListIntent {
    data class LoadBoardList(val pagingRequestDto: PagingRequestDto) : BoardListIntent()
    data class SearchBoardList(val query: QueryRequestDto) : BoardListIntent()
    data class FilterBoardList(val region: String?, val started: Boolean, val recruited: Boolean) : BoardListIntent()
    data class UpdateFilter(val region: String?, val started: Boolean, val recruited: Boolean) : BoardListIntent()
    data class UpdateSelectedOption(val option: String) : BoardListIntent()
}