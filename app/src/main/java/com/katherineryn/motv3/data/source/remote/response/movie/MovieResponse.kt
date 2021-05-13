package com.katherineryn.motv3.data.source.remote.response.movie

import com.google.gson.annotations.SerializedName

data class MovieResponse (
    @SerializedName("id")
    val movieId: Int,

    @SerializedName("genre_ids")
    val genreIds: List<Int>,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("poster_path")
    val poster: String? = null,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("vote_average")
    val voteAverage: Double? = null
)