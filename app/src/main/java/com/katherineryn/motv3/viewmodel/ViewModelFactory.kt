package com.katherineryn.motv3.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.katherineryn.motv3.data.MotvRepository
import com.katherineryn.motv3.di.Injection
import com.katherineryn.motv3.ui.detail.DetailViewModel
import com.katherineryn.motv3.ui.favorite.movie.MovieFavViewModel
import com.katherineryn.motv3.ui.movie.MovieViewModel
import com.katherineryn.motv3.ui.tvshow.TvShowViewModel

class ViewModelFactory private constructor(private val motvRepository: MotvRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(motvRepository) as T
            }
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                TvShowViewModel(motvRepository) as T
            }
            modelClass.isAssignableFrom(MovieFavViewModel::class.java) -> {
                MovieFavViewModel(motvRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(motvRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }
}