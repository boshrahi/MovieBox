package com.boshra.moviebox.presentation.playingnow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.boshra.moviebox.R
import com.boshra.moviebox.domain.model.MovieModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_movies_list.view.*

class PlayingNowMovieAdapter(
    private var items: List<MovieModel>,
    private var onClick : (MovieModel) -> Unit,
) : ListAdapter<MovieModel, PlayingNowMovieAdapter.MovieViewHolder>(MoviesDiffCallback) {

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindData(movieModel: MovieModel, position: Int) {
            itemView.tv_movie_title.text = movieModel.title
            itemView.rating_view.setPercentage(movieModel.rating)
            if (movieModel.posterUrl != null){
                Glide.with(itemView.context)
                    .load(movieModel.posterUrl)
                    .fitCenter()
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(32)))
                    .placeholder(R.drawable.ic_question_mark) // any placeholder to load at start
                    .error(R.drawable.ic_question_mark)  // any image in case of error
                    .into(itemView.iv_movie_poster)

            }
            else
                itemView.iv_movie_poster.setImageResource(R.drawable.ic_question_mark)

            itemView.setOnClickListener {
                onClick(movieModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movies_list, parent,false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindData(getItem(position),position)
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