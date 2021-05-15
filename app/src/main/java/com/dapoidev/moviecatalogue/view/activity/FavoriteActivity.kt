package com.dapoidev.moviecatalogue.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dapoidev.moviecatalogue.R
import com.dapoidev.moviecatalogue.databinding.ActivityFavoriteBinding
import com.dapoidev.moviecatalogue.view.adapter.pager.FavPagerAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favBinding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favBinding.root)

        supportActionBar?.apply {
            title = "Favorite"
            setHomeAsUpIndicator(R.drawable.ic_back)
            setDisplayHomeAsUpEnabled(true)
        }

        setViewPagerFav()
    }

    private fun setViewPagerFav() {
        with(favBinding) {
            val favPagerAdapter = FavPagerAdapter(this@FavoriteActivity, supportFragmentManager)
            viewPagerFav.adapter = favPagerAdapter

            tabsFav.setupWithViewPager(viewPagerFav)
            supportActionBar?.elevation = 0f
        }
        Toast.makeText(applicationContext, "Swipe untuk menghapus item", Toast.LENGTH_LONG).show()
    }
}