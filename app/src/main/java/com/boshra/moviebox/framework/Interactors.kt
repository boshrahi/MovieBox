package com.boshra.moviebox.framework

import com.boshra.moviebox.interactor.GetMostPopularMovies
import com.boshra.moviebox.interactor.GetMovieDetails
import com.boshra.moviebox.interactor.GetPlayingNowMovies

data class Interactors(
    val getPlayingNowMovies : GetPlayingNowMovies,
    val getMostPopularMovies : GetMostPopularMovies,
    val getMovieDetails : GetMovieDetails,
)