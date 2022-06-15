package com.boshra.moviebox.core.di

import com.boshra.moviebox.data.MovieDetailsDataSource
import com.boshra.moviebox.data.MovieDetailsRepo
import com.boshra.moviebox.data.MovieListDataSource
import com.boshra.moviebox.data.MovieListRepo
import com.boshra.moviebox.framework.DispatcherProvider
import com.boshra.moviebox.framework.Interactors
import com.boshra.moviebox.framework.datasourceimpl.MovieDetailsDataSourceImpl
import com.boshra.moviebox.framework.datasourceimpl.MovieListDataSourceImpl
import com.boshra.moviebox.framework.network.ApiService
import com.boshra.moviebox.interactor.GetMostPopularMovies
import com.boshra.moviebox.interactor.GetMovieDetails
import com.boshra.moviebox.interactor.GetPlayingNowMovies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDispatchers() = DispatcherProvider()

    @Provides
    @Singleton
    fun provideMovieListDataSource(apiService: ApiService,
                                       dispatcherProvider: DispatcherProvider)
    : MovieListDataSource {
        return MovieListDataSourceImpl(apiService, dispatcherProvider)
    }

    @Provides
    @Singleton
    fun provideMovieDetailsDataSource(apiService: ApiService,
                                       dispatcherProvider: DispatcherProvider)
    : MovieDetailsDataSource {
        return MovieDetailsDataSourceImpl(apiService, dispatcherProvider)
    }

    @Provides
    @Singleton
    fun provideInteractors(movieListRepo: MovieListRepo,
                           detailsRepo: MovieDetailsRepo): Interactors{
        return Interactors(GetPlayingNowMovies(movieListRepo),
            GetMostPopularMovies(movieListRepo), GetMovieDetails(detailsRepo)
        )
    }

}