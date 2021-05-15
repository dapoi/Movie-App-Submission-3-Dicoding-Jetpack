package com.dapoidev.moviecatalogue.model.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dapoidev.moviecatalogue.model.data.entity.MovieEntity
import com.dapoidev.moviecatalogue.model.data.entity.TVShowEntity
import com.dapoidev.moviecatalogue.vo.Resource

interface FilmDataSource {
    fun loadMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun loadTVShows(): LiveData<Resource<PagedList<TVShowEntity>>>

    fun loadDetailMovies(movieID: Int): LiveData<Resource<MovieEntity>>
    fun loadDetailTVShows(tvShowID: Int): LiveData<Resource<TVShowEntity>>

    fun setMoviesFav(movie: MovieEntity, state: Boolean)
    fun setTVShowsFav(tvShow: TVShowEntity, state: Boolean)

    fun getMoviesFav(): LiveData<PagedList<MovieEntity>>
    fun getTVShowsFav(): LiveData<PagedList<TVShowEntity>>
}