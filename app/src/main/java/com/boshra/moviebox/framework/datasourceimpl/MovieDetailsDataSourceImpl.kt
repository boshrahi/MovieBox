package com.boshra.moviebox.framework.datasourceimpl

import com.boshra.moviebox.data.MovieDetailsDataSource
import com.boshra.moviebox.domain.DataParser
import com.boshra.moviebox.domain.model.MovieDetailModel
import com.boshra.moviebox.framework.DispatcherProvider
import com.boshra.moviebox.framework.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class MovieDetailsDataSourceImpl @Inject constructor(
    private val apiService : ApiService,
    private val dispatcherProvider: DispatcherProvider
) : MovieDetailsDataSource{

    override fun getMovieDetails(movieId: Long): Flow<MovieDetailModel> = flow {
        val response = apiService.getDetails(movieId)
        emit(DataParser.toModel(response))
    }.flowOn(dispatcherProvider.IO)
}