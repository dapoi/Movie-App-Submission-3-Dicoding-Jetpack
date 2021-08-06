package com.dapoidev.moviecatalogue.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dapoidev.moviecatalogue.databinding.FragmentMoviesBinding
import com.dapoidev.moviecatalogue.view.adapter.MoviesAdapter
import com.dapoidev.moviecatalogue.viewmodel.FilmViewModel
import com.dapoidev.moviecatalogue.viewmodel.ViewModelFactory
import com.dapoidev.moviecatalogue.vo.Status

class MoviesFragment : Fragment() {

    private lateinit var fragmentMoviesBinding: FragmentMoviesBinding
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var viewModel: FilmViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragmentMoviesBinding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return fragmentMoviesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            (activity as AppCompatActivity).supportActionBar!!.title = "MOVIES"

            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FilmViewModel::class.java]

            moviesAdapter = MoviesAdapter()

            viewModel.getListMovies().observe(viewLifecycleOwner, { movie ->
                if (movie != null) {
                    when (movie.status) {
                        Status.LOADING -> true.progressBar()
                        Status.SUCCESS -> {
                            false.progressBar()
                            with(moviesAdapter) {
                                submitList(movie.data)
                            }
                        }
                        Status.ERROR -> {
                            false.progressBar()
                            Toast.makeText(context, "Data gagal dimuat", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
            setRecyclerView()
        }
    }

    private fun setRecyclerView() {
        with(fragmentMoviesBinding.rvMovies) {
            layoutManager = LinearLayoutManager(context)
            adapter = moviesAdapter
            setHasFixedSize(true)
        }
    }

    private fun Boolean.progressBar() {
        fragmentMoviesBinding.progressBar.visibility = if (this) View.VISIBLE else View.GONE
    }
}
