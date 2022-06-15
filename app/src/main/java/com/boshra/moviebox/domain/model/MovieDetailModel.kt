package com.boshra.moviebox.domain.model

import java.io.Serializable

class MovieDetailModel(
    val budget: Long?,
    val genres: List<String>,
    val title: String?,
    val revenue: Long?,
    val tagLine: String?,
    val overview: String?,
    val posterUrl: String?,
    val productionCountries: List<String>,
    val spokenLanguages: List<String>,
    val releaseDate: String,
    val runtime: String,
) : Serializable