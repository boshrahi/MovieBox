package com.boshra.moviebox.domain.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class MovieDetailData(
    val budget: Long?,
    val genres: MutableList<GenreData>,
    val title: String?,
    val revenue: Long?,
    @SerializedName("tagline") val tagLine: String?,
    val overview: String?,
    @SerializedName("poster_path") val posterUrl: String?,
    @SerializedName("production_countries") val productionCountries: MutableList<CountryData>,
    @SerializedName("spoken_languages") val spokenLanguages: MutableList<LanguageData>,
    @SerializedName("release_date") val releaseDate: Date,
    val runtime: Int,
) : Serializable