package com.androeddy.satellites.data.file

import android.content.Context
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.core.Single

interface JsonDataServiceSkeleton {
    fun <T : Any> getData(fileName: String, context: Context,  typeToken: TypeToken<T>): Single<T>
}