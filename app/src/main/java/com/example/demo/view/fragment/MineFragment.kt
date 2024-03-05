package com.example.demo.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.demo.R
import com.example.demo.databinding.FragmentMineBinding
import com.example.demo.util.EasyDataStore
import com.example.demo.view.activity.LoginActivity
import kotlin.properties.Delegates


class MineFragment : Fragment() {

    private lateinit var binding: FragmentMineBinding
    private var theme by Delegates.notNull<Int>()
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
        theme = EasyDataStore.getData("theme", R.style.AppTheme)
        binding.themeSwitch.isChecked = theme == R.style.AppTheme_Night
    }

    private fun bindEvent() {

        binding.themeSwitch.setOnClickListener {
            chaneTheme()
        }

        binding.exitCard.setOnClickListener {
            logOut()
        }
    }

    private fun logOut() {
        EasyDataStore.putData("autoLogin", false)
        startActivity(Intent(requireActivity(), LoginActivity::class.java))
        requireActivity().finish()
    }

    private fun chaneTheme() {
        if (theme == R.style.AppTheme) {
            EasyDataStore.putData("theme", R.style.AppTheme_Night)
        } else {
            EasyDataStore.putData("theme", R.style.AppTheme)
        }
        requireActivity().recreate()
    }
}