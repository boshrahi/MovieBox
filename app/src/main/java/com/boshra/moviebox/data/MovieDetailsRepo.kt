package com.boshra.moviebox.data

import javax.inject.Inject

class MovieDetailsRepo @Inject constructor(
    private val movieDetails: MovieDetailsDataSource
) {

    fun getMovieDetails(movieId : Long) = movieDetails.getMovieDetails(movieId)
}