package com.boshra.moviebox.interactor

import com.boshra.moviebox.data.MovieDetailsRepo
import com.boshra.moviebox.domain.model.MovieDetailModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetails @Inject constructor(
    private val detailsRepo: MovieDetailsRepo
) {
    operator fun invoke(movieId : Long) : Flow<MovieDetailModel> {
        return detailsRepo.getMovieDetails(movieId)
    }
}