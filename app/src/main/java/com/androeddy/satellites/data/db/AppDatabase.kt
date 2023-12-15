package com.androeddy.satellites.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androeddy.satellites.data.db.models.Satellite
import com.androeddy.satellites.data.db.models.SatelliteDao

@Database(entities = [Satellite::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun satelliteDao(): SatelliteDao
}