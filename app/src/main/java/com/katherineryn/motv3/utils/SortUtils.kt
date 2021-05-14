package com.katherineryn.motv3.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    const val NEWEST = "Newest"
    const val OLDEST = "Oldest"
    const val RANDOM = "Random"
    const val MOVIE_TABLE = "movieentities"
    const val TVSHOW_TABLE = "tvshowentities"

    fun getSortedQuery(filter: String, tableName: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM $tableName ")
        when (filter) {
            NEWEST -> simpleQuery.append("ORDER BY id DESC")
            OLDEST -> simpleQuery.append("ORDER BY id ASC")
            RANDOM -> simpleQuery.append("ORDER BY RANDOM()")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}