package com.katherineryn.motv3.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.katherineryn.motv3.data.source.local.LocalDataSource
import com.katherineryn.motv3.data.source.local.entity.MovieEntity
import com.katherineryn.motv3.data.source.local.entity.TvShowEntity
import com.katherineryn.motv3.data.source.remote.ApiResponse
import com.katherineryn.motv3.data.source.remote.RemoteDataSource
import com.katherineryn.motv3.data.source.remote.response.movie.MovieDetailResponse
import com.katherineryn.motv3.data.source.remote.response.movie.MovieResponse
import com.katherineryn.motv3.data.source.remote.response.tvshow.TvShowDetailResponse
import com.katherineryn.motv3.data.source.remote.response.tvshow.TvShowResponse
import com.katherineryn.motv3.utils.AppExecutors
import com.katherineryn.motv3.vo.Resource

class MotvRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors)
    : MotvDataSource {

    override fun getMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDb(): LiveData<PagedList<MovieEntity>> {
                return LivePagedListBuilder(localDataSource.getAllMovies(sort), pagedListConfig()).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getMovies()

            override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = data.map {
                    MovieEntity(
                        id = it.id,
                        genre = "",
                        overview = it.overview,
                        poster = it.poster,
                        releaseDate = it.releaseDate,
                        title = it.title,
                        tagline = "",
                        voteAverage = it.voteAverage,
                        isFav = false
                    )
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieDetailResponse>(appExecutors) {
            override fun loadFromDb(): LiveData<MovieEntity> = localDataSource.getMovieById(movieId)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data == null || data.genre == "" // with assumption if genre == "", tag line is "" too. so tag line is not checked

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> =
                remoteDataSource.getMovieDetail(movieId.toString())

            override fun saveCallResult(data: MovieDetailResponse) {
                val listGenres = ArrayList<String>()
                for (i in data.genres!!.indices) {
                    listGenres.add(data.genres[i].name!!)
                }

                val movie = MovieEntity (
                    id = data.id,
                    genre = listGenres.joinToString(),
                    overview = data.overview,
                    poster = data.poster,
                    releaseDate = data.release,
                    title = data.title,
                    tagline = data.tagline,
                    voteAverage = data.voteAverage,
                    isFav = false
                )
                localDataSource.updateMovie(movie, false)
            }
        }.asLiveData()
    }

    override fun getFavMovies(): LiveData<PagedList<MovieEntity>> {
        return LivePagedListBuilder(localDataSource.getFavMovies(), pagedListConfig()).build()
    }

    override fun setFavMovie(movie: MovieEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setFavMovie(movie, state) }

    override fun getTvShows(sort: String): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object : NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowResponse>>(appExecutors) {
            override fun loadFromDb(): LiveData<PagedList<TvShowEntity>> {
                return LivePagedListBuilder(localDataSource.getAllTvShows(sort), pagedListConfig()).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvShowResponse>>> =
                remoteDataSource.getTvShows()

            override fun saveCallResult(data: List<TvShowResponse>) {
                val tvShowList = data.map {
                    TvShowEntity (
                        id = it.id,
                        genre = "",
                        overview = it.overview,
                        poster = it.poster,
                        releaseDate = it.releaseDate,
                        name = it.name,
                        tagline = "",
                        voteAverage = it.voteAverage,
                        isFav = false
                    )
                }
                localDataSource.insertTvShows(tvShowList)
            }
        }.asLiveData()
    }

    override fun getTvShowDetail(tvShowId: Int): LiveData<Resource<TvShowEntity>> {
        return object : NetworkBoundResource<TvShowEntity, TvShowDetailResponse>(appExecutors) {
            override fun loadFromDb(): LiveData<TvShowEntity> = localDataSource.getTvShowById(tvShowId)

            override fun shouldFetch(data: TvShowEntity?): Boolean =
                data == null || data.genre == "" // with assumption if genre == "", tag line is "" too. so tag line is not checked

            override fun createCall(): LiveData<ApiResponse<TvShowDetailResponse>> =
                remoteDataSource.getTvShowDetail(tvShowId.toString())

            override fun saveCallResult(data: TvShowDetailResponse) {
                val listGenres = ArrayList<String>()
                for (i in data.genres!!.indices) {
                    listGenres.add(data.genres[i].name!!)
                }

                val tvShow = TvShowEntity(
                    id = data.id,
                    genre = listGenres.joinToString(),
                    overview = data.overview,
                    poster = data.poster,
                    releaseDate = data.release,
                    name = data.name,
                    tagline = data.tagline,
                    voteAverage = data.voteAverage,
                    isFav = false
                )

                localDataSource.updateTvShow(tvShow, false)
            }
        }.asLiveData()
    }


    override fun getFavTvShows(): LiveData<PagedList<TvShowEntity>> =
        LivePagedListBuilder(localDataSource.getFavTvShows(), pagedListConfig()).build()

    override fun setFavTvShow(tvShow: TvShowEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setFavTvShow(tvShow, state)
        }
    }

    private fun pagedListConfig(): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
    }

    companion object {
        @Volatile
        private var instance: MotvRepository? = null

        fun getInstance(remoteData: RemoteDataSource, localData: LocalDataSource, appExecutors: AppExecutors): MotvRepository =
            instance ?: synchronized(this) {
                instance ?: MotvRepository(remoteData, localData, appExecutors).apply {
                    instance = this
                }
            }
    }
}