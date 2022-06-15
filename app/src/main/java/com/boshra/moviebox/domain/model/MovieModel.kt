package com.boshra.moviebox.domain.model

import java.io.Serializable
import java.util.*

class MovieModel(
    val id: Long,
    val posterUrl: String?,
    val overview: String?,
    val title: String,
    val releaseDate: Date?,
    val rating: Float?,
):Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as MovieModel
        if (this.id == other.id
            && this.posterUrl == other.posterUrl
            && this.overview == other.overview
            && this.title == other.title
            && this.releaseDate == other.releaseDate
            && this.rating == other.rating) return true
        return false
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (posterUrl?.hashCode() ?: 0)
        result = 31 * result + (overview?.hashCode() ?: 0)
        result = 31 * result + title.hashCode()
        result = 31 * result + (releaseDate?.hashCode() ?: 0)
        result = 31 * result + (rating?.hashCode() ?: 0)
        return result
    }
}