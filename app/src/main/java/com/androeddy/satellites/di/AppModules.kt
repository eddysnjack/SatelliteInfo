package com.androeddy.satellites.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import com.androeddy.satellites.BuildConfig
import com.androeddy.satellites.data.db.AppDatabase
import com.androeddy.satellites.data.db.models.SatelliteDao
import com.androeddy.satellites.data.file.JsonDataServiceImp
import com.androeddy.satellites.data.file.JsonDataServiceSkeleton
import com.androeddy.satellites.util.StringResources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Singleton
    @Provides
    fun provideJsonDataService(): JsonDataServiceSkeleton {
        return JsonDataServiceImp()
    }

    //--------------------------------
    //             ROOM DB
    //--------------------------------
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        val db = Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "SatellitesCache.db"
        )

        if (BuildConfig.DEBUG) {
            db.setQueryCallback(RoomDatabase.QueryCallback { sqlQuery: String, bindArgs: List<Any?> ->
                Log.d("ROOM QUERY", "query:$sqlQuery - params:${bindArgs}")
            }, Executors.newSingleThreadExecutor())
        }

        return db.build()
    }

    @Provides
    @Singleton
    fun provideSatelliteDao(appDatabase: AppDatabase): SatelliteDao {
        return appDatabase.satelliteDao()
    }
}