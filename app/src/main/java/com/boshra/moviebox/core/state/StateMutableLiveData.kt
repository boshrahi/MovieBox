package com.boshra.moviebox.core.state

import androidx.lifecycle.MutableLiveData

open class StateMutableLiveData<T> : MutableLiveData<StateData<T>>() {

    fun postLoading() {
        postValue(StateData.Loading())
    }

    fun setLoading() {
        value = StateData.Loading()
    }

    fun postError(error: Throwable?) {
        postValue(StateData.Error(error))
    }

    fun setError(error: Throwable?) {
        value = StateData.Error(error)
    }

    fun postSuccess(data: T) {
        postValue(StateData.Success(data))
    }

    fun setSuccess(data: T) {
        value = StateData.Success(data)
    }
}