package com.katherineryn.motv3.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.katherineryn.motv3.data.MotvRepository
import com.katherineryn.motv3.data.source.local.entity.MovieEntity
import com.katherineryn.motv3.data.source.local.entity.TvShowEntity
import com.katherineryn.motv3.ui.detail.DetailViewModel.Companion.MOVIE
import com.katherineryn.motv3.ui.detail.DetailViewModel.Companion.TV_SHOW
import com.katherineryn.motv3.utils.DataDummy
import com.katherineryn.motv3.vo.Resource
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel

    private val dummyMovie = DataDummy.dummyDetailMovie()
    private val dummyMovieId = dummyMovie.id

    private val dummyTvShow = DataDummy.dummyDetailTvShow()
    private val dummyTvShowId = dummyTvShow.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var motvRepository: MotvRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var tvShowObserver: Observer<Resource<TvShowEntity>>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(motvRepository)
    }

    @Test
    fun getDetailMovie() {
        val dummyDetailMovie = Resource.success(DataDummy.dummyDetailMovie())
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyDetailMovie

        `when`(motvRepository.getMovieDetail(dummyMovieId)).thenReturn(movie)

        viewModel.setItem(dummyMovieId.toString(), MOVIE)
        viewModel.getDetailMovie().observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyDetailMovie)
    }

    @Test
    fun setFavMovie() {
        val dummyDetailMovie = Resource.success(DataDummy.dummyDetailMovie())
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyDetailMovie

        `when`(motvRepository.getMovieDetail(dummyMovieId)).thenReturn(movie)

        viewModel.setItem(dummyMovieId.toString(), MOVIE)
        viewModel.setFavMovie()
        verify(motvRepository).setFavMovie(movie.value!!.data as MovieEntity, true)
        verifyNoMoreInteractions(movieObserver)
    }

    @Test
    fun getDetailTvShow() {
        val dummyDetailTvShow = Resource.success(DataDummy.dummyDetailTvShow())
        val tvShow = MutableLiveData<Resource<TvShowEntity>>()
        tvShow.value = dummyDetailTvShow

        `when`(motvRepository.getTvShowDetail(dummyTvShowId)).thenReturn(tvShow)

        viewModel.setItem(dummyTvShowId.toString(), TV_SHOW)
        viewModel.getDetailTvShow().observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyDetailTvShow)
    }

    @Test
    fun setFavTvShow() {
        val dummyDetailTvShow = Resource.success(DataDummy.dummyDetailTvShow())
        val tvShow = MutableLiveData<Resource<TvShowEntity>>()
        tvShow.value = dummyDetailTvShow

        `when`(motvRepository.getTvShowDetail(dummyMovieId)).thenReturn(tvShow)

        viewModel.setItem(dummyTvShowId.toString(), TV_SHOW)
        viewModel.setFavTvShow()
        verify(motvRepository).setFavTvShow(tvShow.value!!.data as TvShowEntity, true)
        verifyNoMoreInteractions(movieObserver)
    }
}