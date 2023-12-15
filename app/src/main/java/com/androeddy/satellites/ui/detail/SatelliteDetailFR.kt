package com.androeddy.satellites.ui.detail

import android.os.Bundle
import android.view.View
import com.androeddy.satellites.R
import com.androeddy.satellites.databinding.FrSatelliteDetailBinding
import com.androeddy.satellites.ui.base.BaseFragment
import com.androeddy.satellites.util.DateHelper
import com.androeddy.satellites.util.Extensions.gone
import com.androeddy.satellites.util.Extensions.visible
import com.androeddy.satellites.util.StringHelper
import com.androeddy.satellites.util.TimerRepeater
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SatelliteDetailFR : BaseFragment<FrSatelliteDetailBinding, SatelliteDetailFRVM>(FrSatelliteDetailBinding::inflate, SatelliteDetailFRVM::class) {
    private var timerRepeater: TimerRepeater? = null

    companion object {
        const val SATELLITE_ID = "SATELLITE_ID"
        fun newInstance(satelliteId: Int): SatelliteDetailFR {
            val fr = SatelliteDetailFR()
            fr.arguments = Bundle().apply {
                putInt(SATELLITE_ID, satelliteId)
            }

            return fr
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timerRepeater = object : TimerRepeater(viewModel.positionRefreshRateMs) {
            override fun onTick() {
                viewModel.refreshPositionData()
            }
        }
    }

    override fun setReceivers() {
        viewModel.onSatelliteDataReceived.observe(viewLifecycleOwner) {
            val isSucceed = it.first
            val data = it.second
            if (isSucceed) {
                data?.let {
                    binding.tvName.text = data.name
                    binding.tvDate.text = data.firstFlight?.let { it1 -> DateHelper.convertStringDateToLocaleFormat(it1, DateHelper.JSON_DATE_FORMAT) }
                    binding.tvHeightMassValue.text = "${data.height}/${data.mass}"
                    binding.tvCostValue.text = "${StringHelper.formatNumberWithSeparator(data.costPerLaunch?.toLong() ?: 0)}"
                }
            } else {
                binding.llSubParent.gone()
                binding.llOnErrorParent.visible()
            }
        }

        viewModel.onPositionsDataReceived.observe(viewLifecycleOwner) {
            val isSucceed = it.first
            val data = it.second
            if (isSucceed && data != null) {
                binding.tvLastPositionValue.text = String
                    .format(
                        "(%f,%f)",
                        data.positions?.get(data.currentPositionIndex)?.posX,
                        data.positions?.get(data.currentPositionIndex)?.posY
                    )
                if (timerRepeater?.isRunning == false) {
                    timerRepeater?.startTimer()
                }
            } else {
                binding.tvLastPositionValue.text = getString(R.string.satellite_detail_last_position_error)
                timerRepeater?.stopTimer()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.onPositionsDataReceived.isInitialized && timerRepeater?.isRunning == false) {
            timerRepeater?.startTimer()
        }
    }

    override fun onStop() {
        super.onStop()
        timerRepeater?.stopTimer()
    }
}