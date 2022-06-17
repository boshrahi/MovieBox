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
import com.boshra.moviebox.databinding.ActivityDetailBinding
import com.boshra.moviebox.domain.model.MovieDetailModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    companion object{
        const val MOVIE_ID = "MOVIE_ID"
    }

    private val viewModel : DetailViewModel by viewModels()
    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialDetailsState()
        val movieId = intent.getLongExtra(MOVIE_ID,-1)
        viewModel.getMovieDetails(movieId)
    }

    private fun initialDetailsState() {
        viewModel.details.observe(this) { productStateData ->
            when (productStateData) {
                is StateData.Loading -> {
                    binding.layoutLoading.loadingGroup.show()
                    binding.grDetailsInfo.gone()
                }
                is StateData.Success -> {
                    binding.layoutLoading.loadingGroup.gone()
                    binding.grDetailsInfo.show()
                    productStateData.data?.let {
                        fillDetailsData(it)
                    }

                }
                is StateData.Error -> {
                    binding.layoutLoading.pgLoading.hide()
                    binding.grDetailsInfo.gone()
                    binding.layoutLoading.tvLoading.text =
                        getString(R.string.error_fetching_data)
                }
            }
        }

        binding.ivClose.setOnClickListener {
            onBackPressed()
        }
    }

    private fun fillDetailsData(detail: MovieDetailModel) {
        binding.tvDetailTitle.text = detail.title
        binding.tvDetailDate.text = detail.releaseDate
        binding.tvDetailRuntime.text = detail.runtime
        binding.tvDetailOverview.text = detail.overview

        val langAdapter = ChipAdapter(detail.spokenLanguages, ChipAdapter.LANGUAGE)
        binding.rvDetailLangs.adapter = langAdapter
        binding.rvDetailLangs.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)


        val genresAdapter = ChipAdapter(detail.genres, ChipAdapter.GENRE)
        binding.rvDetailGenres.adapter = genresAdapter
        binding.rvDetailGenres.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)


        if (detail.posterUrl != null){
            Glide.with(this)
                .load(detail.posterUrl)
                .fitCenter()
                .apply(RequestOptions.bitmapTransform(RoundedCorners(32)))
                .placeholder(R.drawable.ic_question_mark) // any placeholder to load at start
                .error(R.drawable.ic_question_mark)  // any image in case of error
                .into(binding.ivDetailsPoster)

        }
        else
            binding.ivDetailsPoster.setImageResource(R.drawable.ic_question_mark)
    }
}