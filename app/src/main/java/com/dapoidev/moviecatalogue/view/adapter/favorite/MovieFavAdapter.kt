package com.dapoidev.moviecatalogue.view.adapter.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dapoidev.moviecatalogue.R
import com.dapoidev.moviecatalogue.databinding.ItemMoviesBinding
import com.dapoidev.moviecatalogue.data.source.local.model.MovieEntity
import com.dapoidev.moviecatalogue.view.activity.DetailActivity

class MovieFavAdapter : PagedListAdapter<MovieEntity, MovieFavAdapter.MovieFavViewHolder>(
    DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun getSwipedItem(swipedPosition: Int): MovieEntity? = getItem(swipedPosition)

    inner class MovieFavViewHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(movie: MovieEntity) {
                binding.apply {
                    Glide.with(itemView.context)
                        .load("https://image.tmdb.org/t/p/w500" + movie.image)
                        .transform(RoundedCorners(20))
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_loading)
                                .error(R.drawable.ic_error)
                        )
                        .into(imgMovie)

                    tvTitleMovie.text = movie.title
                    tvDateMovie.text = movie.date
                    tvDescMovie.text = movie.desc

                    itemView.setOnClickListener {
                        Intent(itemView.context, DetailActivity::class.java).also {
                            it.putExtra(DetailActivity.EXTRAS_DATA, movie.id)
                            it.putExtra(DetailActivity.EXTRAS_CHOOSE, "MOVIE")
                            itemView.context.startActivity(it)
                        }
                    }
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFavViewHolder {
        val view = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieFavViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieFavViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) holder.bind(movie)
    }
}