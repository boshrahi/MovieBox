package com.boshra.moviebox.presentation.popular

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boshra.moviebox.R
import com.boshra.moviebox.domain.model.MovieModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_movies_list.view.*


class PopularPagerAdapter(private val context: Context,
                          private val images: List<MovieModel>,
                          private val onClick: (Int)-> Unit):
    RecyclerView.Adapter<PopularPagerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder( LayoutInflater.from(parent.context)
            .inflate(R.layout.item_popular_movie_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (images[position].posterUrl != null){
            Glide.with(context)
                .load(images[position].posterUrl)
                .fitCenter()
                .apply(RequestOptions.bitmapTransform(RoundedCorners(32)))
                .placeholder(R.drawable.ic_question_mark) // any placeholder to load at start
                .error(R.drawable.ic_question_mark)  // any image in case of error
                .into(holder.itemView.iv_movie_poster)

        }
        else
            holder.itemView.iv_movie_poster.iv_movie_poster.setImageResource(R.drawable.ic_question_mark)
        holder.itemView.iv_movie_poster.setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}
