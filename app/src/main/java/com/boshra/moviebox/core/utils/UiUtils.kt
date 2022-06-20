package com.boshra.moviebox.core.utils

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.boshra.moviebox.R
import com.boshra.moviebox.core.ext.gone
import com.boshra.moviebox.core.ext.show
import com.boshra.moviebox.databinding.LayoutLoadingBinding
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

    fun setLoadingLayout(layoutLoading: LayoutLoadingBinding, message: String) {
        layoutLoading.loadingGroup.show()
        layoutLoading.btnRetry.gone()
        layoutLoading.tvLoading.text = message
    }

    fun setErrorLayout(layoutLoading: LayoutLoadingBinding, message: String) {
        layoutLoading.pgLoading.gone()
        layoutLoading.btnRetry.show()
        layoutLoading.tvLoading.text = message
    }

    fun hideLoadingLayout(layoutLoading: LayoutLoadingBinding) {
        layoutLoading.loadingGroup.gone()
        layoutLoading.btnRetry.gone()
    }
}