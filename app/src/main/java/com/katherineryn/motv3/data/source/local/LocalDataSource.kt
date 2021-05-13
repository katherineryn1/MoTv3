package com.katherineryn.motv3.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.katherineryn.motv3.data.source.local.entity.MovieEntity
import com.katherineryn.motv3.data.source.local.entity.TvShowEntity
import com.katherineryn.motv3.data.source.local.room.MotvDao
import com.katherineryn.motv3.utils.SortUtils
import com.katherineryn.motv3.utils.SortUtils.MOVIE_TABLE
import com.katherineryn.motv3.utils.SortUtils.TVSHOW_TABLE

class LocalDataSource private constructor(private val mMotvDao: MotvDao) {
    // movies
    fun getAllMovies(sort: String): DataSource.Factory<Int, MovieEntity> = mMotvDao.getMovies(SortUtils.getSortedQuery(sort, MOVIE_TABLE))

    fun getMovieById(id: Int): LiveData<MovieEntity> = mMotvDao.getMovieById(id)

    fun getFavMovies(): DataSource.Factory<Int, MovieEntity> = mMotvDao.getFavMovies()

    fun insertMovies(movies: List<MovieEntity>) = mMotvDao.insertMovies(movies)

    fun updateMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFav = newState
        mMotvDao.updateMovie(movie)
    }

    fun setFavMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFav = newState
        mMotvDao.updateMovie(movie)
    }

    // tv shows
    fun getAllTvShows(sort: String): DataSource.Factory<Int, TvShowEntity> = mMotvDao.getTvShows(SortUtils.getSortedQuery(sort, TVSHOW_TABLE))

    fun getTvShowById(id: Int): LiveData<TvShowEntity> = mMotvDao.getTvShowById(id)

    fun getFavTvShows(): DataSource.Factory<Int, TvShowEntity> = mMotvDao.getFavTvShows()

    fun insertTvShows(tvShows: List<TvShowEntity>) = mMotvDao.insertTvShows(tvShows)

    fun updateTvShow(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.isFav = newState
        mMotvDao.updateTvShow(tvShow)
    }

    fun setFavTvShow(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.isFav = newState
        mMotvDao.updateTvShow(tvShow)
    }

    // following the instructor guide in submission 2 about class order
    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(motvDao: MotvDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(motvDao)
    }
}