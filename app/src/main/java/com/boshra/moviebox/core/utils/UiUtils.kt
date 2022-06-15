package com.boshra.moviebox.core.utils

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

object UiUtils {

    fun getPagerTransformer(nextItemVisiblePx: Float, currentItemHorizontalMarginPx: Float)
    : ViewPager2.PageTransformer {
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            //fading effect of pre/next items
            page.alpha = 0.5f + (1 - abs(position))
        }
        return pageTransformer
    }
}