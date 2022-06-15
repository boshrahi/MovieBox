package com.boshra.moviebox.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.boshra.moviebox.CoroutineTestRule
import com.boshra.moviebox.core.state.StateData
import com.boshra.moviebox.data.MovieListRepo
import com.boshra.moviebox.framework.Interactors
import com.boshra.moviebox.interactor.GetMostPopularMovies
import com.boshra.moviebox.interactor.GetPlayingNowMovies
import com.boshra.moviebox.observeLimit
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MovieListViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MoviesViewModel

    @Mock
    private lateinit var interactors: Interactors

    @Mock
    private lateinit var movieListRepo: MovieListRepo

    private lateinit var getPlayingNowMovies : GetPlayingNowMovies
    private lateinit var getMostPopularMovies: GetMostPopularMovies

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = MoviesViewModel(interactors)
        getPlayingNowMovies = GetPlayingNowMovies(movieListRepo)
        getMostPopularMovies = GetMostPopularMovies(movieListRepo)
    }

    //------------------------------ Playing Now Movies

    @Test
    fun load_playing_now_movies_successfully() = runBlocking {

        Mockito.`when`(interactors.getPlayingNowMovies).thenReturn(getPlayingNowMovies)

        viewModel.playingNowMovies.observeLimit({
            Truth.assertThat(it is StateData.Loading).isTrue()
        }, {
            Truth.assertThat(it is StateData.Success).isTrue()
        })
        val data = viewModel.getPlayingNowMovies()
    }

    @Test
    fun load_playing_now_movies_with_error() = runBlocking {

        Mockito.`when`(interactors.getPlayingNowMovies).thenThrow(RuntimeException())
        viewModel.playingNowMovies.observeLimit({
            Truth.assertThat(it is StateData.Error).isTrue()
        })
        val data = viewModel.getPlayingNowMovies()
    }

    //--------------------------------- Most Popular Movies
    @Test
    fun load_most_popular_movies_successfully() = runBlocking {

        Mockito.`when`(interactors.getMostPopularMovies).thenReturn(getMostPopularMovies)

        viewModel.mostPopularMovies.observeLimit({
            Truth.assertThat(it is StateData.Loading).isTrue()
        }, {
            Truth.assertThat(it is StateData.Success).isTrue()
        })
        val data = viewModel.getMostPopularMovies()
    }

    @Test
    fun load_most_popular_movies_with_error() = runBlocking {

        Mockito.`when`(interactors.getMostPopularMovies).thenThrow(RuntimeException())
        viewModel.mostPopularMovies.observeLimit({
            Truth.assertThat(it is StateData.Error).isTrue()
        })
        val data = viewModel.getMostPopularMovies()
    }

}