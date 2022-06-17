package com.boshra.moviebox.domain.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieWrapperData(
    val page: Int,
    @SerializedName("total_pages")val totalPages: Int,
    val results: MutableList<MovieData>
) : Serializable