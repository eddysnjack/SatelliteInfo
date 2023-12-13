package com.androeddy.satellites.domain.models

import com.google.gson.annotations.SerializedName

data class SatelliteBasicItemEntity(
    @SerializedName("active")
    val active: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)
