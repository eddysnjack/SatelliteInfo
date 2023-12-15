package com.androeddy.satellites.domain.models

import com.androeddy.satellites.data.db.models.Satellite
import com.androeddy.satellites.data.db.models.SatelliteDao
import com.google.gson.annotations.SerializedName

class SatelliteItemComposite {
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

    fun updateFromBasic(basicEntity: SatelliteBasicItemEntity): SatelliteItemComposite {
        this.id = basicEntity.id
        this.name = basicEntity.name
        this.active = basicEntity.active
        return this
    }

    fun updateFromDetail(detailEntity: SatelliteDetailItemEntity): SatelliteItemComposite {
        this.costPerLaunch = detailEntity.costPerLaunch
        this.firstFlight = detailEntity.firstFlight
        this.height = detailEntity.height
        this.mass = detailEntity.mass
        return this
    }

    fun updateFromLocalDao(satellite: Satellite): SatelliteItemComposite {
        this.id = satellite.id
        this.name = satellite.name
        this.active = satellite.active
        this.costPerLaunch = satellite.costPerLaunch
        this.firstFlight = satellite.firstFlight
        this.height = satellite.height
        this.mass = satellite.mass
        return this
    }

    fun toSatelliteDao(): Satellite {
        return Satellite(
            this.id,
            this.name,
            this.active,
            this.costPerLaunch,
            this.firstFlight,
            this.height,
            this.mass
        )
    }
}