package com.dapoidev.moviecatalogue.model.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dapoidev.moviecatalogue.model.data.NetworkBoundResource
import com.dapoidev.moviecatalogue.model.data.entity.MovieEntity
import com.dapoidev.moviecatalogue.model.data.entity.TVShowEntity
import com.dapoidev.moviecatalogue.model.data.remote.response.ApiResponse
import com.dapoidev.moviecatalogue.model.data.remote.response.movie.MovieRemote
import com.dapoidev.moviecatalogue.model.data.remote.response.movie.MoviesDetailResponse
import com.dapoidev.moviecatalogue.model.data.remote.response.tvshow.TVShowRemote
import com.dapoidev.moviecatalogue.model.data.remote.response.tvshow.TVShowsDetailResponse
import com.dapoidev.moviecatalogue.model.data.source.FilmDataSource
import com.dapoidev.moviecatalogue.model.data.source.RemoteDataSource
import com.dapoidev.moviecatalogue.model.data.source.local.LocalDataSource
import com.dapoidev.moviecatalogue.utils.AppExecutors
import com.dapoidev.moviecatalogue.vo.Resource

class FilmCatalogueRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
) : FilmDataSource {

    companion object {
        @Volatile
        private var instance: FilmCatalogueRepository? = null

        fun getInstance(
            remote: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors,
        ): FilmCatalogueRepository =
            instance ?: synchronized(this) {
                FilmCatalogueRepository(remote, localDataSource, appExecutors).apply {
                    instance = this
                }
            }
    }

    override fun loadMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<MovieRemote>>(appExecutors) {

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun loadFromDb(): LiveData<PagedList<MovieEntity>> {
                val conf = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllDataMovie(), conf).build()
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieRemote>>> =
                remoteDataSource.getListMovies()

            override fun saveCallResult(data: List<MovieRemote>) {
                val listMovie = ArrayList<MovieEntity>()
                for (dataMovie in data) {
                    dataMovie.apply {
                        val movie = MovieEntity(
                            id, title, date, image, desc
                        )
                        listMovie.add(movie)
                    }
                }
                localDataSource.insertMovie(listMovie)
            }
        }.asLiveData()
    }

    override fun loadTVShows(): LiveData<Resource<PagedList<TVShowEntity>>> {
        return object :
            NetworkBoundResource<PagedList<TVShowEntity>, List<TVShowRemote>>(appExecutors) {

            override fun shouldFetch(data: PagedList<TVShowEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun loadFromDb(): LiveData<PagedList<TVShowEntity>> {
                val conf = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllDataTVShow(), conf).build()
            }

            override fun createCall(): LiveData<ApiResponse<List<TVShowRemote>>> =
                remoteDataSource.getListTVShows()

            override fun saveCallResult(data: List<TVShowRemote>) {
                val listTVShow = ArrayList<TVShowEntity>()
                for (dataTVShow in data) {
                    with(dataTVShow) {
                        val tvShow = TVShowEntity(id, title, date, desc, image)
                        listTVShow.add(tvShow)
                    }
                }
                localDataSource.insertTVShow(listTVShow)
            }
        }.asLiveData()
    }

    override fun loadDetailMovies(movieID: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MoviesDetailResponse>(appExecutors) {

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data == null

            override fun loadFromDb(): LiveData<MovieEntity> = localDataSource.getMovieById(movieID)
            override fun createCall(): LiveData<ApiResponse<MoviesDetailResponse>> =
                remoteDataSource.getDetailMovies(movieID.toString())

            override fun saveCallResult(data: MoviesDetailResponse) {
                with(data) {
                    val dataDetailMovie = MovieEntity(
                        id = id,
                        title = title,
                        date = date,
                        image = imageDetail,
                        desc = desc,
                        addFav = false
                    )
                    localDataSource.updateFavMovie(dataDetailMovie, false)
                }
            }
        }.asLiveData()
    }

    override fun loadDetailTVShows(tvShowID: Int): LiveData<Resource<TVShowEntity>> {
        return object : NetworkBoundResource<TVShowEntity, TVShowsDetailResponse>(appExecutors) {

            override fun shouldFetch(data: TVShowEntity?): Boolean = data == null

            override fun loadFromDb(): LiveData<TVShowEntity> =
                localDataSource.getTVShowById(tvShowID)

            override fun createCall(): LiveData<ApiResponse<TVShowsDetailResponse>> =
                remoteDataSource.getDetailTVShows(tvShowID.toString())

            override fun saveCallResult(data: TVShowsDetailResponse) {
                with(data) {
                    val dataDetailTVShow = TVShowEntity(
                        id = id,
                        title = title,
                        date = date,
                        image = imageDetail,
                        desc = desc,
                        addFav = false
                    )
                    localDataSource.updateFavTVShow(dataDetailTVShow, false)
                }
            }
        }.asLiveData()
    }

    override fun setMoviesFav(movie: MovieEntity, state: Boolean) =
        appExecutors.diskIO().execute {
            localDataSource.updateFavMovie(movie, state)
        }

    override fun setTVShowsFav(tvShow: TVShowEntity, state: Boolean) =
        appExecutors.diskIO().execute {
            localDataSource.updateFavTVShow(tvShow, state)
        }

    override fun getMoviesFav(): LiveData<PagedList<MovieEntity>> {
        val conf = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getFavMovies(), conf).build()
    }

    override fun getTVShowsFav(): LiveData<PagedList<TVShowEntity>> {
        val conf = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getFavTVShows(), conf).build()
    }
}