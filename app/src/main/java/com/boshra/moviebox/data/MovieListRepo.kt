package com.boshra.moviebox.data

import javax.inject.Inject


class MovieListRepo @Inject constructor(
    private val moviesDataSource: MovieListDataSource
) {

    fun getPlayingNowMovies() = moviesDataSource.getPlayingNowMovieList()
    fun getMostPopularMovies() = moviesDataSource.getMostPopularMovieList()
}