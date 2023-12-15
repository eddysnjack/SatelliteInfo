package com.androeddy.satellites.ui.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.androeddy.satellites.util.NavigationHelper
import com.androeddy.satellites.util.StringResources
import com.androeddy.satellites.util.UIActionsMessenger
import kotlin.reflect.KClass


typealias FragmentInflater<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(
    private val bindingInflater: FragmentInflater<VB>,
    private val vmClazz: KClass<VM>
) : Fragment(), StringResources {

    private var _binding: VB? = null
    protected val binding
        get() = _binding!!
    protected val viewModel: VM by lazy { getVM() }
    private fun getVM(): VM {
        return ViewModelProvider(this, defaultViewModelProviderFactory).get(vmClazz.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.arguments = arguments
        viewModel.uiActionsMessenger = (activity as BaseActivity<*, *>).getUIActionsMessenger()
        viewModel.stringResources = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setReceivers()
        viewModel.onViewCreated(savedInstanceState)
    }

    open fun setListeners() {

    }

    open fun setReceivers() {

    }

    override fun getStringResource(strId: Int): String {
        return getString(strId)
    }

    override fun getStringResourceWithParams(strId: Int, vararg formatArgs: Any): String {
        return getString(strId, formatArgs)
    }
}