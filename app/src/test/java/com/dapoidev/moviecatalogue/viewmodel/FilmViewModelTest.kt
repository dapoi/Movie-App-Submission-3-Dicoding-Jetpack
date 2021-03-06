package com.dapoidev.moviecatalogue.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dapoidev.moviecatalogue.data.source.local.model.MovieEntity
import com.dapoidev.moviecatalogue.data.source.local.model.TVShowEntity
import com.dapoidev.moviecatalogue.data.source.FilmRepository
import com.dapoidev.moviecatalogue.vo.Resource
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
class FilmViewModelTest {

    private lateinit var viewModel: FilmViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var tvObserver: Observer<Resource<PagedList<TVShowEntity>>>

    @Mock
    private lateinit var moviePagedList: PagedList<MovieEntity>

    @Mock
    private lateinit var tvPagedList: PagedList<TVShowEntity>

    @Before
    fun setUp() {
        viewModel = FilmViewModel(filmRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovie = Resource.success(moviePagedList)
        `when`(dummyMovie.data?.size).thenReturn(5)
        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies.value = dummyMovie

        `when`(filmRepository.loadMovies()).thenReturn(movies)
        val movieEntity = viewModel.getListMovies().value?.data
        verify(filmRepository).loadMovies()
        assertNotNull(movieEntity)
        assertEquals(5, movieEntity?.size)

        viewModel.getListMovies().observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovie)
    }

    @Test
    fun getTVShows() {
        val dummyTVShow = Resource.success(tvPagedList)
        `when`(dummyTVShow.data?.size).thenReturn(5)
        val tvShows = MutableLiveData<Resource<PagedList<TVShowEntity>>>()
        tvShows.value = dummyTVShow

        `when`(filmRepository.loadTVShows()).thenReturn(tvShows)
        val tvShowEntity = viewModel.getListTVShows().value?.data
        verify(filmRepository).loadTVShows()
        assertNotNull(tvShowEntity)
        assertEquals(5, tvShowEntity?.size)

        viewModel.getListTVShows().observeForever(tvObserver)
        verify(tvObserver).onChanged(dummyTVShow)
    }

}