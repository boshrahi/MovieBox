package com.boshra.moviebox.core.ext

import android.util.DisplayMetrics
import java.text.SimpleDateFormat
import java.util.*

fun Date.toReadableDate() : String{
    val simpleDateFormat = SimpleDateFormat("LLLL, dd yyyy", Locale.US)
    return simpleDateFormat.format(this.time).toString()
}

fun Int.toReadableTime() : String{
    val hours = this.div(60)
    val min = this%60
    if (min == 0) return "$hours"+"h"
    if (min < 10) return "$hours"+"h0$min"
    return "$hours"+"h$min"
}

fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()

fun Int.pxToDp(displayMetrics: DisplayMetrics): Int = (this / displayMetrics.density).toInt()