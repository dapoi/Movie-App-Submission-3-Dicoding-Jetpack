package com.dapoidev.moviecatalogue.data.source.remote.model.movie

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("results")
    val result: List<MovieResponse>
)
