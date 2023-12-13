package com.androeddy.satellites.di

import com.androeddy.satellites.data.file.JsonDataServiceImp
import com.androeddy.satellites.data.file.JsonDataServiceSkeleton
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Singleton
    @Provides
    fun provideJsonDataService(): JsonDataServiceSkeleton {
        return JsonDataServiceImp()
    }
}