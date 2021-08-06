package com.dapoidev.moviecatalogue.model.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dapoidev.moviecatalogue.model.data.entity.MovieEntity
import com.dapoidev.moviecatalogue.model.data.entity.TVShowEntity

@Database(entities = [MovieEntity::class, TVShowEntity::class], version = 4, exportSchema = false)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao

    companion object {

        @Volatile
        private var INSTANCE: FilmDatabase? = null

        fun getInstance(context: Context): FilmDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    FilmDatabase::class.java,
                    "film.db"
                ).fallbackToDestructiveMigration().build().apply { INSTANCE = this }
            }
    }
}