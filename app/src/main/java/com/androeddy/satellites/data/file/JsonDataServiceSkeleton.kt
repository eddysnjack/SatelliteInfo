package com.androeddy.satellites.data.file

import android.content.Context
import io.reactivex.rxjava3.core.Single
import kotlin.reflect.KClass

interface JsonDataServiceSkeleton {
    fun <T : Any> getData(fileName: String, context: Context, typeOfResponse: KClass<T>): Single<T>
}