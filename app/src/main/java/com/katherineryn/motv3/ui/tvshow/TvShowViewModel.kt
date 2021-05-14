package com.katherineryn.motv3.ui.tvshow

import androidx.lifecycle.ViewModel
import com.katherineryn.motv3.data.MotvRepository

class TvShowViewModel(private val motvRepository: MotvRepository) : ViewModel() {

    fun getTvShows(sort: String) = motvRepository.getTvShows(sort)
}