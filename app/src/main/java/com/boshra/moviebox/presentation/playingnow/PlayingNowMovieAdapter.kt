package com.boshra.moviebox.presentation.playingnow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.boshra.moviebox.R
import com.boshra.moviebox.databinding.ItemMoviesListBinding
import com.boshra.moviebox.domain.model.MovieModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class PlayingNowMovieAdapter(
    private var onClick : (MovieModel) -> Unit,
) : PagingDataAdapter<MovieModel, PlayingNowMovieAdapter.MovieViewHolder>(MoviesDiffCallback) {

    companion object{
        const val SHOW_ITEM = 0
        const val LOADING_ITEM = 1
    }
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

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) SHOW_ITEM
        else LOADING_ITEM
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