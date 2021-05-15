package com.dapoidev.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dapoidev.moviecatalogue.model.data.entity.MovieEntity
import com.dapoidev.moviecatalogue.model.data.entity.TVShowEntity
import com.dapoidev.moviecatalogue.model.data.remote.repository.FilmCatalogueRepository
import com.dapoidev.moviecatalogue.vo.Resource

class DetailFilmViewModel(private val filmCatalogueRepository: FilmCatalogueRepository) :
    ViewModel() {

    private lateinit var dataDetailMovie: LiveData<Resource<MovieEntity>>
    private lateinit var dataDetailTVShow: LiveData<Resource<TVShowEntity>>

    fun setDataMovie(movieId: Int) : LiveData<Resource<MovieEntity>> {
        dataDetailMovie = filmCatalogueRepository.loadDetailMovies(movieId)
        return dataDetailMovie
    }

    fun setDataTVShow(tvShowId: Int) : LiveData<Resource<TVShowEntity>> {
        dataDetailTVShow = filmCatalogueRepository.loadDetailTVShows(tvShowId)
        return dataDetailTVShow
    }

    fun setMovieFavorite() {
        val dataMovie = dataDetailMovie.value
        if (dataMovie?.data != null) {
            val newState = !dataMovie.data.addFav
            filmCatalogueRepository.setMoviesFav(dataMovie.data, newState)
        }
    }

    fun setTVShowFavorite() {
        val dataTVShow = dataDetailTVShow.value
        if (dataTVShow?.data != null) {
            val newState = !dataTVShow.data.addFav
            filmCatalogueRepository.setTVShowsFav(dataTVShow.data, newState)
        }
    }
}