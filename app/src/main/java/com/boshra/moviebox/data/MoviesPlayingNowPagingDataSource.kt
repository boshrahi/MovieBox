package com.boshra.moviebox.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.boshra.moviebox.domain.DataParser
import com.boshra.moviebox.domain.model.MovieModel
import com.boshra.moviebox.framework.network.ApiService
import javax.inject.Inject

class MoviesPlayingNowPagingDataSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, MovieModel>(){

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        val pageNumber = params.key ?: 1
        return try {
            val response = apiService.getNowPlayingMovies(lang = "en-US", page = pageNumber)

            var nextPageNumber: Int? = null
            if (pageNumber < response.totalPages) {
                nextPageNumber = pageNumber + 1
            }

            LoadResult.Page(
                data = response.results.map { DataParser.toModel(it) },
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}