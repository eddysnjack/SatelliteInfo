package com.androeddy.satellites.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.androeddy.satellites.R
import com.androeddy.satellites.ui.main.MainFR

object NavigationHelper {
    enum class Action() {
        ADD, SHOW, HIDE, REPLACE
    }

    fun goToFragment(
        fragment: Fragment,
        fragmentManager: FragmentManager,
        containerId: Int,
        action: Action = Action.REPLACE,
        addToBackstack: Boolean = true
    ) {
        val frMt = fragmentManager.beginTransaction()

        if (addToBackstack) {
            frMt.addToBackStack(fragment::class.java.name)
        }

        when (action) {
            Action.ADD -> frMt.add(containerId, fragment, fragment::class.java.name)
            Action.SHOW -> frMt.show(fragment)
            Action.HIDE -> frMt.hide(fragment)
            Action.REPLACE -> frMt.replace(containerId, fragment, fragment::class.java.name)
        }

        frMt.commitAllowingStateLoss()
    }
}