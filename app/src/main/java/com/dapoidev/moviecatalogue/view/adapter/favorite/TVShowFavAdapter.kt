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
import com.dapoidev.moviecatalogue.databinding.ItemTvShowsBinding
import com.dapoidev.moviecatalogue.data.source.local.model.TVShowEntity
import com.dapoidev.moviecatalogue.view.activity.DetailActivity

class TVShowFavAdapter :
    PagedListAdapter<TVShowEntity, TVShowFavAdapter.TVShowViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TVShowEntity>() {
            override fun areItemsTheSame(oldItem: TVShowEntity, newItem: TVShowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TVShowEntity, newItem: TVShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun getSwipedItem(swipedPosition: Int): TVShowEntity? = getItem(swipedPosition)

    inner class TVShowViewHolder(private val binding: ItemTvShowsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tvShow: TVShowEntity) {
            binding.apply {
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500" + tvShow.image)
                    .transform(RoundedCorners(20))
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgShows)

                with(tvShow) {
                    tvTitleShows.text = title
                    tvDateShows.text = date
                    tvDescShows.text = desc
                }

                itemView.setOnClickListener {
                    Intent(itemView.context, DetailActivity::class.java).also {
                        it.putExtra(DetailActivity.EXTRAS_DATA, tvShow.id)
                        it.putExtra(DetailActivity.EXTRAS_CHOOSE, "TV_SHOW")
                        itemView.context.startActivity(it)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val view = ItemTvShowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TVShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) holder.bind(tvShow)
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: String)
    }
}