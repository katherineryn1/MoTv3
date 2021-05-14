package com.katherineryn.motv3.ui.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.katherineryn.motv3.data.MotvRepository
import com.katherineryn.motv3.data.source.local.entity.MovieEntity
import com.katherineryn.motv3.utils.DataDummy
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieFavViewModelTest {

    private lateinit var viewModel: MovieFavViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var motvRepository: MotvRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        viewModel = MovieFavViewModel(motvRepository)
    }

    @Test
    fun getFavMovies() {
        val dummyMovies = pagedList
        `when`(dummyMovies.size).thenReturn(3)
        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dummyMovies

        `when`(motvRepository.getFavMovies()).thenReturn(movies)
        val movieEntitites = viewModel.getFavMovies().value
        verify(motvRepository).getFavMovies()
        assertNotNull(movieEntitites)
        assertEquals(3, movieEntitites?.size)

        viewModel.getFavMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun setFavMovie() {
        viewModel.setFavMovie(DataDummy.dummyDetailMovie())
        verify(motvRepository).setFavMovie(DataDummy.dummyDetailMovie(), true)
        verifyNoMoreInteractions(motvRepository)
    }
}