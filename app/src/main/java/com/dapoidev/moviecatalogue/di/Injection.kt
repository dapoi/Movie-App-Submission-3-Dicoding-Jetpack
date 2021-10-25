package com.dapoidev.moviecatalogue.di

import android.content.Context
import com.dapoidev.moviecatalogue.data.source.IFilmCatalogueRepository
import com.dapoidev.moviecatalogue.data.source.local.room.FilmDatabase
import com.dapoidev.moviecatalogue.data.source.remote.RemoteDataSource
import com.dapoidev.moviecatalogue.data.source.local.LocalDataSource
import com.dapoidev.moviecatalogue.data.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): IFilmCatalogueRepository {

        val db = FilmDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(db.filmDao())
        val appExecutors = AppExecutors()

        return IFilmCatalogueRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}