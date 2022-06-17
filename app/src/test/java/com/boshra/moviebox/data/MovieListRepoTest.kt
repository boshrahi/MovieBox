package com.boshra.moviebox.data

import com.boshra.moviebox.domain.DataParser
import com.boshra.moviebox.factory.MovieFactory
import com.boshra.moviebox.framework.DispatcherProvider
import com.boshra.moviebox.framework.datasourceimpl.MovieListDataSourceImpl
import com.boshra.moviebox.framework.network.ApiService
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MovieListRepoTest {

    @Mock
    private lateinit var api: ApiService

    private val repository: MovieListRepo by lazy {
        MovieListRepo(MovieListDataSourceImpl(api, DispatcherProvider()))
    }

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun get_playing_now_movies_successfully() = runBlocking {
        val movies = MovieFactory.getMovieWrapperDataList(1)

        `when`(api.getNowPlayingMovies()).thenReturn(movies)
        val movieModels = movies.results.map{DataParser.toModel(it)}

        repository.getPlayingNowMovies().collect{
            Truth.assertThat(it).isEqualTo(movieModels)
        }
    }

    @Test
    fun get_playing_now_movies_failed() = runBlocking {
        val meg = "unauthorized"
        `when`(api.getNowPlayingMovies()).thenThrow(RuntimeException())
        try {
            val flow = repository.getPlayingNowMovies()
        }catch (e: RuntimeException){
            Truth.assertThat(e).hasMessageThat().isEqualTo(meg)
        }
    }

    @Test
    fun get_most_popular_movies_successfully() = runBlocking {
        val movies = MovieFactory.getMovieWrapperDataList(1)

        `when`(api.getMostPopularMovies()).thenReturn(movies)
        val movieModels = movies.results.map{DataParser.toModel(it)}

        repository.getMostPopularMovies().collect{
            Truth.assertThat(it).isEqualTo(movieModels)
        }
    }

    @Test
    fun get_most_popular_movies_failed() = runBlocking {
        val meg = "unauthorized"
        `when`(api.getMostPopularMovies()).thenThrow(RuntimeException())
        try {
            val flow = repository.getMostPopularMovies()
        }catch (e: RuntimeException){
            Truth.assertThat(e).hasMessageThat().isEqualTo(meg)
        }
    }

}