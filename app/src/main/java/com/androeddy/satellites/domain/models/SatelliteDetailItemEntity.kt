package com.androeddy.satellites.domain.models


import com.google.gson.annotations.SerializedName

data class SatelliteDetailItemEntity(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("cost_per_launch")
    val costPerLaunch: Int?,
    @SerializedName("first_flight")
    val firstFlight: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("mass")
    val mass: Int?
)