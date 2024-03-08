package com.example.demo.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.demo.databinding.FragmentMineBinding


class MineFragment : Fragment() {

    private lateinit var binding: FragmentMineBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMineBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initView()
        bindEvent()
    }

    private fun initView() {
//        theme = EasyDataStore.getData("theme", R.style.AppTheme)
//        binding.themeSwitch.isChecked = theme == R.style.AppTheme_Night

    }

    private fun bindEvent() {

    }
}