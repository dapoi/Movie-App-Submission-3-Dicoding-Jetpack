package com.dapoidev.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dapoidev.moviecatalogue.data.source.local.model.MovieEntity
import com.dapoidev.moviecatalogue.data.source.local.model.TVShowEntity
import com.dapoidev.moviecatalogue.data.source.IFilmCatalogueRepository

class FavoriteViewModel(private val filmCatalogueRepository: IFilmCatalogueRepository) : ViewModel() {

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