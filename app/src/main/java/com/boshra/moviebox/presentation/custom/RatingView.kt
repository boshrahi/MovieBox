package com.boshra.moviebox.presentation.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import com.boshra.moviebox.R
import com.boshra.moviebox.core.ext.hide
import kotlinx.android.synthetic.main.view_rating.view.*

class RatingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs) {

    init{
        View.inflate(context, R.layout.view_rating, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.RatingView)
        val percentageColor = attributes.getColor(R.styleable.RatingView_percentageTextColor,
            context.getColor(android.R.color.black))
        tv_rating_percent.setTextColor(percentageColor)

        attributes.recycle()
    }

    fun setPercentage(percentage: Float?){
        if (percentage == null || percentage<0){
            tv_rating_percent.hide()
            v_rating_color.hide()
            return
        }
        val percent = "${percentage.toInt()}%"
        tv_rating_percent.text = percent
        if (percentage < 50.0){
            v_rating_color.background = AppCompatResources
                .getDrawable(context, R.drawable.bg_red_gradient)

        }else
            v_rating_color.background = AppCompatResources
                .getDrawable(context, R.drawable.bg_green_gradient)
    }
}