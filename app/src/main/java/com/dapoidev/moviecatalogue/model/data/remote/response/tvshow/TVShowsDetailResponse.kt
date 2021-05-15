package com.dapoidev.moviecatalogue.model.data.remote.response.tvshow

import com.google.gson.annotations.SerializedName

data class TVShowsDetailResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_name")
    val title: String,
    @SerializedName("first_air_date")
    val date: String,
    @SerializedName("poster_path")
    val imageDetail: String,
    @SerializedName("overview")
    val desc: String
)
