package com.dapoidev.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dapoidev.moviecatalogue.data.source.local.model.MovieEntity
import com.dapoidev.moviecatalogue.data.source.local.model.TVShowEntity
import com.dapoidev.moviecatalogue.data.source.IFilmCatalogueRepository
import com.dapoidev.moviecatalogue.vo.Resource

class FilmViewModel(private val filmCatalogueRepository: IFilmCatalogueRepository) : ViewModel() {

    fun getListMovies(): LiveData<Resource<PagedList<MovieEntity>>> =
        filmCatalogueRepository.loadMovies()

    fun getListTVShows(): LiveData<Resource<PagedList<TVShowEntity>>> =
        filmCatalogueRepository.loadTVShows()
}