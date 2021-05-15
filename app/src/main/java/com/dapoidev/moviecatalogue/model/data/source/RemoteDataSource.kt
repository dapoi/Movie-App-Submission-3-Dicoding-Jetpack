package com.dapoidev.moviecatalogue.model.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dapoidev.moviecatalogue.model.data.remote.response.ApiResponse
import com.dapoidev.moviecatalogue.model.data.remote.response.movie.MovieRemote
import com.dapoidev.moviecatalogue.model.data.remote.response.movie.MoviesDetailResponse
import com.dapoidev.moviecatalogue.model.data.remote.response.movie.MoviesResponse
import com.dapoidev.moviecatalogue.model.data.remote.response.tvshow.TVShowRemote
import com.dapoidev.moviecatalogue.model.data.remote.response.tvshow.TVShowsDetailResponse
import com.dapoidev.moviecatalogue.model.data.remote.response.tvshow.TVShowsResponse
import com.dapoidev.moviecatalogue.model.network.RetrofitClient
import com.dapoidev.moviecatalogue.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    companion object {
        const val TAG = "Remote Data Source"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource = instance ?: synchronized(this) {
            RemoteDataSource().apply { instance = this }
        }
    }

    fun getListMovies(): LiveData<ApiResponse<List<MovieRemote>>> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<ApiResponse<List<MovieRemote>>>()
        RetrofitClient.getRetrofitService().getListMovies(1)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    result.value = ApiResponse.success(response.body()?.result as List<MovieRemote>)
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    Log.e(TAG, "Failure to Get List Movie ${t.message}")
                    EspressoIdlingResource.decrement()
                }

            })
        return result
    }

    fun getListTVShows(): LiveData<ApiResponse<List<TVShowRemote>>> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<ApiResponse<List<TVShowRemote>>>()
        RetrofitClient.getRetrofitService().getListTVShows(1)
            .enqueue(object : Callback<TVShowsResponse> {
                override fun onResponse(
                    call: Call<TVShowsResponse>,
                    response: Response<TVShowsResponse>
                ) {
                    result.value =
                        ApiResponse.success(response.body()?.result as List<TVShowRemote>)
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<TVShowsResponse>, t: Throwable) {
                    Log.e(TAG, "Failure to get List TV Show ${t.message}")
                    EspressoIdlingResource.decrement()
                }

            })
        return result
    }

    fun getDetailMovies(movieId: String): LiveData<ApiResponse<MoviesDetailResponse>> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<ApiResponse<MoviesDetailResponse>>()
        RetrofitClient.getRetrofitService().getDetailMovies(movieId)
            .enqueue(object : Callback<MoviesDetailResponse> {
                override fun onResponse(
                    call: Call<MoviesDetailResponse>,
                    response: Response<MoviesDetailResponse>
                ) {
                    result.value = ApiResponse.success(response.body() as MoviesDetailResponse)
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<MoviesDetailResponse>, t: Throwable) {
                    Log.e(TAG, "Failure to get Detail Movies : ${t.message}")
                    EspressoIdlingResource.decrement()
                }

            })
        return result
    }

    fun getDetailTVShows(id: String): LiveData<ApiResponse<TVShowsDetailResponse>> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<ApiResponse<TVShowsDetailResponse>>()
        RetrofitClient.getRetrofitService().getDetailTVShows(id)
            .enqueue(object : Callback<TVShowsDetailResponse> {
                override fun onResponse(
                    call: Call<TVShowsDetailResponse>,
                    response: Response<TVShowsDetailResponse>
                ) {
                    result.value = ApiResponse.success(response.body() as TVShowsDetailResponse)
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<TVShowsDetailResponse>, t: Throwable) {
                    Log.e(TAG, "Failure to get Detail TV Show : ${t.message}")
                    EspressoIdlingResource.decrement()
                }

            })
        return result
    }
}