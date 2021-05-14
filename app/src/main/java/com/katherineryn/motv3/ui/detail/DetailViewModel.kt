package com.katherineryn.motv3.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.katherineryn.motv3.data.MotvRepository
import com.katherineryn.motv3.data.source.local.entity.MovieEntity
import com.katherineryn.motv3.data.source.local.entity.TvShowEntity
import com.katherineryn.motv3.vo.Resource

class DetailViewModel(private val motvRepository: MotvRepository) : ViewModel() {

    private lateinit var detailMovie: LiveData<Resource<MovieEntity>>
    private lateinit var detailTvShow: LiveData<Resource<TvShowEntity>>

    fun setItem(id: String, category: String) {
        when (category) {
            MOVIE -> {
                detailMovie = motvRepository.getMovieDetail(id.toInt())
            }
            TV_SHOW -> {
                detailTvShow = motvRepository.getTvShowDetail(id.toInt())
            }
        }
    }

    fun getDetailMovie() = detailMovie

    fun getDetailTvShow() = detailTvShow

    fun setFavMovie() {
        val movieResource = detailMovie.value
        if (movieResource?.data != null) {
            val newState = !movieResource.data.isFav
            motvRepository.setFavMovie(movieResource.data, newState)
        }
    }

    fun setFavTvShow() {
        val tvShowResource = detailTvShow.value
        if (tvShowResource?.data != null) {
            val newState = !tvShowResource.data.isFav
            motvRepository.setFavTvShow(tvShowResource.data, newState)
        }
    }

    fun parseDate(date: String): String {
        val dateArray = date.split("-")
        val month = dateArray[1].toInt() - 1
        return "${MONTH_ARRAY[month]} ${dateArray[2]}, ${dateArray[0]}"
    }

    companion object {
        const val MOVIE = "movie"
        const val TV_SHOW = "tvShow"

        private val MONTH_ARRAY = arrayOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        )
    }
}