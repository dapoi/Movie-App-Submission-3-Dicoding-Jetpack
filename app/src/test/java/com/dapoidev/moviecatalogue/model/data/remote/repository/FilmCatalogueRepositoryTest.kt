package com.dapoidev.moviecatalogue.model.data.remote.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dapoidev.moviecatalogue.model.data.entity.MovieEntity
import com.dapoidev.moviecatalogue.model.data.entity.TVShowEntity
import com.dapoidev.moviecatalogue.model.data.source.RemoteDataSource
import com.dapoidev.moviecatalogue.model.data.source.local.LocalDataSource
import com.dapoidev.moviecatalogue.utils.*
import com.dapoidev.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@Suppress("UNCHECKED_CAST")
class FilmCatalogueRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val filmCatalogueRepository = FakeFilmCatalogueRepository(remote, local, appExecutors)

    private val responseMovie = DataDummy.getRemoteMovies()
    private val responseTVShow = DataDummy.getRemoteTVShows()

    private val idMovie = responseMovie[0].id
    private val idTVShow = responseTVShow[0].id

    private val detailMovie = DataDetailDummy.getRemoteDetailMovie()
    private val detailTVShow = DataDetailDummy.getRemoteDetailTVShow()

    @Test
    fun getListMovies() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllDataMovie()).thenReturn(dataSourceFactory)
        filmCatalogueRepository.loadMovies()

        val movieEntity = Resource.success(PagedListUtil.mockPagedList(DataDummy.getMovies()))
        verify(local).getAllDataMovie()
        assertNotNull(movieEntity.data)
        assertEquals(responseMovie.size.toLong(), movieEntity.data?.size?.toLong())
    }

    @Test
    fun getListTVShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TVShowEntity>
        `when`(local.getAllDataTVShow()).thenReturn(dataSourceFactory)
        filmCatalogueRepository.loadTVShows()

        val tvShowEntity = Resource.success(PagedListUtil.mockPagedList(DataDummy.getTVShows()))
        verify(local).getAllDataTVShow()
        assertNotNull(tvShowEntity)
        assertEquals(responseTVShow.size.toLong(), tvShowEntity.data?.size?.toLong())
    }

    @Test
    fun getDetailMovies() {
        val dummyDetail = MutableLiveData<MovieEntity>()
        dummyDetail.value = DataDetailDummy.getDetailMovie()
        `when`(local.getMovieById(idMovie)).thenReturn(dummyDetail)

        val movieDetailEntity =
            LiveDataTestUtil.getValue(filmCatalogueRepository.loadDetailMovies(idMovie))
        verify(local).getMovieById(idMovie)
        assertNotNull(movieDetailEntity)
        assertEquals(detailMovie.id, movieDetailEntity.data?.id)
    }

    @Test
    fun getDetailTVShows() {
        val dummyDetail = MutableLiveData<TVShowEntity>()
        dummyDetail.value = DataDetailDummy.getDetailTVShow()
        `when`(local.getTVShowById(idTVShow)).thenReturn(dummyDetail)

        val tvShowDetailEntity =
            LiveDataTestUtil.getValue(filmCatalogueRepository.loadDetailTVShows(idTVShow))
        verify(local).getTVShowById(idTVShow)
        assertNotNull(tvShowDetailEntity)
        assertEquals(detailTVShow.id, tvShowDetailEntity.data?.id)
    }

    @Test
    fun setFavMovie() {
        filmCatalogueRepository.setMoviesFav(DataDetailDummy.getDetailMovie(), true)
        verify(local).updateFavMovie(DataDetailDummy.getDetailMovie(), true)
        verifyNoMoreInteractions(local)
    }

    @Test
    fun getFavMovie() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getFavMovies()).thenReturn(dataSourceFactory)
        filmCatalogueRepository.getMoviesFav()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.getMovies()))
        verify(local).getFavMovies()
        assertNotNull(movieEntities)
        assertEquals(responseMovie.size, movieEntities.data?.size)
    }
}