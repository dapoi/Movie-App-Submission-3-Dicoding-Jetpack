package com.dapoidev.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dapoidev.moviecatalogue.data.source.local.model.MovieEntity
import com.dapoidev.moviecatalogue.data.source.local.model.TVShowEntity
import com.dapoidev.moviecatalogue.data.source.FilmRepository

class FavoriteViewModel(private val filmRepository: FilmRepository) : ViewModel() {

    fun getFavListMovie(): LiveData<PagedList<MovieEntity>> = filmRepository.getMoviesFav()

    fun getFavListTVShow(): LiveData<PagedList<TVShowEntity>> = filmRepository.getTVShowsFav()

    fun setFavListMovie(movieEntity: MovieEntity) {
        val newState = !movieEntity.addFav
        filmRepository.setMoviesFav(movieEntity, newState)
    }

    fun setFavListTVShow(tvShowEntity: TVShowEntity) {
        val newState = !tvShowEntity.addFav
        filmRepository.setTVShowsFav(tvShowEntity, newState)
    }
}