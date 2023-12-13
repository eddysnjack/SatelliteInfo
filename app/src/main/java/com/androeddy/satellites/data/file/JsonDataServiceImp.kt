package com.androeddy.satellites.data.file

import android.content.Context
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import kotlin.reflect.KClass

class JsonDataServiceImp : JsonDataServiceSkeleton {
    override fun <T : Any> getData(fileName: String, context: Context, typeOfResponse: KClass<T>): Single<T> {
        return Single.create<T> { emitter ->
            val result = JsonDataFetchTask.getJsonData(fileName, context)
            if (result.first) {
                val strResult = result.second
                val modelT = Gson().fromJson(strResult, typeOfResponse.javaObjectType)
                emitter.onSuccess(modelT)
            } else {
                emitter.onError(Throwable(result.second))
            }
        }
    }
}