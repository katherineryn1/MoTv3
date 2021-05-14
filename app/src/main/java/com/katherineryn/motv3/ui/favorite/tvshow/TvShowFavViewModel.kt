package com.katherineryn.motv3.ui.favorite.tvshow

import androidx.lifecycle.ViewModel
import com.katherineryn.motv3.data.MotvRepository
import com.katherineryn.motv3.data.source.local.entity.TvShowEntity

class TvShowFavViewModel(private val motvRepository: MotvRepository) : ViewModel() {

    fun getFavTvShows() = motvRepository.getFavTvShows()

    fun setFavTvShow(tvShow: TvShowEntity) {
        val newState = !tvShow.isFav
        motvRepository.setFavTvShow(tvShow, newState)
    }
}