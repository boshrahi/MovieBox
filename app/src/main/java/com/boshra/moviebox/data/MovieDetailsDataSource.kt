package com.boshra.moviebox.data

import com.boshra.moviebox.domain.model.MovieDetailModel
import kotlinx.coroutines.flow.Flow

interface MovieDetailsDataSource {
    fun getMovieDetails(movieId: Long) : Flow<MovieDetailModel>

}