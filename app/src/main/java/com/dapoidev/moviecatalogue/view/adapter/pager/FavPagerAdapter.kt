package com.dapoidev.moviecatalogue.view.adapter.pager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dapoidev.moviecatalogue.R
import com.dapoidev.moviecatalogue.view.fragment.favorite.MovieFavoriteFragment
import com.dapoidev.moviecatalogue.view.fragment.favorite.TvShowFavoriteFragment

class FavPagerAdapter(private val context: Context, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private val TAB_FAV_TITLES = intArrayOf(R.string.movies_fav, R.string.tv_shows_fav)
    }

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> MovieFavoriteFragment()
            1 -> TvShowFavoriteFragment()
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence =
        context.resources.getString(TAB_FAV_TITLES[position])
}