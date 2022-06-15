package com.boshra.moviebox.core.state

sealed class StateData<out T> {

    class Loading<T>(val data: T? = null) : StateData<T>()
    class Success<T>(val data: T?) : StateData<T>()
    class Error<T>(val error: Throwable?) : StateData<T>()
}
