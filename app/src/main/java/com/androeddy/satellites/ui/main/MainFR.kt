package com.androeddy.satellites.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.androeddy.satellites.R
import com.androeddy.satellites.databinding.FrMainBinding
import com.androeddy.satellites.ui.base.BaseFragment
import com.androeddy.satellites.ui.detail.SatelliteDetailFR
import com.androeddy.satellites.ui.main.models.SatelliteUIModel
import com.androeddy.satellites.util.TimerCountDown
import com.androeddy.satellites.util.Extensions.gone
import com.androeddy.satellites.util.Extensions.visible
import com.androeddy.satellites.util.NavigationHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFR : BaseFragment<FrMainBinding, MainFRVM>(FrMainBinding::inflate, MainFRVM::class) {
    companion object {
        fun newInstance() = MainFR()
    }

    private val timerCountDown = object : TimerCountDown<String>(500) {
        override fun onTrigger(triggerMessage: String?) {
            Log.d(MainFR::class.java.simpleName, "onTrigger: triggerMessage:$triggerMessage")
            if ((triggerMessage?.trim()).isNullOrEmpty().not()) {
                viewModel.getList(triggerMessage)
            } else {
                viewModel.getList()
            }
        }
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
                viewModel.getList(query)
                timerCountDown.stop()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("SEARCH", "onQueryTextChange: $newText")
                timerCountDown.restart(newText)
                return false
            }

        })
    }

    override fun setReceivers() {
        viewModel.onSatelliteListFetched.observe(viewLifecycleOwner) {
            val adapter: SatelliteListAdapter = SatelliteListAdapter(it)
            adapter.itemClickListener = object : SatelliteListAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int, item: SatelliteUIModel) {
                    viewModel.uiActionsMessenger?.gotoFragment(
                        SatelliteDetailFR.newInstance(item.id ?: -1),
                        requireActivity().supportFragmentManager,
                        R.id.frContainer
                    )
                }
            }

            binding.rvSatellites.adapter = adapter
            Log.d(MainFR::class.java.simpleName, "observe: onSuccess called")
        }

        viewModel.onError.observe(this@MainFR) {
            binding.groupSuccessItems.gone()
            binding.tvErrorDescription.visible()
            Log.d(MainFR::class.java.simpleName, "setReceivers: onError Called")
        }
    }

    override fun onStop() {
        super.onStop()
        timerCountDown.stop()
    }
}