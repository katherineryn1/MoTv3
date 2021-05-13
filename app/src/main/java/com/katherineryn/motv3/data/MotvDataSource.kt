package com.katherineryn.motv3.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.katherineryn.motv3.data.source.local.entity.MovieEntity
import com.katherineryn.motv3.data.source.local.entity.TvShowEntity
import com.katherineryn.motv3.vo.Resource

interface MotvDataSource {

    fun getMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>>

    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>>

    fun getFavMovies(): LiveData<PagedList<MovieEntity>>

    fun setFavMovie(movie: MovieEntity, state: Boolean)

    fun getTvShows(sort: String): LiveData<Resource<PagedList<TvShowEntity>>>

    fun getTvShowDetail(tvShowId: Int): LiveData<Resource<TvShowEntity>>

    fun getFavTvShows(): LiveData<PagedList<TvShowEntity>>

    fun setFavTvShow(tvShow: TvShowEntity, state: Boolean)
}