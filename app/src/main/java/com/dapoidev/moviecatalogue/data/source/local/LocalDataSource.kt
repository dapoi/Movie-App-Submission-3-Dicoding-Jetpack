package com.dapoidev.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.dapoidev.moviecatalogue.data.source.local.model.MovieEntity
import com.dapoidev.moviecatalogue.data.source.local.model.TVShowEntity
import com.dapoidev.moviecatalogue.data.source.local.room.FilmDao

class LocalDataSource private constructor(private val filmDao: FilmDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(filmDao: FilmDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(filmDao).apply {
                INSTANCE = this
            }
    }

    fun getAllDataMovie(): DataSource.Factory<Int, MovieEntity> =
        filmDao.getMovies()

    fun getAllDataTVShow(): DataSource.Factory<Int, TVShowEntity> =
        filmDao.getTVShows()

    fun getFavMovies(): DataSource.Factory<Int, MovieEntity> = filmDao.getFavMovies()

    fun getFavTVShows(): DataSource.Factory<Int, TVShowEntity> = filmDao.getFavTVShows()

    fun getMovieById(id: Int): LiveData<MovieEntity> = filmDao.getMoviesById(id)

    fun getTVShowById(id: Int): LiveData<TVShowEntity> = filmDao.getTVShowsById(id)

    fun insertMovie(movies: List<MovieEntity>) = filmDao.insertMovie(movies)

    fun insertTVShow(tvShow: List<TVShowEntity>) = filmDao.insertTVShow(tvShow)

    fun updateFavMovie(movies: MovieEntity, newState: Boolean) {
        movies.addFav = newState
        filmDao.updateMovie(movies)
    }

    fun updateFavTVShow(tvShow: TVShowEntity, newState: Boolean) {
        tvShow.addFav = newState
        filmDao.updateTVShow(tvShow)
    }
}