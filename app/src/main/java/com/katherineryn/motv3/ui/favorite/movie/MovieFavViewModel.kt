package com.katherineryn.motv3.ui.favorite.movie

import androidx.lifecycle.ViewModel
import com.katherineryn.motv3.data.MotvRepository
import com.katherineryn.motv3.data.source.local.entity.MovieEntity

class MovieFavViewModel(private val motvRepository: MotvRepository) : ViewModel() {

    fun getFavMovies() = motvRepository.getFavMovies()

    fun setFavMovie(movie: MovieEntity) {
        val newState = !movie.isFav
        motvRepository.setFavMovie(movie, newState)
    }
}