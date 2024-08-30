package com.materip.feature_mypage.pager

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.materip.core_model.accompany_board.BoardItem
import com.materip.core_model.accompany_board.Pageable
import com.materip.core_repository.repository.accompany_repository.AccompanyRepository

class AccompanyRecordPagingSource(
    val accompanyRepository: AccompanyRepository
): PagingSource<Int, BoardItem>() {
    private var total = 0
    private var cursor = 0

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, BoardItem> {
        val pageNumber = params.key ?: 0
        try{
            val response =
                accompanyRepository.getAccompanyRecords(
                    Pageable(
                        page = cursor,
                        size = 10,
                        sort = emptyList()
                    )
                )
            if(response.error != null){
                return LoadResult.Error(Exception(response.error!!.message))
            }
            val result = response.data!!
            total = result.data.size
            cursor = result.data.last().boardId
            val prevKey = if(pageNumber > 0) pageNumber - 1 else null
            val nextKey = if(result.hasNext) pageNumber + 1 else null
            return PagingSource.LoadResult.Page(
                data = result.data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BoardItem>): Int? {
        return state.anchorPosition?.let{
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}