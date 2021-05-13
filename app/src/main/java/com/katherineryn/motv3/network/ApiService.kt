package com.katherineryn.motv3.network

import com.katherineryn.motv3.data.source.remote.response.movie.MovieDetailResponse
import com.katherineryn.motv3.data.source.remote.response.movie.MovieListResponse
import com.katherineryn.motv3.data.source.remote.response.tvshow.TvShowDetailResponse
import com.katherineryn.motv3.data.source.remote.response.tvshow.TvShowListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // get list of movie
    @GET("movie/now_playing")
    fun getMovies(
        @Query("api_key") apiKey: String
    ) : Call<MovieListResponse>

    // get detail movie
    @GET("movie/{id}")
    fun getMovieDetail(
        @Path("id") id: String,
        @Query("api_key") apiKey: String
    ) : Call<MovieDetailResponse>

    // get list of tv show
    @GET("tv/on_the_air")
    fun getTvShows(
        @Query("api_key") apiKey: String
    ) : Call<TvShowListResponse>

    // get detail tv show
    @GET("tv/{id}")
    fun getTvShowDetail(
        @Path("id") id: String,
        @Query("api_key") apiKey: String
    ) : Call<TvShowDetailResponse>
}