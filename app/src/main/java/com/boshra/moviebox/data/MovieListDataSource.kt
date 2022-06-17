package com.boshra.moviebox.data

import androidx.paging.PagingData
import com.boshra.moviebox.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieListDataSource {
    fun getPlayingNowMovieList() : Flow<PagingData<MovieModel>>
    fun getMostPopularMovieList() : Flow<List<MovieModel>>
}