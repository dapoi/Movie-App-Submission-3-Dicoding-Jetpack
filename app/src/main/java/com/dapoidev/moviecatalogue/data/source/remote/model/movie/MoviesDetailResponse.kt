package com.dapoidev.moviecatalogue.data.source.remote.model.movie

import com.google.gson.annotations.SerializedName

data class MoviesDetailResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("release_date")
    val date: String,
    @SerializedName("poster_path")
    val imageDetail: String,
    @SerializedName("overview")
    val desc: String
)
