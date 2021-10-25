package com.dapoidev.moviecatalogue.data.source.local.model

import com.google.gson.annotations.SerializedName

data class DetailEntity(
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