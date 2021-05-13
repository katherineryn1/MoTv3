package com.katherineryn.motv3.data.source.remote.response.tvshow

import com.google.gson.annotations.SerializedName

data class TvShowResponse (
    @SerializedName("id")
    val id: Int,

    @SerializedName("first_air_date")
    val releaseDate: String,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("poster_path")
    val poster: String? = null,

    @SerializedName("vote_average")
    val voteAverage: Double? = null
)