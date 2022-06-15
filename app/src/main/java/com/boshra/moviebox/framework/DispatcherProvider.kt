package com.boshra.moviebox.framework

import kotlinx.coroutines.Dispatchers

class DispatcherProvider {

    val IO  = Dispatchers.IO
    val main  = Dispatchers.Main
    val default  = Dispatchers.Default
}