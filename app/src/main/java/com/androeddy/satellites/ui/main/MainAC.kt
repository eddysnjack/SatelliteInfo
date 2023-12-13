package com.androeddy.satellites.ui.main

import android.os.Bundle
import com.androeddy.satellites.R
import com.androeddy.satellites.databinding.ActivityMainBinding
import com.androeddy.satellites.ui.base.BaseActivity
import com.androeddy.satellites.util.NavigationHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainAC : BaseActivity<ActivityMainBinding, MainACVM>(ActivityMainBinding::inflate, MainACVM::class) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavigationHelper.goToFragment(
            MainFR.newInstance(), supportFragmentManager, R.id.frContainer
        )
    }
}