package com.katherineryn.motv3.data.source.remote.response.tvshow

import com.google.gson.annotations.SerializedName
import com.katherineryn.motv3.data.source.remote.response.genre.GenreResponse

data class TvShowDetailResponse (
    @SerializedName("first_air_date")
    val release: String? = null,

    @SerializedName("genres")
    val genres: List<GenreResponse>? = null,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("poster_path")
    val poster: String? = null,

    @SerializedName("tagline")
    val tagline: String? = null,

    @SerializedName("vote_average")
    val voteAverage: Double? = null
)