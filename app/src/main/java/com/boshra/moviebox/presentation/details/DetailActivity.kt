package com.boshra.moviebox.presentation.details

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boshra.moviebox.R
import com.boshra.moviebox.core.ext.gone
import com.boshra.moviebox.core.ext.hide
import com.boshra.moviebox.core.ext.show
import com.boshra.moviebox.core.state.StateData
import com.boshra.moviebox.domain.model.MovieDetailModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_loading.*

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    companion object{
        const val MOVIE_ID = "MOVIE_ID"
    }

    private val viewModel : DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initialDetailsState()
        val movieId = intent.getLongExtra(MOVIE_ID,-1)
        viewModel.getMovieDetails(movieId)
    }

    private fun initialDetailsState() {
        viewModel.details.observe(this) { productStateData ->
            when (productStateData) {
                is StateData.Loading -> {
                    loading_group.show()
                    gr_details_info.gone()
                }
                is StateData.Success -> {
                    loading_group.gone()
                    gr_details_info.show()
                    productStateData.data?.let {
                        fillDetailsData(it)
                    }

                }
                is StateData.Error -> {
                    pg_loading.hide()
                    gr_details_info.gone()
                    tv_loading.text = getString(R.string.error_fetching_data)
                }
            }
        }

        iv_close.setOnClickListener {
            onBackPressed()
        }
    }

    private fun fillDetailsData(detail: MovieDetailModel) {
        tv_detail_title.text = detail.title
        tv_detail_date.text = detail.releaseDate
        tv_detail_runtime.text = detail.runtime
        tv_detail_overview.text = detail.overview

        val langAdapter = ChipAdapter(detail.spokenLanguages, ChipAdapter.LANGUAGE)
        rv_detail_langs.adapter = langAdapter
        rv_detail_langs.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)


        val genresAdapter = ChipAdapter(detail.genres, ChipAdapter.GENRE)
        rv_detail_genres.adapter = genresAdapter
        rv_detail_genres.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)


        if (detail.posterUrl != null){
            Glide.with(this)
                .load(detail.posterUrl)
                .fitCenter()
                .apply(RequestOptions.bitmapTransform(RoundedCorners(32)))
                .placeholder(R.drawable.ic_question_mark) // any placeholder to load at start
                .error(R.drawable.ic_question_mark)  // any image in case of error
                .into(iv_details_poster)

        }
        else
            iv_details_poster.setImageResource(R.drawable.ic_question_mark)
    }
}