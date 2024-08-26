package com.materip.core_datastore.accompany_datastore

import com.materip.core_common.ResultResponse
import com.materip.core_model.accompany_board.BoardItem
import com.materip.core_model.accompany_board.Pageable
import com.materip.core_model.request.AccompanyApplicationResponseDto
import com.materip.core_model.response.AccompanyReceivedItem
import com.materip.core_model.response.DefaultGetAccompanyResponseDto

interface AccompanyDataStore {
    suspend fun getAccompanyApplication(id: Int): ResultResponse<AccompanyApplicationResponseDto>
    suspend fun getAccompanySend(pageable: Pageable): ResultResponse<DefaultGetAccompanyResponseDto<BoardItem>>
    suspend fun getAccompanyReceived(pageable: Pageable): ResultResponse<DefaultGetAccompanyResponseDto<AccompanyReceivedItem>>
    suspend fun getAccompanyRecords(pageable: Pageable): ResultResponse<DefaultGetAccompanyResponseDto<BoardItem>>
}