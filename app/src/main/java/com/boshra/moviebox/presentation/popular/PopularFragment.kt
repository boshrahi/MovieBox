package com.boshra.moviebox.presentation.popular

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.boshra.moviebox.R
import com.boshra.moviebox.core.ext.gone
import com.boshra.moviebox.core.ext.hide
import com.boshra.moviebox.core.ext.setTextAnimation
import com.boshra.moviebox.core.ext.show
import com.boshra.moviebox.core.state.StateData
import com.boshra.moviebox.core.utils.UiUtils.getPagerTransformer
import com.boshra.moviebox.databinding.FragMostPopularBinding
import com.boshra.moviebox.domain.model.MovieModel
import com.boshra.moviebox.presentation.MoviesViewModel
import com.boshra.moviebox.presentation.custom.HorizontalMarginItemDecoration
import com.boshra.moviebox.presentation.details.DetailActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PopularFragment : Fragment(R.layout.frag_most_popular) {

    private val viewModel : MoviesViewModel by viewModels()
    private lateinit var binding : FragMostPopularBinding

    private val onPageChangeCallback = object: OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            viewModel.updatePopularMovieInfo(position)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.pager.registerOnPageChangeCallback(onPageChangeCallback)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragMostPopularBinding.bind(view)
        viewModel.getMostPopularMovies()
        initMoviesList()
    }

    private fun initMoviesList() {

        viewModel.mostPopularMovies.observe(viewLifecycleOwner) { productStateData ->
            when (productStateData) {
                is StateData.Loading -> {
                    binding.layoutLoading.loadingGroup.show()
                    binding.grPopularInfo.hide()
                }
                is StateData.Success -> {
                    binding.layoutLoading.loadingGroup.gone()
                    binding.grPopularInfo.show()
                    productStateData.data?.let {
                       updatePager(it)
                    }

                }
                is StateData.Error -> {
                    binding.layoutLoading.pgLoading.gone()
                    binding.grPopularInfo.hide()
                    binding.layoutLoading.tvLoading.text =
                        getString(R.string.error_fetching_data)
                }
            }
        }

        viewModel.popularMovieInfo.observe(viewLifecycleOwner){
            if (it == null) return@observe
            binding.tvPopularName.setTextAnimation(it.title)
            binding.viewPopularRating.setPercentage(it.rating)
            binding.tvPopularOverview.setTextAnimation(it.overview)
        }
    }

    private fun updatePager(list: List<MovieModel>) {
        binding.pager.adapter = PopularPagerAdapter(requireActivity(), list){ position->
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.MOVIE_ID, list[position].id)
            requireActivity().startActivity(intent)
        }

        viewModel.updatePopularMovieInfo(0)
        //retain one page on each side so that the next and previous items are visible
        binding.pager.offscreenPageLimit = 1
        // Add a PageTransformer that translates the next and previous items horizontally
        // towards the center of the screen, which makes them visible
        binding.pager.setPageTransformer(
            getPagerTransformer(resources.getDimension(R.dimen.viewpager_next_item_visible),
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)))

        // The ItemDecoration gives the current (centered) item horizontal margin so that
        // it doesn't occupy the whole screen width. Without it the items overlap
        binding.pager.addItemDecoration(HorizontalMarginItemDecoration(
            requireActivity(),
            R.dimen.viewpager_current_item_horizontal_margin))
    }

    override fun onPause() {
        super.onPause()
        binding.pager.unregisterOnPageChangeCallback(onPageChangeCallback)
    }
}