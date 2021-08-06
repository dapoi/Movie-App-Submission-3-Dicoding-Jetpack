package com.dapoidev.moviecatalogue.model.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_show_entity")
data class TVShowEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "date")
    val date: String?,

    @ColumnInfo(name = "desc")
    val desc: String?,

    @ColumnInfo(name = "image")
    val image: String?,

    @ColumnInfo(name = "addFav")
    var addFav: Boolean = false
)