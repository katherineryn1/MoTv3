package com.katherineryn.motv3.ui.movie

import androidx.lifecycle.ViewModel
import com.katherineryn.motv3.data.MotvRepository

class MovieViewModel(private val motvRepository: MotvRepository) : ViewModel() {

    fun getMovies(sort: String) = motvRepository.getMovies(sort)
}