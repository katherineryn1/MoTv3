package com.katherineryn.motv3.utils

import com.katherineryn.motv3.data.source.local.entity.MovieEntity
import com.katherineryn.motv3.data.source.local.entity.TvShowEntity
import com.katherineryn.motv3.data.source.remote.response.genre.GenreResponse
import com.katherineryn.motv3.data.source.remote.response.movie.MovieDetailResponse
import com.katherineryn.motv3.data.source.remote.response.movie.MovieResponse
import com.katherineryn.motv3.data.source.remote.response.tvshow.TvShowDetailResponse
import com.katherineryn.motv3.data.source.remote.response.tvshow.TvShowResponse

object DataDummy {
    /**
     *
     * NOTE :
     * DATA DUMMY IS DIFFERENT WITH THE REAL MOVIE DATA
     *
     **/

    fun generateDummyMovies(): List<MovieEntity> {
        return listOf(
            MovieEntity(
                3,
                "Genre 1, Genre 2, Genre 3",
                "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
                "/xGuOF1T3WmPsAcQEQJfnG7Ud9f8.jpg",
                "2021-04-07",
                "Mortal Kombat",
                "Tagline example",
                7.8,
                false
            ),
            MovieEntity(
                2,
                "Genre 1",
                "Overview Godzilla vs. Kong example",
                "/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg",
                "2020-02-02",
                "Godzilla vs. Kong",
                "Tagline example",
                8.2,
                false
            ),
            MovieEntity(
                1,
                "Genre 4, Genre 2, Genre 7",
                "Overview Vanquish",
                "/AoWY1gkcNzabh229Icboa1Ff0BM.jpg",
                "2020-10-10",
                "Vanquish",
                "Tagline example",
                6.1,
                false
            )
        )
    }

    fun dummyDetailMovie(): MovieEntity {
        return MovieEntity (
            3,
            "Genre 1, Genre 2, Genre 3",
            "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
            "/xGuOF1T3WmPsAcQEQJfnG7Ud9f8.jpg",
            "2021-04-07",
            "Mortal Kombat",
            "Tagline example",
            7.8,
            false
        )
    }

    fun generateDummyTvShows(): List<TvShowEntity> {
        return listOf(
            TvShowEntity(
                3,
                "Genre 4",
                "Overview The Good Doctor",
                "/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg",
                "2017-09-25",
                "The Good Doctor",
                "Tagline example",
                8.6,
                false
            ),
            TvShowEntity(
                2,
                "Genre 5, Genre 2, Genre 7, Genre 10",
                "Overview The Flash",
                "/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg",
                "2021-05-10",
                "The Flash",
                "Tagline example",
                7.7,
                false
            ),
            TvShowEntity(
                1,
                "Genre 1",
                "Overview Grey's Anatomy",
                "/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg",
                "2018-10-20",
                "Grey's Anatomy",
                "Tagline example",
                8.2,
                false
            )
        )
    }

    fun dummyDetailTvShow(): TvShowEntity {
        return TvShowEntity(
            3,
            "Genre 4",
            "Overview The Good Doctor",
            "/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg",
            "2017-09-25",
            "The Good Doctor",
            "Tagline example",
            8.6,
            false
        )
    }

    /**
     *
     * REMOTE DATA DUMMY
     *
     **/

    fun generateRemoteDummyMovies(): List<MovieResponse> {
        return listOf(
            MovieResponse(
                3,
                listOf(1, 2, 3),
                "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
                "/xGuOF1T3WmPsAcQEQJfnG7Ud9f8.jpg",
                "2021-04-07",
                "Mortal Kombat",
                7.8
            ),
            MovieResponse(
                2,
                listOf(1),
                "Overview Godzilla vs. Kong example",
                "/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg",
                "2020-02-02",
                "Godzilla vs. Kong",
                8.2,
            ),
            MovieResponse(
                1,
                listOf(4, 2, 7),
                "Overview Vanquish",
                "/AoWY1gkcNzabh229Icboa1Ff0BM.jpg",
                "2020-10-10",
                "Vanquish",
                6.1
            )
        )
    }

    fun dummyRemoteDetailMovie(): MovieDetailResponse {
        return MovieDetailResponse(
            genres = listOf(
                GenreResponse(
                    1,
                    "Genre 1"
                ),
                GenreResponse(
                    2,
                    "Genre 2"
                ),
                GenreResponse(
                    3,
                    "Genre 3"
                )
            ),
            3,
            "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
            "/xGuOF1T3WmPsAcQEQJfnG7Ud9f8.jpg",
            "2021-04-07",
            "Mortal Kombat",
            "Tagline example",
            7.8
        )
    }

    fun generateRemoteDummyTvShows(): List<TvShowResponse> {
        return listOf(
            TvShowResponse(
                3,
                listOf(4),
                "2017-09-25",
                "The Good Doctor",
                "Overview The Good Doctor",
                "/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg",
                8.6
            ),
            TvShowResponse(
                2,
                listOf(5, 2, 7, 10),
                "2021-05-10",
                "The Flash",
                "Overview The Flash",
                "/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg",
                7.7
            ),
            TvShowResponse(
                1,
                listOf(1),
                "2018-10-20",
                "Grey's Anatomy",
                "Overview Grey's Anatomy",
                "/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg",
                8.2
            )
        )
    }

    fun dummyRemoteDetailTvShow(): TvShowDetailResponse {
        return TvShowDetailResponse(
            "2017-09-25",
            genres = listOf(
                GenreResponse(
                    4,
                    "Genre 4"
                )
            ),
            3,
            "The Good Doctor",
            "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives",
            "/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg",
            "Tagline example",
            8.6
        )
    }
}