package com.androeddy.satellites.domain.models

import com.google.gson.annotations.SerializedName

data class SatellitePositions(
    @SerializedName("id")
    val id: Int?,
    var positions: ArrayList<PositionSatelliteEntity>? = null
)

data class AllSatellitePositionsList(
    @SerializedName("list")
    var list: ArrayList<SatellitePositions>? = null
)