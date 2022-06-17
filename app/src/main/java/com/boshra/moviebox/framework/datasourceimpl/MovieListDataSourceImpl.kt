package com.boshra.moviebox.framework.datasourceimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.boshra.moviebox.data.MovieListDataSource
import com.boshra.moviebox.data.MoviesPlayingNowPagingDataSource
import com.boshra.moviebox.domain.DataParser
import com.boshra.moviebox.domain.model.MovieModel
import com.boshra.moviebox.framework.DispatcherProvider
import com.boshra.moviebox.framework.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


internal class MovieListDataSourceImpl @Inject constructor(
    private val apiService : ApiService,
    private val dispatcherProvider: DispatcherProvider
) : MovieListDataSource {

    override fun getPlayingNowMovieList(): Flow<PagingData<MovieModel>> = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 2),
        pagingSourceFactory = { MoviesPlayingNowPagingDataSource(apiService) }
    ).flow

    override fun getMostPopularMovieList() = flow {
            val response = apiService.getMostPopularMovies()
            emit(response.results.map { DataParser.toModel(it) })
        }.flowOn(Dispatchers.IO)

}