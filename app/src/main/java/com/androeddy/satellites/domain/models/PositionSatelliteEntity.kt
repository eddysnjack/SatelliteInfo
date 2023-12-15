package com.androeddy.satellites.domain.models


import com.google.gson.annotations.SerializedName

data class PositionSatelliteEntity(
    @SerializedName("posX")
    val posX: Double?,
    @SerializedName("posY")
    val posY: Double?
)