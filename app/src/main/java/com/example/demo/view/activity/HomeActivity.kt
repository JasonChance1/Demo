package com.example.demo.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.demo.R
import com.example.demo.adapter.ViewPagerAdapter
import com.example.demo.databinding.ActivityHomeBinding
import com.example.demo.view.fragment.HomeFragment
import com.example.demo.view.fragment.MineFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val fragments = mutableListOf<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initView()
        bindEvent()
    }

    private fun initData() {
        fragments.add(HomeFragment())
        fragments.add(MineFragment())

    }

    private fun initView() {

        binding.viewPager.adapter = ViewPagerAdapter(this, fragments)
//        binding.viewPager.isUserInputEnabled = false //禁止滑动切换
    }

    private fun bindEvent() {
        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.bottomNavigation.menu.getItem(position).isChecked = true
            }
        })

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> binding.viewPager.currentItem = 0
                R.id.mine ->  binding.viewPager.currentItem = 1
            }
            true
        }
    }
}