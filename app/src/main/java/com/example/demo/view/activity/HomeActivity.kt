package com.example.demo.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.demo.R
import com.example.demo.adapter.ViewPagerAdapter
import com.example.demo.databinding.ActivityHomeBinding
import com.example.demo.util.EasyDataStore
import com.example.demo.util.showNotice
import com.example.demo.view.base.BaseActivity
import com.example.demo.view.fragment.HomeFragment
import com.example.demo.view.fragment.MineFragment

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val fragments = mutableListOf<Fragment>()

    //    private var theme by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)
//        this.showNotice("测试")
        initData()
        initView()
        bindEvent()
    }

    private fun initData() {
        fragments.add(HomeFragment())
        fragments.add(MineFragment())

    }

    private fun initView() {
        val theme = EasyDataStore.getData("theme", R.style.AppTheme)
        binding.themeSwitch.isChecked = theme == R.style.AppTheme_Night

        binding.viewPager.adapter = ViewPagerAdapter(this, fragments)
//        binding.viewPager.isUserInputEnabled = false //禁止滑动切换

    }


    private fun bindEvent() {
        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomNavigation.menu.getItem(position).isChecked = true
            }
        })

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> binding.viewPager.currentItem = 0
                R.id.mine -> binding.viewPager.currentItem = 1
            }
            true
        }

        binding.cover.setOnClickListener {
            binding.drawerLayout.open()
        }

        binding.exitCard.setOnClickListener {
            logOut()
        }

        binding.themeCard.setOnClickListener {
            binding.themeSwitch.isChecked = !binding.themeSwitch.isChecked
        }
        binding.themeSwitch.setOnCheckedChangeListener { _, _ ->
            chaneTheme()
        }
    }

    private fun logOut() {
        EasyDataStore.putData("autoLogin", false)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun chaneTheme() {
        if (EasyDataStore.getData("theme", R.style.AppTheme) == R.style.AppTheme) {
            EasyDataStore.putData("theme", R.style.AppTheme_Night)
        } else {
            EasyDataStore.putData("theme", R.style.AppTheme)
        }
        recreate()
    }
}