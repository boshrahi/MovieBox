package com.boshra.moviebox.presentation.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boshra.moviebox.R
import kotlinx.android.synthetic.main.item_chip_genre.view.*
import kotlinx.android.synthetic.main.item_chip_language.view.*

class ChipAdapter(private val items: List<String>,
                  private val type: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object{
        const val LANGUAGE = 1
        const val GENRE = 2
    }

    inner class GenreChipViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: String) {
            itemView.tv_genre_title.text = item
        }
    }

    inner class LanguageChipViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: String) {
            itemView.tv_language_title.text = item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            LANGUAGE -> {
                LanguageChipViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_chip_language, parent, false)
                )
            }
            else -> {
                GenreChipViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_chip_genre, parent, false)
                )
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is LanguageChipViewHolder) {
            holder.bind(item)
        }
        if (holder is GenreChipViewHolder) {
            holder.bind(item)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}