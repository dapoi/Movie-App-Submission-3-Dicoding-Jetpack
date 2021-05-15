package com.dapoidev.moviecatalogue.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dapoidev.moviecatalogue.R
import com.dapoidev.moviecatalogue.databinding.ActivityDetailBinding
import com.dapoidev.moviecatalogue.model.data.entity.MovieEntity
import com.dapoidev.moviecatalogue.model.data.entity.TVShowEntity
import com.dapoidev.moviecatalogue.viewmodel.DetailFilmViewModel
import com.dapoidev.moviecatalogue.viewmodel.ViewModelFactory
import com.dapoidev.moviecatalogue.vo.Status

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var detailFilmViewModel: DetailFilmViewModel

    companion object {
        const val EXTRAS_DATA = "extras_data"
        const val EXTRAS_CHOOSE = "choose"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_back)
            setDisplayHomeAsUpEnabled(true)
        }

        val factory = ViewModelFactory.getInstance(this)
        detailFilmViewModel = ViewModelProvider(this, factory)[DetailFilmViewModel::class.java]

        val receivedData = intent.getIntExtra(EXTRAS_DATA, 0)
        val filmChoose = intent.getStringExtra(EXTRAS_CHOOSE)
        if (receivedData != 0 && filmChoose != null) {
            when (filmChoose) {
                "MOVIE" -> {
                    getDataMovie(receivedData)
                }
                "TV_SHOW" -> {
                    getDataTVShow(receivedData)
                }
            }
        }
        setFav()
    }

    private fun getDataMovie(movieID: Int) {
        detailFilmViewModel.setDataMovie(movieID).observe(this, {
            when (it.status) {
                Status.LOADING -> true.progressBar()
                Status.SUCCESS -> {
                    if (it.data != null) {
                        false.progressBar()
                        generateDataDetailMovie(it.data)
                    }
                }
                Status.ERROR -> {
                    false.progressBar()
                    Toast.makeText(applicationContext, "Data gagal dimuat", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    private fun getDataTVShow(tvShowID: Int) {
        detailFilmViewModel.setDataTVShow(tvShowID).observe(this, {
            when (it.status) {
                Status.LOADING -> true.progressBar()
                Status.SUCCESS -> {
                    if (it.data != null) {
                        false.progressBar()
                        generateDataDetailTVShow(it.data)
                    }
                }
                Status.ERROR -> {
                    false.progressBar()
                    Toast.makeText(applicationContext, "Data gagal dimuat", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    private fun generateDataDetailMovie(detail: MovieEntity) {

        if (supportActionBar != null) {
            title = detail.title + " Detail's"
        }

        detailBinding.apply {
            with(detail) {
                tvDetailTitle.text = title
                tvDetailDate.text = date
                tvDetailDesc.text = desc
                buttonFav.isChecked = addFav

                Glide.with(this@DetailActivity)
                    .load("https://image.tmdb.org/t/p/w500$image")
                    .transform(RoundedCorners(20))
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgDetail)
            }
        }
    }

    private fun generateDataDetailTVShow(detail: TVShowEntity) {
        if (supportActionBar != null) {
            title = detail.title + " Detail's"
        }

        with(detailBinding) {
            with(detail) {
                tvDetailTitle.text = title
                tvDetailDate.text = date
                tvDetailDesc.text = desc
                buttonFav.isChecked = addFav

                Glide.with(this@DetailActivity)
                    .load("https://image.tmdb.org/t/p/w500$image")
                    .transform(RoundedCorners(20))
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgDetail)
            }
        }
    }

    private fun setFav() {
        val filmChoose = intent.getStringExtra(EXTRAS_CHOOSE)
        if (filmChoose != null) {
            detailBinding.buttonFav.setOnClickListener {
                when (filmChoose) {
                    "MOVIE" -> {
                        detailFilmViewModel.setMovieFavorite()
                    }
                    "TV_SHOW" -> {
                        detailFilmViewModel.setTVShowFavorite()
                    }
                }
            }
        }
    }

    private fun Boolean.progressBar() {
        detailBinding.progressBar.visibility = if (this) View.VISIBLE else View.GONE
    }
}