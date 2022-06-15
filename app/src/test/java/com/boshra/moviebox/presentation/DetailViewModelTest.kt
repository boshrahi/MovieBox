package com.boshra.moviebox.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.boshra.moviebox.CoroutineTestRule
import com.boshra.moviebox.core.state.StateData
import com.boshra.moviebox.data.MovieDetailsRepo
import com.boshra.moviebox.framework.Interactors
import com.boshra.moviebox.interactor.GetMovieDetails
import com.boshra.moviebox.observeLimit
import com.boshra.moviebox.presentation.details.DetailViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailViewModel

    @Mock
    private lateinit var interactors: Interactors

    @Mock
    private lateinit var movieDetailsRepo: MovieDetailsRepo

    private lateinit var getDetails : GetMovieDetails

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = DetailViewModel(interactors)
        getDetails = GetMovieDetails(movieDetailsRepo)
    }

    @Test
    fun load_movie_detail_successfully() = runBlocking {

        Mockito.`when`(interactors.getMovieDetails).thenReturn(getDetails)

        viewModel.details.observeLimit({
            Truth.assertThat(it is StateData.Loading).isTrue()
        }, {
            Truth.assertThat(it is StateData.Success).isTrue()
        })
        val data = viewModel.getMovieDetails(123)
    }

    @Test
    fun load_movie_detail_with_error() = runBlocking {

        Mockito.`when`(interactors.getMovieDetails).thenThrow(RuntimeException())
        viewModel.details.observeLimit({
            Truth.assertThat(it is StateData.Error).isTrue()
        })
        val data = viewModel.getMovieDetails(123)
    }
}