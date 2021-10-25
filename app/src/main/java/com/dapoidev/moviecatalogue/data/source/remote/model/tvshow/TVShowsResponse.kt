package com.dapoidev.moviecatalogue.data.source.remote.model.tvshow

import com.google.gson.annotations.SerializedName

data class TVShowsResponse(
    @SerializedName("results")
    val result: List<TVShowResponse>
)