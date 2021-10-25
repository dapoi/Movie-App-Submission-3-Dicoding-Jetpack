package com.dapoidev.moviecatalogue.data.source.remote.model.tvshow

import com.google.gson.annotations.SerializedName

data class TVShowResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val title: String,
    @SerializedName("first_air_date")
    val date: String,
    @SerializedName("overview")
    val desc: String,
    @SerializedName("poster_path")
    val image: String
)
