package com.boshra.moviebox.domain.data

import java.io.Serializable

data class MovieWrapperData(
    val page: Int,
    val results: MutableList<MovieData>
) : Serializable