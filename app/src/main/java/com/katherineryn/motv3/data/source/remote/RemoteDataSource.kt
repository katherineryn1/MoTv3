package com.katherineryn.motv3.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.katherineryn.motv3.data.source.remote.response.movie.MovieDetailResponse
import com.katherineryn.motv3.data.source.remote.response.movie.MovieListResponse
import com.katherineryn.motv3.data.source.remote.response.movie.MovieResponse
import com.katherineryn.motv3.data.source.remote.response.tvshow.TvShowDetailResponse
import com.katherineryn.motv3.data.source.remote.response.tvshow.TvShowListResponse
import com.katherineryn.motv3.data.source.remote.response.tvshow.TvShowResponse
import com.katherineryn.motv3.network.ApiConfig
import com.katherineryn.motv3.network.NetworkConst.API_KEY
import com.katherineryn.motv3.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    fun getMovies(): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        val client = ApiConfig.getApiService().getMovies(API_KEY)

        client.enqueue(object : Callback<MovieListResponse>{
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                resultMovies.value = ApiResponse.success(response.body()?.movieList as List<MovieResponse>)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                Log.e(TAG, "getMovies failed : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return resultMovies
    }

    fun getMovieDetail(movieId: String): LiveData<ApiResponse<MovieDetailResponse>> {
        EspressoIdlingResource.increment()
        val resultMovieDetail = MutableLiveData<ApiResponse<MovieDetailResponse>>()
        val client = ApiConfig.getApiService().getMovieDetail(movieId, API_KEY)

        client.enqueue(object : Callback<MovieDetailResponse>{
            override fun onResponse(
                call: Call<MovieDetailResponse>,
                response: Response<MovieDetailResponse>
            ) {
                resultMovieDetail.value = ApiResponse.success(response.body() as MovieDetailResponse)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                Log.e(TAG, "getMovieDetail failed : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return resultMovieDetail
    }

    fun getTvShows(): LiveData<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()
        val resultTvShows = MutableLiveData<ApiResponse<List<TvShowResponse>>>()
        val client = ApiConfig.getApiService().getTvShows(API_KEY)

        client.enqueue(object : Callback<TvShowListResponse> {
            override fun onResponse(
                call: Call<TvShowListResponse>,
                response: Response<TvShowListResponse>
            ) {
                resultTvShows.value = ApiResponse.success(response.body()?.tvShowList as List<TvShowResponse>)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvShowListResponse>, t: Throwable) {
                Log.e(TAG, "getTvShows failed : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return resultTvShows
    }

    fun getTvShowDetail(tvShowId: String): LiveData<ApiResponse<TvShowDetailResponse>> {
        EspressoIdlingResource.increment()
        val resultTvShowDetail = MutableLiveData<ApiResponse<TvShowDetailResponse>>()
        val client = ApiConfig.getApiService().getTvShowDetail(tvShowId, API_KEY)

        client.enqueue(object : Callback<TvShowDetailResponse>{
            override fun onResponse(
                call: Call<TvShowDetailResponse>,
                response: Response<TvShowDetailResponse>
            ) {
                resultTvShowDetail.value = ApiResponse.success(response.body() as TvShowDetailResponse)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvShowDetailResponse>, t: Throwable) {
                Log.e(TAG, "getTvShowDetail failed : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return resultTvShowDetail
    }

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }

        private val TAG = RemoteDataSource::class.java.simpleName
    }
}