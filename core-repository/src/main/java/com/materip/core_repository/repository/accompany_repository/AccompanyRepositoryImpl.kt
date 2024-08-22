package com.materip.core_repository.repository.accompany_repository

import com.materip.core_common.ResultResponse
import com.materip.core_datastore.accompany_datastore.AccompanyDataStore
import com.materip.core_model.accompany_board.BoardItem
import com.materip.core_model.accompany_board.Pageable
import com.materip.core_model.request.AccompanyApplicationResponseDto
import com.materip.core_model.response.AccompanyReceivedItem
import com.materip.core_model.response.DefaultGetAccompanyResponseDto
import javax.inject.Inject

class AccompanyRepositoryImpl @Inject constructor(
    private val accompanyDataStore: AccompanyDataStore
): AccompanyRepository{
    override suspend fun getAccompanyApplication(
        id: Int
    ): ResultResponse<AccompanyApplicationResponseDto> {
        return accompanyDataStore.getAccompanyApplication(id)
    }

    override suspend fun getAccompanySend(pageable: Pageable): ResultResponse<DefaultGetAccompanyResponseDto<BoardItem>> {
        return accompanyDataStore.getAccompanySend(pageable)
    }

    override suspend fun getAccompanyReceived(pageable: Pageable): ResultResponse<DefaultGetAccompanyResponseDto<AccompanyReceivedItem>> {
        return accompanyDataStore.getAccompanyReceived(pageable)
    }
}