package com.dapoidev.moviecatalogue.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dapoidev.moviecatalogue.databinding.ActivityMainBinding
import com.dapoidev.moviecatalogue.view.adapter.pager.MainPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configViewPager()

        binding.fabFav.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
    }

    private fun configViewPager() {
        binding.apply {
            val mainPagerAdapter = MainPagerAdapter(this@MainActivity, supportFragmentManager)
            viewPager.adapter = mainPagerAdapter

            tabs.setupWithViewPager(viewPager)
            supportActionBar?.elevation = 0f
        }
    }
}