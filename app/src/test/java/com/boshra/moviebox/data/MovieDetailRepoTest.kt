package com.boshra.moviebox.data

import com.boshra.moviebox.domain.DataParser
import com.boshra.moviebox.factory.MovieDetailFactory
import com.boshra.moviebox.framework.DispatcherProvider
import com.boshra.moviebox.framework.datasourceimpl.MovieDetailsDataSourceImpl
import com.boshra.moviebox.framework.network.ApiService
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MovieDetailRepoTest {

    @Mock
    private lateinit var api: ApiService

    private val repository: MovieDetailsRepo by lazy {
        MovieDetailsRepo(MovieDetailsDataSourceImpl(api, DispatcherProvider()))
    }

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun get_details_movie_successfully() = runBlocking {
        val movieId : Long = 123
        val detail = MovieDetailFactory.getMovieDetail(movieId)

        Mockito.`when`(api.getDetails(movieId)).thenReturn(detail)
        val detailsModel = DataParser.toModel(detail)

        repository.getMovieDetails(movieId).collect{
            Truth.assertThat(it.title).isEqualTo(detailsModel.title)
            Truth.assertThat(it.posterUrl).isEqualTo(detailsModel.posterUrl)
            Truth.assertThat(it.budget).isEqualTo(detailsModel.budget)
            Truth.assertThat(it.overview).isEqualTo(detailsModel.overview)
            Truth.assertThat(it.releaseDate).isEqualTo(detailsModel.releaseDate)
        }
    }

    @Test
    fun get_details_movie_failed() = runBlocking {
        val movieId : Long = 123
        val meg = "Network Error"
        Mockito.`when`(api.getDetails(movieId)).thenThrow(RuntimeException())
        try {
            val flow = repository.getMovieDetails(movieId)
        }catch (e: RuntimeException){
            Truth.assertThat(e).hasMessageThat().isEqualTo(meg)
        }
    }
}