package com.dapoidev.moviecatalogue.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dapoidev.moviecatalogue.databinding.FragmentTvBinding
import com.dapoidev.moviecatalogue.view.adapter.TVShowsAdapter
import com.dapoidev.moviecatalogue.viewmodel.FilmViewModel
import com.dapoidev.moviecatalogue.viewmodel.ViewModelFactory
import com.dapoidev.moviecatalogue.vo.Status

class TVShowsFragment : Fragment() {

    private lateinit var fragmentTVBinding: FragmentTvBinding
    private lateinit var tvShowsAdapter: TVShowsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragmentTVBinding = FragmentTvBinding.inflate(layoutInflater, container, false)
        return fragmentTVBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[FilmViewModel::class.java]

            tvShowsAdapter = TVShowsAdapter()

            viewModel.getListTVShows().observe(viewLifecycleOwner, { tvShow ->
                if (tvShow != null) {
                    when (tvShow.status) {
                        Status.LOADING -> true.progressBar()
                        Status.SUCCESS -> {
                            false.progressBar()
                            with(tvShowsAdapter) {
                                submitList(tvShow.data)
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
        fragmentTVBinding.rvTvshows.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = tvShowsAdapter
        }
    }

    private fun Boolean.progressBar() {
        fragmentTVBinding.progressBar.visibility = if (this) View.VISIBLE else View.GONE
    }
}
