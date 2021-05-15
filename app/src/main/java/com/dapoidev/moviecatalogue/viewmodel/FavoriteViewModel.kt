package com.dapoidev.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dapoidev.moviecatalogue.model.data.entity.MovieEntity
import com.dapoidev.moviecatalogue.model.data.entity.TVShowEntity
import com.dapoidev.moviecatalogue.model.data.remote.repository.FilmCatalogueRepository

class FavoriteViewModel(private val filmCatalogueRepository: FilmCatalogueRepository) : ViewModel() {

    fun getFavListMovie(): LiveData<PagedList<MovieEntity>> = filmCatalogueRepository.getMoviesFav()

    fun getFavListTVShow(): LiveData<PagedList<TVShowEntity>> = filmCatalogueRepository.getTVShowsFav()

    fun setFavListMovie(movieEntity: MovieEntity) {
        val newState = !movieEntity.addFav
        filmCatalogueRepository.setMoviesFav(movieEntity, newState)
    }

    fun setFavListTVShow(tvShowEntity: TVShowEntity) {
        val newState = !tvShowEntity.addFav
        filmCatalogueRepository.setTVShowsFav(tvShowEntity, newState)
    }
}