package com.boshra.moviebox.framework.network

import com.boshra.moviebox.domain.data.MovieDetailData
import com.boshra.moviebox.domain.data.MovieWrapperData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") lang : String? = null,
        @Query("page") page : Int? = null
    ): MovieWrapperData

    @GET("movie/popular")
    suspend fun getMostPopularMovies(
        @Query("language") lang : String? = null,
        @Query("page") page : Int? = null
    ): MovieWrapperData

    @GET("movie/{movie_id}")
    suspend fun getDetails(
        @Path("movie_id") movieId: Long,
        @Query("language") query: String? = null
    ): MovieDetailData
}