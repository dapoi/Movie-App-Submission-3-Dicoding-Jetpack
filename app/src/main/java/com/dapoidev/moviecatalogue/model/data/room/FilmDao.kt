package com.dapoidev.moviecatalogue.model.data.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dapoidev.moviecatalogue.model.data.entity.MovieEntity
import com.dapoidev.moviecatalogue.model.data.entity.TVShowEntity

@Dao
interface FilmDao {

    @Query("SELECT * FROM movie_entity")
    fun getMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tv_show_entity")
    fun getTVShows(): DataSource.Factory<Int, TVShowEntity>

    @Query("SELECT * FROM movie_entity WHERE addFav = 1")
    fun getFavMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tv_show_entity WHERE addFav = 1")
    fun getFavTVShows(): DataSource.Factory<Int, TVShowEntity>

    @Query("SELECT * FROM movie_entity WHERE id = :id")
    fun getMoviesById(id: Int): LiveData<MovieEntity>

    @Query("SELECT * FROM tv_show_entity WHERE id = :id")
    fun getTVShowsById(id: Int): LiveData<TVShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTVShow(tvShow: List<TVShowEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Update
    fun updateTVShow(tvShow: TVShowEntity)
}