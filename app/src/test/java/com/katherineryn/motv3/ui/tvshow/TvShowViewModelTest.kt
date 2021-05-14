package com.katherineryn.motv3.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.katherineryn.motv3.data.MotvRepository
import com.katherineryn.motv3.data.source.local.entity.TvShowEntity
import com.katherineryn.motv3.vo.Resource
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
class TvShowViewModelTest {
    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var motvRepository: MotvRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvShowEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(motvRepository)
    }

    @Test
    fun getTvShows() {
        val dummyTvShows = Resource.success(pagedList)
        `when`(dummyTvShows.data?.size).thenReturn(3)
        val tvShows = MutableLiveData<Resource<PagedList<TvShowEntity>>>()
        tvShows.value = dummyTvShows

        `when`(motvRepository.getTvShows("NEWEST")).thenReturn(tvShows)
        val tvShowEntities = viewModel.getTvShows("NEWEST").value?.data
        verify(motvRepository).getTvShows("NEWEST")
        assertNotNull(tvShowEntities)
        assertEquals(3, tvShowEntities?.size)

        viewModel.getTvShows("NEWEST").observeForever(observer)
        verify(observer).onChanged(dummyTvShows)
    }
}