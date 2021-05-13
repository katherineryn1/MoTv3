package com.katherineryn.motv3.ui.detail

import androidx.lifecycle.ViewModel
import com.katherineryn.motv3.data.MotvRepository

class DetailViewModel(private val motvRepository: MotvRepository) : ViewModel() {
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