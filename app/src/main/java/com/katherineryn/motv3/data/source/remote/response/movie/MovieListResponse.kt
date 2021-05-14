package com.katherineryn.motv3.data.source.remote.response.movie

import com.google.gson.annotations.SerializedName

data class MovieListResponse (
    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("results")
    val movieList: List<MovieResponse>? = null,

    @SerializedName("total_pages")
    val totalPages: Int? = null,

    @SerializedName("total_results")
    val totalResults: Int? = null
)