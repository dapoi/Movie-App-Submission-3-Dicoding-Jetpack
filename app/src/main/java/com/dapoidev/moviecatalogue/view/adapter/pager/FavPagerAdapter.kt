package com.dapoidev.moviecatalogue.view.adapter.pager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dapoidev.moviecatalogue.view.fragment.MoviesFragment
import com.dapoidev.moviecatalogue.view.fragment.favorite.MovieFavoriteFragment
import com.dapoidev.moviecatalogue.view.fragment.favorite.TvShowFavoriteFragment

class FavPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = MovieFavoriteFragment()
            1 -> fragment = TvShowFavoriteFragment()
        }
        return fragment as Fragment
    }
}