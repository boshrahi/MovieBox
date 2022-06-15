package com.boshra.moviebox.factory

import com.boshra.moviebox.domain.data.CountryData
import com.boshra.moviebox.domain.data.GenreData
import com.boshra.moviebox.domain.data.LanguageData
import com.boshra.moviebox.domain.data.MovieDetailData
import com.boshra.moviebox.domain.model.MovieDetailModel
import java.util.*


object MovieDetailFactory : DataFactory(){

    fun getMovieDetailModel(id:Int): MovieDetailModel {
        return MovieDetailModel(
            budget = getRandomInt(1000000).toLong(),
            genres = listOf("Drama, Horror"),
            title = "title- $id",
            revenue = getRandomInt(1000000).toLong(),
            tagLine = "tag line - $id",
            overview = "overview - $id",
            posterUrl = "/postUrl/$id",
            productionCountries = listOf("Germany, USA"),
            spokenLanguages = listOf("English, Spanish"),
            releaseDate = "12-08-2022",
            runtime = "2h05"
        )
    }

    fun getMovieDetail(id:Long): MovieDetailData {
        val calendar = Calendar.getInstance();
        calendar.set(1994, Calendar.MAY, 20)
        val date: Date = calendar.time

        return MovieDetailData(
            budget = getRandomInt(1000000).toLong(),
            genres = getGenreList(),
            title = "title- $id",
            revenue = getRandomInt(1000000).toLong(),
            tagLine = "tag line - $id",
            overview = "overview - $id",
            posterUrl = "/postUrl/$id",
            productionCountries = getProductionCountries(),
            spokenLanguages = getSpokenLanguages(),
            releaseDate = date,
            runtime = 125
        )
    }

    private fun getSpokenLanguages(): MutableList<LanguageData> {
        return mutableListOf(
            LanguageData("English"),
            LanguageData("Persian"),
            LanguageData("Spanish")
        )
    }

    private fun getProductionCountries(): MutableList<CountryData> {
        return mutableListOf(
            CountryData("USA"),
            CountryData("Canada"),
            CountryData("Germany")
        )
    }

    private fun getGenreList(): MutableList<GenreData> {
        return mutableListOf(
            GenreData(getRandomInt(100),"Horror"),
            GenreData(getRandomInt(100),"Drama"),
            GenreData(getRandomInt(100),"Comedy")
        )
    }

}