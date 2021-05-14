package com.katherineryn.motv3.ui.favorite.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.katherineryn.motv3.R
import com.katherineryn.motv3.data.source.local.entity.TvShowEntity
import com.katherineryn.motv3.databinding.ItemsMotvBinding
import com.katherineryn.motv3.network.NetworkConst.IMAGE_URL

class TvShowFavAdapter : PagedListAdapter<TvShowEntity, TvShowFavAdapter.TvShowFavViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class TvShowFavViewHolder(private val binding: ItemsMotvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShowEntity) {
            with(binding) {
                tvTitle.text = tvShow.name
                tvRating.text = tvShow.voteAverage.toString()
                imagePoster.loadImage(IMAGE_URL + tvShow.poster)

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(tvShow.id.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowFavAdapter.TvShowFavViewHolder {
        val itemsTvShowBinding = ItemsMotvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowFavViewHolder(itemsTvShowBinding)
    }

    override fun onBindViewHolder(holder: TvShowFavAdapter.TvShowFavViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(this)
    }

    fun getSwipedData(swipedPosition: Int): TvShowEntity? = getItem(swipedPosition)

    interface OnItemClickCallback {
        fun onItemClicked(id: String)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}