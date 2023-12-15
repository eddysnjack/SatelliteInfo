package com.androeddy.satellites.ui.detail.models

import com.androeddy.satellites.domain.models.PositionSatelliteEntity
import com.androeddy.satellites.domain.models.SatellitePositions

class PositionUiModel() {
    var currentPositionIndex: Int = 0
    var positions: ArrayList<PositionSatelliteEntity>? = null

    constructor(currentPositionIndex: Int, positions: ArrayList<PositionSatelliteEntity>?) : this() {
        this.currentPositionIndex = currentPositionIndex
        this.positions = positions
    }

    fun createFromSatellitePositions(satellitePositions: SatellitePositions): PositionUiModel {
        this.positions = satellitePositions.positions
        return this
    }
}