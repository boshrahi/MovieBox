package com.boshra.moviebox.data.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams.Refresh
import androidx.paging.PagingSource.LoadResult.Page
import com.boshra.moviebox.CoroutineTestRule
import com.boshra.moviebox.data.MoviesPlayingNowPagingDataSource
import com.boshra.moviebox.domain.DataParser
import com.boshra.moviebox.domain.model.MovieModel
import com.boshra.moviebox.factory.MovieFactory
import com.boshra.moviebox.framework.network.ApiService
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import retrofit2.HttpException
import retrofit2.Response

class MoviesPagingDataSourceTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private val fakeMovies = listOf(
        MovieFactory.getMovieWrapperDataList(1),
        MovieFactory.getMovieWrapperDataList(2),
        MovieFactory.getMovieWrapperDataList(3)
    )
    private val apiService : ApiService = Mockito.spy(ApiService::class.java)

    @Test
    fun load_first_page_movie_data_source() = runBlocking {
        val models = fakeMovies[0].results.map { DataParser.toModel(it) }
        given(apiService.getNowPlayingMovies("en-US",1)).willReturn(fakeMovies[0])
        val pagingSource = MoviesPlayingNowPagingDataSource(apiService)
        val loadedPage = pagingSource.load(
            Refresh(
                key = null,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assert(Page(
                data = models,
                prevKey = null,
                nextKey = 2) == loadedPage
        )
    }

    @Test
    fun movies_paging_source_load_failure_http_error_404() = runBlocking {
        val error = HttpException(Response.error<HttpException>
            (404, ResponseBody.create(null,"Not Found")))
        given(apiService.getNowPlayingMovies("en-US",0)).willThrow(error)
        val expectedResult = PagingSource.LoadResult.Error<Int, MovieModel>(error)
        val pagingSource = MoviesPlayingNowPagingDataSource(apiService)
        assertEquals(
            expectedResult, pagingSource.load(
                Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun movies_paging_source_load_failure_received_null() = runBlocking {
        given(apiService.getNowPlayingMovies("en-US",0)).willReturn(null)
        val expectedResult = PagingSource.LoadResult.Error<Int, MovieModel>(NullPointerException())
        val pagingSource = MoviesPlayingNowPagingDataSource(apiService)
        assertEquals(
            expectedResult.toString(), pagingSource.load(
                Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            ).toString()
        )
    }
}