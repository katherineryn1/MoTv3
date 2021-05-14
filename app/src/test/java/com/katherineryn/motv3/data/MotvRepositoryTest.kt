package com.katherineryn.motv3.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.katherineryn.motv3.data.source.local.LocalDataSource
import com.katherineryn.motv3.data.source.local.entity.MovieEntity
import com.katherineryn.motv3.data.source.local.entity.TvShowEntity
import com.katherineryn.motv3.data.source.remote.RemoteDataSource
import com.katherineryn.motv3.utils.AppExecutors
import com.katherineryn.motv3.utils.DataDummy
import com.katherineryn.motv3.utils.LiveDataTestUtil
import com.katherineryn.motv3.utils.PagedListUtil
import com.katherineryn.motv3.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MotvRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val motvRepository = FakeMotvRepository(remote, local, appExecutors)

    private val movieResponses = DataDummy.generateRemoteDummyMovies()
    private val movieId = movieResponses[0].id
    private val movieDetail = DataDummy.dummyRemoteDetailMovie()

    private val tvShowResponses = DataDummy.generateRemoteDummyTvShows()
    private val tvShowId = tvShowResponses[0].id
    private val tvShowDetail = DataDummy.dummyRemoteDetailTvShow()

    @Test
    fun getMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies("NEWEST")).thenReturn(dataSourceFactory)
        motvRepository.getMovies("NEWEST")

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getAllMovies("NEWEST")
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getMovieDetail() {
        val dummyMovie = MutableLiveData<MovieEntity>()
        dummyMovie.value = DataDummy.dummyDetailMovie()
        `when`(local.getMovieById(movieId)).thenReturn(dummyMovie)

        val movieDetailEntity = LiveDataTestUtil.getValue(motvRepository.getMovieDetail(movieId))
        verify(local).getMovieById(movieId)
        assertNotNull(movieDetailEntity)
        assertEquals(movieDetail.id, movieDetailEntity.data?.id)
    }

    @Test
    fun getFavMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getFavMovies()).thenReturn(dataSourceFactory)
        motvRepository.getFavMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getFavMovies()
        assertNotNull(movieEntities)
        assertEquals(movieResponses.size, movieEntities.data?.size)
    }

    @Test
    fun setFavMovie() {
        motvRepository.setFavMovie(DataDummy.dummyDetailMovie(), true)
        verify(local).setFavMovie(DataDummy.dummyDetailMovie(), true)
        verifyNoMoreInteractions(local)
    }

    @Test
    fun getTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getAllTvShows("NEWEST")).thenReturn(dataSourceFactory)
        motvRepository.getTvShows("NEWEST")

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShows()))
        verify(local).getAllTvShows("NEWEST")
        assertNotNull(tvShowEntities.data)
        assertEquals(tvShowResponses.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun getTvShowDetail() {
        val dummyTvShow = MutableLiveData<TvShowEntity>()
        dummyTvShow.value = DataDummy.dummyDetailTvShow()
        `when`(local.getTvShowById(tvShowId)).thenReturn(dummyTvShow)

        val tvShowDetailEntity = LiveDataTestUtil.getValue(motvRepository.getTvShowDetail(tvShowId))
        verify(local).getTvShowById(tvShowId)
        assertNotNull(tvShowDetailEntity)
        assertEquals(tvShowDetail.id, tvShowDetailEntity.data?.id)
    }

    @Test
    fun getFavTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getFavTvShows()).thenReturn(dataSourceFactory)
        motvRepository.getFavTvShows()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShows()))
        verify(local).getFavTvShows()
        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponses.size, tvShowEntities.data?.size)
    }

    @Test
    fun setFavTvShow() {
        motvRepository.setFavTvShow(DataDummy.dummyDetailTvShow(), true)
        verify(local).setFavTvShow(DataDummy.dummyDetailTvShow(), true)
        verifyNoMoreInteractions(local)
    }
}