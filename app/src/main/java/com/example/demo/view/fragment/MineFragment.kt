package com.example.demo.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.demo.R
import com.example.demo.databinding.FragmentMineBinding
import com.example.demo.util.EasyDataStore
import com.example.demo.view.activity.LoginActivity
import com.example.demo.view.widget.CustomSnackBar
import com.google.android.material.imageview.ShapeableImageView
import kotlin.properties.Delegates


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