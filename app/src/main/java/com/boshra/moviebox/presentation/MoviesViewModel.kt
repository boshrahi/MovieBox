package com.boshra.moviebox.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.boshra.moviebox.core.state.StateData
import com.boshra.moviebox.core.state.StateMutableLiveData
import com.boshra.moviebox.domain.model.MovieModel
import com.boshra.moviebox.framework.Interactors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val interactors: Interactors
): ViewModel() {

    //---------- Live data
    private lateinit var _playingNowMovies : Flow<PagingData<MovieModel>>
    val playingNowMovies: Flow<PagingData<MovieModel>>
        get() = _playingNowMovies

    private val _mostPopularMovies = StateMutableLiveData<List<MovieModel>>()
    val mostPopularMovies: LiveData<StateData<List<MovieModel>>>
        get() = _mostPopularMovies

    private val _popularMovieInfo = MutableLiveData<MovieModel?>()
    val popularMovieInfo: LiveData<MovieModel?>
        get() = _popularMovieInfo

    init {
        getPlayingNowMovies()
    }

    fun getPlayingNowMovies() = viewModelScope.launch {
        val result = interactors.getPlayingNowMovies().cachedIn(viewModelScope)
        _playingNowMovies = result
    }

    fun getMostPopularMovies() = viewModelScope.launch {
        interactors.getMostPopularMovies()
            .onStart { _mostPopularMovies.setLoading() }
            .catch { _mostPopularMovies.setError(it) }
            .collect { _mostPopularMovies.setSuccess(it) }
    }

    fun updatePopularMovieInfo(position: Int) {
        if (mostPopularMovies.value is StateData.Success){
            val movieModel = (mostPopularMovies.value as StateData.Success<List<MovieModel>>)
                .data?.get(position)
            _popularMovieInfo.postValue(movieModel)
        }
    }
}