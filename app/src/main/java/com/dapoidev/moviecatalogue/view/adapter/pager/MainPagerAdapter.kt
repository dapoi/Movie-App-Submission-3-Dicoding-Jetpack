package com.dapoidev.moviecatalogue.view.adapter.pager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dapoidev.moviecatalogue.R
import com.dapoidev.moviecatalogue.view.fragment.MoviesFragment
import com.dapoidev.moviecatalogue.view.fragment.TVShowsFragment

class MainPagerAdapter(private val context: Context, fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private val TAB_TITLES = intArrayOf(R.string.movies, R.string.tv_shows)
    }

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> MoviesFragment()
            1 -> TVShowsFragment()
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence =
        context.resources.getString(TAB_TITLES[position])
}