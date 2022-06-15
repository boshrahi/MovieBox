package com.boshra.moviebox.domain

import com.boshra.moviebox.BuildConfig
import com.boshra.moviebox.core.ext.toReadableDate
import com.boshra.moviebox.core.ext.toReadableTime
import com.boshra.moviebox.domain.data.MovieData
import com.boshra.moviebox.domain.data.MovieDetailData
import com.boshra.moviebox.domain.model.MovieDetailModel
import com.boshra.moviebox.domain.model.MovieModel


object DataParser {

    fun toModel(data: MovieData): MovieModel {
        return MovieModel(
            data.id,
            if(data.posterUrl != null) BuildConfig.BASE_URL_IMAGE+
                    data.posterUrl
            else null,
            data.overview,
            data.title,
            data.releaseDate,
            data.voteAverage?.times(10),
        )
    }
    fun toModel(data: MovieDetailData): MovieDetailModel {
        return MovieDetailModel(
            data.budget,
            data.genres.map { it.name },
            data.title,
            data.revenue,
            data.tagLine,
            data.overview,
            if(data.posterUrl != null) BuildConfig.BASE_URL_ORIGINAL_IMAGE+
                    data.posterUrl
            else null,
            data.productionCountries.map { it.name },
            data.spokenLanguages.map { it.name },
            data.releaseDate.toReadableDate(),
            data.runtime.toReadableTime()
        )
    }
}
