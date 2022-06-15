package com.boshra.moviebox.domain.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class MovieData(
    val id: Long,
    @SerializedName("poster_path")
    val posterUrl: String?,
    val overview: String?,
    val title: String,
    @SerializedName("release_date")
    val releaseDate: Date?,
    @SerializedName("vote_average")
    val voteAverage: Float?,
) : Serializable
