package com.boshra.moviebox.presentation.playingnow

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.boshra.moviebox.R
import com.boshra.moviebox.core.ext.gone
import com.boshra.moviebox.core.ext.show
import com.boshra.moviebox.core.state.StateData
import com.boshra.moviebox.databinding.FragPlayingNowBinding
import com.boshra.moviebox.presentation.MoviesViewModel
import com.boshra.moviebox.presentation.details.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

        val moviesAdapter = PlayingNowMovieAdapter(){
            val intent = Intent(requireActivity(),DetailActivity::class.java)
            intent.putExtra(DetailActivity.MOVIE_ID, it.id)
            requireActivity().startActivity(intent)
        }

        binding.rvMoviesList.adapter = moviesAdapter
        binding.rvMoviesList.layoutManager = GridLayoutManager(requireActivity(),2)


        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            moviesAdapter.loadStateFlow.collectLatest {

                when (it.refresh) {
                    is LoadState.Loading -> binding.layoutLoading.loadingGroup.show()
                    is LoadState.Error -> {
                        binding.layoutLoading.pgLoading.gone()
                        binding.layoutLoading.tvLoading.text =
                            getString(R.string.error_fetching_data)
                    }
                    else -> binding.layoutLoading.loadingGroup.gone()
                }
            }

        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.playingNowMovies.collect {
                moviesAdapter.submitData(it)
            }
        }
    }
}