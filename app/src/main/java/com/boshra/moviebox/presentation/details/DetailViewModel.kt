package com.boshra.moviebox.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boshra.moviebox.core.state.StateData
import com.boshra.moviebox.core.state.StateMutableLiveData
import com.boshra.moviebox.domain.model.MovieDetailModel
import com.boshra.moviebox.framework.Interactors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val interactors: Interactors
) : ViewModel() {

    //---------- Live data
    private val _details = StateMutableLiveData<MovieDetailModel>()
    val details: LiveData<StateData<MovieDetailModel>>
        get() = _details

    fun getMovieDetails(movieId: Long) = viewModelScope.launch {
        interactors.getMovieDetails(movieId)
            .onStart { _details.setLoading() }
            .catch { _details.setError(it) }
            .collect { _details.setSuccess(it) }
    }
}