package com.androeddy.satellites.ui.main

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.androeddy.satellites.R
import com.androeddy.satellites.databinding.FrMainBinding
import com.androeddy.satellites.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFR : BaseFragment<FrMainBinding, MainFRVM>(FrMainBinding::inflate, MainFRVM::class) {

    companion object {
        fun newInstance() = MainFR()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setListeners() {
        val decoration = DividerItemDecoration(
            binding.rvSatellites.context,
            (binding.rvSatellites.layoutManager as LinearLayoutManager).orientation
        ).apply {
            ContextCompat.getDrawable(requireContext(), R.drawable.recyclerview_seperator_divider)?.let {
                setDrawable(it)
            }
        }

        binding.rvSatellites.addItemDecoration(decoration)
        binding.svSatellites.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("SEARCH", "onQueryTextSubmit: $query")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("SEARCH", "onQueryTextChange: $newText")
                return false
            }

        })
    }

    override fun setReceivers() {
        viewModel.onSatelliteListFetched.observe(viewLifecycleOwner) {
            val adapter: SatelliteListAdapter = SatelliteListAdapter(it)
            binding.rvSatellites.adapter = adapter
            Log.d(MainFRVM.TAG, "observe: MainFR called")
        }

        viewModel.onError.observe(this@MainFR) {
            Log.d(MainFRVM.TAG, "setReceivers: onError Called")
        }
    }
}