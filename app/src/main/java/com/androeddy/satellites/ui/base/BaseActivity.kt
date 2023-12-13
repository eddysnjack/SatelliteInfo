package com.androeddy.satellites.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

typealias ActivityInflater<T> = (LayoutInflater) -> T

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel>(private val bindingInflater: ActivityInflater<VB>, private val vmClazz: KClass<VM>) : AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding
        get() = _binding!! // I hate this damn trick!
    protected val viewModel: VM by lazy { getVM() }
    private fun getVM(): VM {
        return ViewModelProvider(this, defaultViewModelProviderFactory).get(vmClazz.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)
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
}