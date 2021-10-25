package com.dapoidev.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dapoidev.moviecatalogue.data.source.local.model.MovieEntity
import com.dapoidev.moviecatalogue.data.source.local.model.TVShowEntity
import com.dapoidev.moviecatalogue.data.source.FilmRepository
import com.dapoidev.moviecatalogue.vo.Resource

class FilmViewModel(private val filmRepository: FilmRepository) : ViewModel() {

    fun getListMovies(): LiveData<Resource<PagedList<MovieEntity>>> =
        filmRepository.loadMovies()

    fun getListTVShows(): LiveData<Resource<PagedList<TVShowEntity>>> =
        filmRepository.loadTVShows()
}