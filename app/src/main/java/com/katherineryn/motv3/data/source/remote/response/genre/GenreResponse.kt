package com.katherineryn.motv3.data.source.remote.response.genre

import com.google.gson.annotations.SerializedName

data class GenreResponse (
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    var name: String? = null
)
