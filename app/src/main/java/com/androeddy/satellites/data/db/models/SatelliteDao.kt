package com.androeddy.satellites.data.db.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface SatelliteDao {
    @Query("SELECT * FROM Satellites")
    fun getAllSatellites(): Single<List<Satellite>>

    @Query("SELECT * FROM Satellites WHERE id=:id")
    fun getSatelliteByd(id: Int): Single<Satellite>

    @Insert
    fun insertSatellite(satellite: Satellite): Single<Long>

    @Insert
    fun insertMultipleSatellite(satellites: Array<Satellite>): Single<List<Long>>

    //deleteAll
    @Query("DELETE FROM Satellites")
    fun deleteAll(): Single<Int>
}