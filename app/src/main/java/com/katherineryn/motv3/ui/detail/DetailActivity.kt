package com.katherineryn.motv3.ui.detail

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.katherineryn.motv3.R
import com.katherineryn.motv3.data.source.local.entity.MovieEntity
import com.katherineryn.motv3.data.source.local.entity.TvShowEntity
import com.katherineryn.motv3.databinding.ActivityDetailBinding
import com.katherineryn.motv3.network.NetworkConst.IMAGE_URL
import com.katherineryn.motv3.ui.detail.DetailViewModel.Companion.MOVIE
import com.katherineryn.motv3.ui.detail.DetailViewModel.Companion.TV_SHOW
import com.katherineryn.motv3.viewmodel.ViewModelFactory
import com.katherineryn.motv3.vo.Status

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var activityDetailBinding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var itemCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        setActionBarTitle("")
        showProgressBar(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        activityDetailBinding.fabFavorite.setOnClickListener(this)
        activityDetailBinding.tvOverview.movementMethod = ScrollingMovementMethod()

        init()
    }

    private fun init() {
        val id = intent.extras?.getString(EXTRA_ID)
        itemCategory = intent.extras?.getString(EXTRA_CATEGORY)

        if (id != null && itemCategory != null) {
            viewModel.setItem(id, itemCategory.toString())
            when (itemCategory) {
                MOVIE -> {
                    viewModel.getDetailMovie().observe(this, { detail ->
                        when (detail.status) {
                            Status.LOADING -> showProgressBar(true)
                            Status.SUCCESS -> {
                                if (detail.data != null) {
                                    showProgressBar(false)
                                    populateMovieDetail(detail.data)
                                }
                            }
                            Status.ERROR -> {
                                showProgressBar(false)
                                Log.e(TAG, "getDetailMovie failed")
                                Toast.makeText(applicationContext, "Something wrong when retrieve the data", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                }
                TV_SHOW -> {
                    viewModel.getDetailTvShow().observe(this, { detail ->
                        when (detail.status) {
                            Status.LOADING -> showProgressBar(true)
                            Status.SUCCESS -> {
                                if (detail.data != null) {
                                    showProgressBar(false)
                                    populateTvShowDetail(detail.data)
                                }
                            }
                            Status.ERROR -> {
                                showProgressBar(false)
                                Log.e(TAG, "getDetailTvShow failed")
                                Toast.makeText(applicationContext, "Something wrong when retrieve the data", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                }
            }
        }
    }

    private fun populateMovieDetail(movie: MovieEntity) {
        with (activityDetailBinding) {
            tvTitle.text = movie.title
            tvRating.text = movie.voteAverage.toString()
            tvRelease.text = viewModel.parseDate(movie.releaseDate!!)
            tvGenre.text = movie.genre
            tvOverview.text = movie.overview
            imagePoster.loadImage(IMAGE_URL + movie.poster)
            setFavState(movie.isFav)

            if (movie.tagline.isNullOrEmpty()) {
                tvTagline.visibility = View.GONE
            } else {
                tvTagline.visibility = View.VISIBLE
                tvTagline.text = resources.getString(R.string.tagline, movie.tagline)
            }

            setActionBarTitle(movie.title!!)
        }
    }

    private fun populateTvShowDetail(tvShow: TvShowEntity) {
        with (activityDetailBinding) {
            tvTitle.text = tvShow.name
            tvRating.text = tvShow.voteAverage.toString()
            tvRelease.text = viewModel.parseDate(tvShow.releaseDate!!)
            tvGenre.text = tvShow.genre
            tvOverview.text = tvShow.overview
            imagePoster.loadImage(IMAGE_URL + tvShow.poster)
            setFavState(tvShow.isFav)

            if (tvShow.tagline.isNullOrEmpty()) {
                tvTagline.visibility = View.GONE
            } else {
                tvTagline.visibility = View.VISIBLE
                tvTagline.text = resources.getString(R.string.tagline, tvShow.tagline)
            }

            setActionBarTitle(tvShow.name!!)
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

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab_favorite -> onClickFab()
        }
    }

    private fun onClickFab() {
        when (itemCategory) {
            MOVIE -> viewModel.setFavMovie()
            TV_SHOW -> viewModel.setFavTvShow()
        }
    }

    private fun setFavState(state: Boolean) {
        if (state) {
            activityDetailBinding.fabFavorite.setImageResource(R.drawable.ic_favorite_yellow)
        } else {
            activityDetailBinding.fabFavorite.setImageResource(R.drawable.ic_favorite_border_yellow)
        }
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun showProgressBar(visibilityStatus: Boolean) {
        // if progress bar visible, content invisible
        activityDetailBinding.progressBar.isVisible = visibilityStatus
        activityDetailBinding.contentDetail.isInvisible = visibilityStatus
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_CATEGORY = "extra_category"
        private val TAG = DetailActivity::class.java.simpleName
    }
}