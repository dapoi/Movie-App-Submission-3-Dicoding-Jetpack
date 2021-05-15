package com.dapoidev.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dapoidev.moviecatalogue.model.data.entity.MovieEntity
import com.dapoidev.moviecatalogue.model.data.entity.TVShowEntity
import com.dapoidev.moviecatalogue.model.data.remote.repository.FilmCatalogueRepository
import com.dapoidev.moviecatalogue.vo.Resource

class FilmViewModel(private val filmCatalogueRepository: FilmCatalogueRepository) : ViewModel() {

    fun getListMovies(): LiveData<Resource<PagedList<MovieEntity>>> =
        filmCatalogueRepository.loadMovies()

    fun getListTVShows(): LiveData<Resource<PagedList<TVShowEntity>>> =
        filmCatalogueRepository.loadTVShows()
}