package com.androeddy.satellites.ui.main.models

import com.androeddy.satellites.domain.models.SatelliteBasicItemEntity
import com.androeddy.satellites.domain.models.SatelliteDetailItemEntity
import com.google.gson.annotations.SerializedName

class SatelliteUIModel {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("active")
    var active: Boolean? = null

    @SerializedName("cost_per_launch")
    var costPerLaunch: Int? = null

    @SerializedName("first_flight")
    var firstFlight: String? = null

    @SerializedName("height")
    var height: Int? = null

    @SerializedName("mass")
    var mass: Int? = null

    fun mapFromBasic(basicEntity: SatelliteBasicItemEntity): SatelliteUIModel {
        this.id = basicEntity.id
        this.name = basicEntity.name
        this.active = basicEntity.active
        return this
    }

    fun mapFromDetail(detailEntity: SatelliteDetailItemEntity): SatelliteUIModel {
        this.costPerLaunch = detailEntity.costPerLaunch
        this.firstFlight = detailEntity.firstFlight
        this.height = detailEntity.height
        this.mass = detailEntity.mass
        return this
    }

}


enum class SatelliteState(val strValue: String) {
    Active("Active"),
    Passive("Passive");

    companion object {
        fun getFromBoolean(bool: Boolean): SatelliteState {
            return if (bool) Active else Passive
        }
    }

}