package com.katherineryn.motv3.data.source.remote.response.tvshow

import com.google.gson.annotations.SerializedName

data class TvShowListResponse (
    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("results")
    val tvShowList: List<TvShowResponse>? = null,

    @SerializedName("total_pages")
    val totalPages: Int? = null,

    @SerializedName("total_results")
    val totalResults: Int? = null
)