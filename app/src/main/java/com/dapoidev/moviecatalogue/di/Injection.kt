package com.dapoidev.moviecatalogue.di

import android.content.Context
import com.dapoidev.moviecatalogue.model.data.remote.repository.FilmCatalogueRepository
import com.dapoidev.moviecatalogue.model.data.room.FilmDatabase
import com.dapoidev.moviecatalogue.model.data.source.RemoteDataSource
import com.dapoidev.moviecatalogue.model.data.source.local.LocalDataSource
import com.dapoidev.moviecatalogue.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FilmCatalogueRepository {

        val db = FilmDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(db.filmDao())
        val appExecutors = AppExecutors()

        return FilmCatalogueRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}