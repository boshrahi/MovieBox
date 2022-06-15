package com.boshra.moviebox.factory

import com.boshra.moviebox.BuildConfig
import com.boshra.moviebox.domain.data.MovieData
import com.boshra.moviebox.domain.data.MovieWrapperData
import java.util.*

object MovieFactory : DataFactory() {

    fun getMovieWrapperDataList(pageNumber:Int): MovieWrapperData {
        return getMovieWrapperData(pageNumber)
    }

    private fun getMovieWrapperData(pageNumber: Int): MovieWrapperData {
        return MovieWrapperData(pageNumber, getMovieDataList())
    }

    private fun getMovieDataList(): MutableList<MovieData> {
        return mutableListOf<MovieData>(
            getMovieData(),
            getMovieData(),
            getMovieData(),
            getMovieData()
        )
    }

    private fun getMovieData(): MovieData {
        return MovieData(
            id = getRandomInt(upTo = 1000).toLong(),
            posterUrl = BuildConfig.BASE_URL_IMAGE+
                    "/postUrl",
            overview = "overview",
            title = "title",
            releaseDate = Date(),
            voteAverage = getRandomFloat()
        )
    }

}