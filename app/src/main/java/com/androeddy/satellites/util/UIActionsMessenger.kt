package com.androeddy.satellites.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface UIActionsMessenger {
    fun gotoFragment(
        fragment: Fragment,
        fragmentManager: FragmentManager,
        containerId: Int,
        action: NavigationHelper.Action = NavigationHelper.Action.REPLACE,
        addToBackstack: Boolean = true
    )

    fun showProgress(msg: String)
    fun hideProgress()
}