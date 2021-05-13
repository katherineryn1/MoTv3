package com.katherineryn.motv3.data.source.remote.response.movie

import com.google.gson.annotations.SerializedName
import com.katherineryn.motv3.data.source.remote.response.genre.GenreResponse

data class MovieDetailResponse (
    @SerializedName("genres")
    val genres: List<GenreResponse>? = null,

    @SerializedName("id")
    val id: Int,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("poster_path")
    val poster: String? = null,

    @SerializedName("release_date")
    val release: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("tagline")
    val tagline: String? = null,

    @SerializedName("vote_average")
    val voteAverage: Double? = null
)