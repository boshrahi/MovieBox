package com.boshra.moviebox

import androidx.lifecycle.*

/**
 * Observer implementation that owns its lifecycle and achieves a one-time only observation
 * by marking it as destroyed once the onChange handler is executed.
 *
 * @param onChangeHandler the handler to execute on change.
 */
class OneTimeObserver<T>(private val onChangeHandler: Array<out (T) -> Unit>) :
    Observer<T>, LifecycleOwner {
    private val lifecycle = LifecycleRegistry(this)

    var calls = 0

    init {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun getLifecycle(): Lifecycle = lifecycle

    override fun onChanged(t: T) {
        if (calls < onChangeHandler.size) {
            onChangeHandler[calls].invoke(t)
            calls++
        }
        if (calls == onChangeHandler.size) {
            calls = 0
            lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        }
    }
}

fun <T> LiveData<T>.observeLimit(vararg onChangeHandler: (T) -> Unit) {
    val observer = OneTimeObserver(onChangeHandler)
    observe(observer, observer)
}