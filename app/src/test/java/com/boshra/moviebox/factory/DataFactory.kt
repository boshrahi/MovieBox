package com.boshra.moviebox.factory

import kotlin.random.Random

open class DataFactory {

    internal fun getRandomInt(upTo:Int):Int{
        return Random(1000).nextInt(upTo)
    }
    internal fun getRandomFloat():Float{
        return Random(1000).nextFloat()
    }
}