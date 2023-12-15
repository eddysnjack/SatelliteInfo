package com.androeddy.satellites.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Satellites")
data class Satellite(
    @PrimaryKey var id: Int? = null,
    var name: String? = null,
    var active: Boolean? = null,
    var costPerLaunch: Int? = null,
    var firstFlight: String? = null,
    var height: Int? = null,
    var mass: Int? = null
)
