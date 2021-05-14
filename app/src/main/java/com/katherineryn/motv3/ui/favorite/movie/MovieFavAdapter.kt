package com.katherineryn.motv3.ui.favorite.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.katherineryn.motv3.R
import com.katherineryn.motv3.data.source.local.entity.MovieEntity
import com.katherineryn.motv3.databinding.ItemsMotvBinding
import com.katherineryn.motv3.network.NetworkConst

class MovieFavAdapter : PagedListAdapter<MovieEntity, MovieFavAdapter.MovieViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class MovieViewHolder(private val binding: ItemsMotvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding) {
                tvTitle.text = movie.title
                tvRating.text = movie.voteAverage.toString()
                imagePoster.loadImage(NetworkConst.IMAGE_URL + movie.poster)

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(movie.id.toString())
                }
            }
        }
    }

    fun getSwipedData(swipedPosition: Int): MovieEntity? = getItem(swipedPosition)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFavAdapter.MovieViewHolder {
        val itemsMovieBinding = ItemsMotvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieFavAdapter.MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_error))
            .into(this)
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: String)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}