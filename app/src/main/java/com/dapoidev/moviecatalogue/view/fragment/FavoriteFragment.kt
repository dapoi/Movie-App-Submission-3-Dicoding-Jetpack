package com.dapoidev.moviecatalogue.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dapoidev.moviecatalogue.R
import com.dapoidev.moviecatalogue.databinding.FragmentFavoriteBinding
import com.dapoidev.moviecatalogue.view.adapter.pager.FavPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteFragment : Fragment() {

    private lateinit var favBinding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        favBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return favBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar!!.title = "FAVORITE"

        with(favBinding) {
            val favPagerAdapter = FavPagerAdapter(activity as AppCompatActivity)
            viewPagerFav.adapter = favPagerAdapter

            TabLayoutMediator(tabsFav, viewPagerFav) { tab, position ->
                tab.text = resources.getString(TAB_FAV_TITLES[position])
            }.attach()
        }
    }

    companion object {
        private val TAB_FAV_TITLES = intArrayOf(R.string.movies_fav, R.string.tv_shows_fav)
    }
}