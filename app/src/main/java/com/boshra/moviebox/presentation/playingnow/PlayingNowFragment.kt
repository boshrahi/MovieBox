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
import com.boshra.moviebox.presentation.MoviesViewModel
import com.boshra.moviebox.presentation.details.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.frag_playing_now.*
import kotlinx.android.synthetic.main.layout_loading.*

@AndroidEntryPoint
class PlayingNowFragment : Fragment(R.layout.frag_playing_now) {

    private val viewModel : MoviesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMoviesList()
    }

    private fun initMoviesList() {

        val moviesAdapter = PlayingNowMovieAdapter(emptyList()){
            val intent = Intent(requireActivity(),DetailActivity::class.java)
            intent.putExtra(DetailActivity.MOVIE_ID, it.id)
            requireActivity().startActivity(intent)
        }
        rv_movies_list.adapter = moviesAdapter
        rv_movies_list.layoutManager = GridLayoutManager(requireActivity(),2)

        viewModel.playingNowMovies.observe(viewLifecycleOwner) { productStateData ->
            when (productStateData) {
                is StateData.Loading -> {
                    loading_group.show()
                }
                is StateData.Success -> {
                    loading_group.gone()
                    productStateData.data?.let {
                        moviesAdapter.submitList(it)
                    }

                }
                is StateData.Error -> {
                    pg_loading.gone()
                    tv_loading.text = getString(R.string.error_fetching_data)
                }
            }
        }
    }
}