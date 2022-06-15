package com.boshra.moviebox.data

import javax.inject.Inject


class MovieListRepo @Inject constructor(
    private val moviesDataSource: MovieListDataSource
) {

    fun getMovies() = moviesDataSource.getPlayingNowMovieList()
    fun getMostPopularMovies() = moviesDataSource.getMostPopularMovieList()
}