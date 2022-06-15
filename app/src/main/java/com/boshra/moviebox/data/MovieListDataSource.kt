package com.boshra.moviebox.data

import com.boshra.moviebox.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieListDataSource {
    fun getPlayingNowMovieList() : Flow<List<MovieModel>>
    fun getMostPopularMovieList() : Flow<List<MovieModel>>
}