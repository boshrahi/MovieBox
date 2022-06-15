package com.boshra.moviebox.core

import com.boshra.moviebox.core.ext.toReadableDate
import com.boshra.moviebox.core.ext.toReadableTime
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.util.*

class TextExtensionTest {

    @Test
    fun get_date_obj_and_return_readable_format_month_days_years() {

        val calendar = Calendar.getInstance();
        calendar.set(1994, Calendar.MAY, 20)
        val date: Date = calendar.time
        assertThat(date.toReadableDate()).isEqualTo("May, 20 1994")
    }

    @Test
    fun get_135_runtime_movie_and_return_readable_format_hours_minutes() {

        val runtime = 135
        assertThat(runtime.toReadableTime()).isEqualTo("2h15")
    }

    @Test
    fun get_120_runtime_movie_and_return_readable_format_hours_minutes() {

        val runtime = 120
        assertThat(runtime.toReadableTime()).isEqualTo("2h")
    }

    @Test
    fun get_125_runtime_movie_and_return_readable_format_hours_minutes() {

        val runtime = 125
        assertThat(runtime.toReadableTime()).isEqualTo("2h05")
    }
}