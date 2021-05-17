package com.dapoidev.moviecatalogue.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dapoidev.moviecatalogue.model.data.entity.MovieEntity
import com.dapoidev.moviecatalogue.model.data.entity.TVShowEntity
import com.dapoidev.moviecatalogue.model.data.remote.repository.FilmCatalogueRepository
import com.dapoidev.moviecatalogue.utils.DataDetailDummy
import com.dapoidev.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailFilmViewModelTest {

    private lateinit var viewModel: DetailFilmViewModel

    private val dummyDetailMovie = DataDetailDummy.getDetailMovie()
    private val dummyDetailTVShow = DataDetailDummy.getDetailTVShow()

    private val movieID = dummyDetailMovie.id
    private val tvShowID = dummyDetailTVShow.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmCatalogueRepository: FilmCatalogueRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var tvShowObserver: Observer<Resource<TVShowEntity>>

    @Before
    fun setUp() {
        viewModel = DetailFilmViewModel(filmCatalogueRepository)
    }

    @Test
    fun getDetailMovie() {
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = Resource.success(DataDetailDummy.getDetailMovie())

        `when`(filmCatalogueRepository.loadDetailMovies(movieID)).thenReturn(movie)
        viewModel.setDataMovie(movieID).observeForever(movieObserver)
        verify(movieObserver).onChanged(movie.value)
    }

    @Test
    fun getDetailTVShow() {
        val tvShow = MutableLiveData<Resource<TVShowEntity>>()
        tvShow.value = Resource.success(DataDetailDummy.getDetailTVShow())

        `when`(filmCatalogueRepository.loadDetailTVShows(tvShowID)).thenReturn(tvShow)
        viewModel.setDataTVShow(tvShowID).observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(tvShow.value)
    }

    @Test
    fun setMovieFav() {
        val movies = MutableLiveData<Resource<MovieEntity>>()
        movies.value = Resource.success(DataDetailDummy.getDetailMovie())

        `when`(filmCatalogueRepository.loadDetailMovies(movieID)).thenReturn(movies)

        viewModel.setDataMovie(movieID).observeForever(movieObserver)
        viewModel.setMovieFavorite()
        verify(filmCatalogueRepository).setMoviesFav((movies.value?.data) as MovieEntity, true)
    }

    @Test
    fun setTVShowFav() {
        val tvShow = MutableLiveData<Resource<TVShowEntity>>()
        tvShow.value = Resource.success(DataDetailDummy.getDetailTVShow())

        `when`(filmCatalogueRepository.loadDetailTVShows(tvShowID)).thenReturn(tvShow)

        viewModel.setDataTVShow(tvShowID).observeForever(tvShowObserver)
        viewModel.setTVShowFavorite()
        verify(filmCatalogueRepository).setTVShowsFav((tvShow.value?.data) as TVShowEntity, true)
    }
}