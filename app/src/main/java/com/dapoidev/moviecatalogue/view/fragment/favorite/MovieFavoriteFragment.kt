package com.dapoidev.moviecatalogue.view.fragment.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dapoidev.moviecatalogue.R
import com.dapoidev.moviecatalogue.databinding.FragmentMovieFavoriteBinding
import com.dapoidev.moviecatalogue.view.adapter.favorite.MovieFavAdapter
import com.dapoidev.moviecatalogue.viewmodel.FavoriteViewModel
import com.dapoidev.moviecatalogue.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MovieFavoriteFragment : Fragment(){

    private lateinit var movieFavoriteBinding: FragmentMovieFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var movieFavAdapter: MovieFavAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        movieFavoriteBinding =
            FragmentMovieFavoriteBinding.inflate(layoutInflater, container, false)
        return movieFavoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(movieFavoriteBinding.rvMoviesFav)

        if (activity != null) {

            true.progressBar()

            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

            movieFavAdapter = MovieFavAdapter()

            viewModel.getFavListMovie().observe(viewLifecycleOwner, {
                false.progressBar()
                movieFavAdapter.submitList(it)
            })

            movieFavoriteBinding.rvMoviesFav.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = movieFavAdapter
            }
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
        ): Int = makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)


        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipePosition = viewHolder.bindingAdapterPosition
                val movieEntity = movieFavAdapter.getSwipedItem(swipePosition)
                movieEntity?.let {
                    viewModel.setFavListMovie(it)
                }

                val snackbar = Snackbar.make(view as View, R.string.undo, Snackbar.LENGTH_LONG)
                snackbar.setAction("OK") {
                    movieEntity?.let {
                        viewModel.setFavListMovie(it)
                    }
                }
                snackbar.show()
            }
        }

    })

    private fun Boolean.progressBar() {
        movieFavoriteBinding.progressBar.visibility = if (this) View.VISIBLE else View.GONE
    }
}