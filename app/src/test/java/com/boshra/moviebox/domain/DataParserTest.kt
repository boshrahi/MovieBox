package com.boshra.moviebox.domain

import com.boshra.moviebox.BuildConfig
import com.boshra.moviebox.factory.MovieDetailFactory
import com.boshra.moviebox.factory.MovieFactory
import com.google.common.truth.Truth
import org.junit.Test

class DataParserTest {

    @Test
    fun get_movie_data_and_convert_to_movie_model(){
        val moviesData = MovieFactory.getMovieWrapperDataList(1)
        val movieData = moviesData.results[0]
        val movieModel = DataParser.toModel(movieData)
        Truth.assertThat(movieModel.id).isEqualTo(movieData.id)
        Truth.assertThat(movieModel.overview).isEqualTo(movieData.overview)
        Truth.assertThat(movieModel.posterUrl).contains(BuildConfig.BASE_URL_IMAGE)
    }

    @Test
    fun get_detail_data_and_convert_to_detail_model(){
        val detailData = MovieDetailFactory.getMovieDetail(1)
        val detailModel = DataParser.toModel(detailData)
        Truth.assertThat(detailModel.title).isEqualTo(detailData.title)
        Truth.assertThat(detailModel.overview).isEqualTo(detailData.overview)
        Truth.assertThat(detailModel.posterUrl).contains(BuildConfig.BASE_URL_ORIGINAL_IMAGE)
        Truth.assertThat(detailModel.runtime).isEqualTo("2h05")
        Truth.assertThat(detailModel.releaseDate).isEqualTo("May, 20 1994")
    }
}