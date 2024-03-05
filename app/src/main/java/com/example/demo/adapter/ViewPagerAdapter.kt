package com.example.demo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @description:
 * @author yanglei
 * @date :2024/3/5
 * @version 1.0.0
 */
class ViewPagerAdapter(activity: FragmentActivity,private val fragments:List<Fragment>) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}
