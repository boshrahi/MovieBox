package com.boshra.moviebox.domain

import com.boshra.moviebox.domain.model.MovieModel
import junit.framework.TestCase
import org.junit.Test
import java.util.*


class EqualHashCodeMethodsTest : TestCase() {

    @Test
    fun test_2_movie_model_equals() {

        val calendar = Calendar.getInstance();
        calendar.set(1994, Calendar.MAY, 20)
        val date: Date = calendar.time

        val movieModel1 = MovieModel(1,"url","overview",
            "title",date,50.0f)
        val movieModel2 = MovieModel(1,"url","overview",
            "title",date,50.0f)

        assertEquals(true, movieModel1 == movieModel2)
    }

    @Test
    fun test_2_movie_model_not_equals() {

        val movieModel1 = MovieModel(1,"url","overview",
            "title",Date(),50.0f)
        val movieModel2 = MovieModel(2,"url","overview",
            "title",Date(),50.0f)

        assertEquals(false, movieModel1 == movieModel2)
    }

    @Test
    fun test_2_equal_movie_model_have_same_hashcode() {

        val calendar = Calendar.getInstance();
        calendar.set(1994, Calendar.MAY, 20)
        val date: Date = calendar.time

        val movieModel1 = MovieModel(1,"url","overview",
            "title",date,50.0f)
        val movieModel2 = MovieModel(1,"url","overview",
            "title",date,50.0f)

        assertEquals(true, movieModel1.hashCode() == movieModel2.hashCode())
    }
}