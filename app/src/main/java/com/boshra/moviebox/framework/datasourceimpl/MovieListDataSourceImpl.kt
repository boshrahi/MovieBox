package com.boshra.moviebox.framework.datasourceimpl

import com.boshra.moviebox.data.MovieListDataSource
import com.boshra.moviebox.domain.DataParser
import com.boshra.moviebox.framework.DispatcherProvider
import com.boshra.moviebox.framework.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


internal class MovieListDataSourceImpl @Inject constructor(
    private val apiService : ApiService,
    private val dispatcherProvider: DispatcherProvider
) : MovieListDataSource {

    override fun getPlayingNowMovieList() = flow {
            val response = apiService.getNowPlayingMovies()
            emit(response.results.map { DataParser.toModel(it) })
        }.flowOn(dispatcherProvider.IO)

    override fun getMostPopularMovieList() = flow {
            val response = apiService.getMostPopularMovies()
            emit(response.results.map { DataParser.toModel(it) })
        }.flowOn(Dispatchers.IO)

}