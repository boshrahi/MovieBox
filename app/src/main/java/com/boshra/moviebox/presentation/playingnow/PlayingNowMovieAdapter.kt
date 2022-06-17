package com.boshra.moviebox.presentation.playingnow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.boshra.moviebox.R
import com.boshra.moviebox.databinding.ItemMoviesListBinding
import com.boshra.moviebox.databinding.MovieItemBinding
import com.boshra.moviebox.domain.model.MovieModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_movies_list.view.*

class PlayingNowMovieAdapter(
    private var onClick : (MovieModel) -> Unit,
) : PagingDataAdapter<MovieModel, PlayingNowMovieAdapter.MovieViewHolder>(MoviesDiffCallback) {


    inner class MovieViewHolder(private val binding: ItemMoviesListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(movieModel: MovieModel, position: Int) {
            binding.tvMovieTitle.text = movieModel.title
            binding.ratingView.setPercentage(movieModel.rating)
            if (movieModel.posterUrl != null){
                Glide.with(itemView.context)
                    .load(movieModel.posterUrl)
                    .fitCenter()
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(32)))
                    .placeholder(R.drawable.ic_question_mark) // any placeholder to load at start
                    .error(R.drawable.ic_question_mark)  // any image in case of error
                    .into(binding.ivMoviePoster)

            }
            else
                binding.ivMoviePoster.setImageResource(R.drawable.ic_question_mark)

            binding.ivMoviePoster.setOnClickListener {
                onClick(movieModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMoviesListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bindData(it, position) }
    }

    object MoviesDiffCallback : DiffUtil.ItemCallback<MovieModel>() {

        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }

    }

}