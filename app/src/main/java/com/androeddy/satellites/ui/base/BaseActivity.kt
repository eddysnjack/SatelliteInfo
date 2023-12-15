package com.androeddy.satellites.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.androeddy.satellites.util.NavigationHelper
import com.androeddy.satellites.util.ProgressDialogUtil
import com.androeddy.satellites.util.StringResources
import com.androeddy.satellites.util.UIActionsMessenger
import kotlin.reflect.KClass

typealias ActivityInflater<T> = (LayoutInflater) -> T

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel>(
    private val bindingInflater: ActivityInflater<VB>,
    private val vmClazz: KClass<VM>
) : AppCompatActivity(), UIActionsMessenger, StringResources {

    private var _binding: VB? = null
    protected val binding
        get() = _binding!! // I hate this damn trick!
    protected val viewModel: VM by lazy { getVM() }
    private fun getVM(): VM {
        return ViewModelProvider(this, defaultViewModelProviderFactory).get(vmClazz.java)
    }

    open fun getUIActionsMessenger(): UIActionsMessenger? {
        return viewModel.uiActionsMessenger
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)
        viewModel.uiActionsMessenger = this
        viewModel.stringResources = this
        viewModel.onViewCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewModel.onDestroy()
    }

    // MARK @eddy: we may create a builder class for these parameters
    override fun gotoFragment(
        fragment: Fragment,
        fragmentManager: FragmentManager,
        containerId: Int,
        action: NavigationHelper.Action,
        addToBackstack: Boolean
    ) {
        NavigationHelper.goToFragment(fragment, fragmentManager, containerId, action, addToBackstack)
    }

    override fun showProgress(msg: String) {
        ProgressDialogUtil.showProgressDialog(this, msg)
    }

    override fun hideProgress() {
        ProgressDialogUtil.hideProgressDialog()
    }

    override fun getStringResource(strId: Int): String {
        return getString(strId)
    }

    override fun getStringResourceWithParams(strId: Int, vararg formatArgs: Any): String {
        return getString(strId, formatArgs)
    }
}