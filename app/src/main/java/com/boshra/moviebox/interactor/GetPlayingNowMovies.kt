package com.boshra.moviebox.interactor

import com.boshra.moviebox.data.MovieListRepo
import com.boshra.moviebox.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlayingNowMovies @Inject constructor(
    private val moviesRepo: MovieListRepo
) {
    operator fun invoke() : Flow<List<MovieModel>> {
        return moviesRepo.getMovies()
    }
}