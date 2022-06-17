package com.boshra.moviebox.presentation.playingnow

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.boshra.moviebox.R
import com.boshra.moviebox.core.ext.gone
import com.boshra.moviebox.core.ext.show
import com.boshra.moviebox.core.state.StateData
import com.boshra.moviebox.databinding.FragPlayingNowBinding
import com.boshra.moviebox.presentation.MoviesViewModel
import com.boshra.moviebox.presentation.details.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayingNowFragment : Fragment(R.layout.frag_playing_now) {

    private val viewModel : MoviesViewModel by viewModels()
    private lateinit var binding: FragPlayingNowBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragPlayingNowBinding.bind(view)
        initMoviesList()
    }

    private fun initMoviesList() {

        val moviesAdapter = PlayingNowMovieAdapter(emptyList()){
            val intent = Intent(requireActivity(),DetailActivity::class.java)
            intent.putExtra(DetailActivity.MOVIE_ID, it.id)
            requireActivity().startActivity(intent)
        }
        binding.rvMoviesList.adapter = moviesAdapter
        binding.rvMoviesList.layoutManager = GridLayoutManager(requireActivity(),2)

        viewModel.playingNowMovies.observe(viewLifecycleOwner) { productStateData ->
            when (productStateData) {
                is StateData.Loading -> {
                    binding.layoutLoading.loadingGroup.show()
                }
                is StateData.Success -> {
                    binding.layoutLoading.loadingGroup.gone()
                    productStateData.data?.let {
                        moviesAdapter.submitList(it)
                    }

                }
                is StateData.Error -> {
                    binding.layoutLoading.pgLoading.gone()
                    binding.layoutLoading.tvLoading.text =
                        getString(R.string.error_fetching_data)
                }
            }
        }
    }
}