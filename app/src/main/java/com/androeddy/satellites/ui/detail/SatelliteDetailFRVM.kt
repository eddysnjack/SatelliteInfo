package com.androeddy.satellites.ui.detail

import android.os.Bundle
import android.os.HandlerThread
import androidx.lifecycle.MutableLiveData
import com.androeddy.satellites.domain.models.SatelliteItemComposite
import com.androeddy.satellites.domain.usecases.GetSatelliteDetailsUseCase
import com.androeddy.satellites.domain.usecases.GetSatellitePositionsUseCase
import com.androeddy.satellites.ui.base.BaseViewModel
import com.androeddy.satellites.ui.detail.models.PositionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SatelliteDetailFRVM @Inject constructor(
    private val getSatelliteDetailsUseCase: GetSatelliteDetailsUseCase,
    private val getSatellitePositionsUseCase: GetSatellitePositionsUseCase
) : BaseViewModel() {
    val onSatelliteDataReceived = MutableLiveData<Pair<Boolean, SatelliteItemComposite?>>()
    val onPositionsDataReceived = MutableLiveData<Pair<Boolean, PositionUiModel?>>()
    val positionRefreshRateMs: Long = 3000
    private var satelliteId: Int? = null

    override fun onViewCreated(savedInstanceState: Bundle?) {
        super.onViewCreated(savedInstanceState)
        satelliteId = arguments?.getInt(SatelliteDetailFR.SATELLITE_ID, -1)
        val handlerThread: HandlerThread = HandlerThread("TimerThread")
        handlerThread.start()
        getSatelliteDetailsUseCase.execute(
            params = satelliteId?.let { GetSatelliteDetailsUseCase.Params(it) },
            onSuccess = {
                onSatelliteDataReceived.postValue(Pair(true, it))
                getPositions()
            },
            onError = {
                onSatelliteDataReceived.postValue(Pair(false, null))
            }
        )
    }

    private fun getPositions() {
        getSatellitePositionsUseCase.execute(
            params = GetSatellitePositionsUseCase.Params(satelliteId = (satelliteId ?: -1)),
            onSuccess = {
                onPositionsDataReceived.postValue(Pair(true, PositionUiModel(0, it.positions)))
            },
            onError = {
                onPositionsDataReceived.postValue(Pair(false, null))
            }
        )
    }

    fun refreshPositionData() {
        val positionsListPair = onPositionsDataReceived.value
        val positionUiModel = positionsListPair?.second
        if (positionsListPair?.first == true) {
            val currentIndex = positionUiModel?.currentPositionIndex ?: -1
            if (currentIndex != -1 && positionUiModel != null && positionUiModel.positions.isNullOrEmpty().not()) {
                val nextPosition = (currentIndex + 1) % positionUiModel.positions!!.size
                positionUiModel.currentPositionIndex = nextPosition
            }
        }

        onPositionsDataReceived.postValue(Pair(positionsListPair?.first ?: false, positionUiModel))
    }
}