package com.katherineryn.motv3.ui.favorite.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.katherineryn.motv3.data.MotvRepository
import com.katherineryn.motv3.data.source.local.entity.TvShowEntity
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
class TvShowFavViewModelTest {

    private lateinit var viewModel: TvShowFavViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var motvRepository: MotvRepository

    @Mock
    private lateinit var observer: Observer<PagedList<TvShowEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = TvShowFavViewModel(motvRepository)
    }

    @Test
    fun getFavTvShows() {
        val dummyTvShows = pagedList
        `when`(dummyTvShows.size).thenReturn(3)
        val tvShows = MutableLiveData<PagedList<TvShowEntity>>()
        tvShows.value = dummyTvShows

        `when`(motvRepository.getFavTvShows()).thenReturn(tvShows)
        val tvShowEntitites = viewModel.getFavTvShows().value
        verify(motvRepository).getFavTvShows()
        assertNotNull(tvShowEntitites)
        assertEquals(3, tvShowEntitites?.size)

        viewModel.getFavTvShows().observeForever(observer)
        verify(observer).onChanged(dummyTvShows)
    }

    @Test
    fun setFavTvShow() {
        viewModel.setFavTvShow(DataDummy.dummyDetailTvShow())
        verify(motvRepository).setFavTvShow(DataDummy.dummyDetailTvShow(), true)
        verifyNoMoreInteractions(motvRepository)
    }
}