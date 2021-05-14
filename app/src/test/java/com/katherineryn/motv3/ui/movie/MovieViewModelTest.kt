package com.katherineryn.motv3.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.katherineryn.motv3.data.MotvRepository
import com.katherineryn.motv3.data.source.local.entity.MovieEntity
import com.katherineryn.motv3.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {
    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var motvRepository: MotvRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(motvRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovies = Resource.success(pagedList)
        `when`(dummyMovies.data?.size).thenReturn(3)
        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies.value = dummyMovies

        `when`(motvRepository.getMovies("NEWEST")).thenReturn(movies)
        val movieEntities = viewModel.getMovies("NEWEST").value?.data
        verify(motvRepository).getMovies("NEWEST")
        assertNotNull(movieEntities)
        assertEquals(3, movieEntities?.size)

        viewModel.getMovies("NEWEST").observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}