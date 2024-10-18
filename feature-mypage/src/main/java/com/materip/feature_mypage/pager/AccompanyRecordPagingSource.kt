package com.materip.feature_mypage.pager

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.materip.core_model.request.PagingRequestDto
import com.materip.core_model.response.BoardItemWithReviewId
import com.materip.core_repository.repository.accompany_repository.AccompanyRepository

class AccompanyRecordPagingSource(
    val accompanyRepository: AccompanyRepository
): PagingSource<Int, BoardItemWithReviewId>() {
    private var total = 0
    private var cursor: String? = null

    override suspend fun load(params: PagingSource.LoadParams<Int>): LoadResult<Int, BoardItemWithReviewId> {
        val pageNumber = params.key ?: 0
        try{
            val response =
                accompanyRepository.postAccompanyRecords(
                    PagingRequestDto(cursor = cursor)
                )
            if(response.error != null){
                return LoadResult.Error(Exception(response.error!!.message))
            }
            val result = response.data!!
            total = result.data.size
            cursor = result.cursor
            val prevKey = if(pageNumber > 0) pageNumber - 1 else null
            val nextKey = if(result.hasNext) pageNumber + 1 else null
            return LoadResult.Page(
                data = result.data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BoardItemWithReviewId>): Int? {
        return state.anchorPosition?.let{
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}