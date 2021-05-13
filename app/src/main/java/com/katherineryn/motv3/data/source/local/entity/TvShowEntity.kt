package com.katherineryn.motv3.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvshowentities")
data class TvShowEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id") // column name same as movie id, so it can use the same function in filter
    val id: Int,

    @ColumnInfo(name = "genre")
    var genre: String? = null,

    @ColumnInfo(name = "overview")
    var overview: String? = null,

    @ColumnInfo(name = "poster")
    var poster: String? = null,

    @ColumnInfo(name = "releaseDate")
    var releaseDate: String? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "tagline")
    var tagline: String? = null,

    @ColumnInfo(name = "voteAverage")
    var voteAverage: Double? = null, // var name changed to eliminate confusion between table and API

    @ColumnInfo(name = "isFav")
    var isFav: Boolean = false
)