package com.dapoidev.moviecatalogue.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dapoidev.moviecatalogue.data.source.local.model.MovieEntity
import com.dapoidev.moviecatalogue.data.source.local.model.TVShowEntity
import com.dapoidev.moviecatalogue.data.source.IFilmCatalogueRepository
import com.dapoidev.moviecatalogue.data.utils.DataDetailDummy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    private lateinit var viewModel: FavoriteViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmCatalogueRepository: IFilmCatalogueRepository

    @Mock
    private lateinit var movieObserver: Observer<PagedList<MovieEntity>>
    @Mock
    private lateinit var tvShowObserver: Observer<PagedList<TVShowEntity>>

    @Mock
    private lateinit var moviePagedList: PagedList<MovieEntity>
    @Mock
    private lateinit var tvShowPagedList: PagedList<TVShowEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteViewModel(filmCatalogueRepository)
    }

    @Test
    fun setFavListMovies() {
        viewModel.setFavListMovie(DataDetailDummy.getDetailMovie())
        verify(filmCatalogueRepository).setMoviesFav(DataDetailDummy.getDetailMovie(), true)
        verifyNoMoreInteractions(filmCatalogueRepository)
    }

    @Test
    fun setFavListTVShows() {
        viewModel.setFavListTVShow(DataDetailDummy.getDetailTVShow())
        verify(filmCatalogueRepository).setTVShowsFav(DataDetailDummy.getDetailTVShow(), true)
        verifyNoMoreInteractions(filmCatalogueRepository)
    }

    @Test
    fun getFavListMovies() {
        val dummyMovie = moviePagedList
        `when`(dummyMovie.size).thenReturn(5)
        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dummyMovie

        `when`(filmCatalogueRepository.getMoviesFav()).thenReturn(movies)
        val favMovie = viewModel.getFavListMovie().value
        verify(filmCatalogueRepository).getMoviesFav()
        Assert.assertNotNull(favMovie)
        Assert.assertEquals(5, favMovie?.size)

        viewModel.getFavListMovie().observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovie)
    }

    @Test
    fun getFavListTVShow() {
        val dummyTVShow = tvShowPagedList
        `when`(dummyTVShow.size).thenReturn(5)
        val tvShows = MutableLiveData<PagedList<TVShowEntity>>()
        tvShows.value = dummyTVShow

        `when`(filmCatalogueRepository.getTVShowsFav()).thenReturn(tvShows)
        val favTVShow = viewModel.getFavListTVShow().value
        verify(filmCatalogueRepository).getTVShowsFav()
        Assert.assertNotNull(favTVShow)
        Assert.assertEquals(5, favTVShow?.size)

        viewModel.getFavListTVShow().observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyTVShow)
    }

}