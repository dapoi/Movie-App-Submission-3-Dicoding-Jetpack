package com.dapoidev.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dapoidev.moviecatalogue.data.source.local.model.MovieEntity
import com.dapoidev.moviecatalogue.data.source.local.model.TVShowEntity
import com.dapoidev.moviecatalogue.vo.Resource

interface IFilmRepository {
    fun loadMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun loadTVShows(): LiveData<Resource<PagedList<TVShowEntity>>>

    fun loadDetailMovies(movieID: Int): LiveData<Resource<MovieEntity>>
    fun loadDetailTVShows(tvShowID: Int): LiveData<Resource<TVShowEntity>>

    fun setMoviesFav(movie: MovieEntity, state: Boolean)
    fun setTVShowsFav(tvShow: TVShowEntity, state: Boolean)

    fun getMoviesFav(): LiveData<PagedList<MovieEntity>>
    fun getTVShowsFav(): LiveData<PagedList<TVShowEntity>>
}