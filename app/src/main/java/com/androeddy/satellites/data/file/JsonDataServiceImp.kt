package com.androeddy.satellites.data.file

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.core.Single

class JsonDataServiceImp : JsonDataServiceSkeleton {
    override fun <T : Any> getData(fileName: String, context: Context, typeToken:TypeToken<T>): Single<T> {
        return Single.create<T> { emitter ->
            val result = JsonDataFetchTask.getJsonData(fileName, context)
            if (result.first) {
                val strResult = result.second
                val modelT= Gson().fromJson<T>(strResult, typeToken.type)
                emitter.onSuccess(modelT)
            } else {
                emitter.onError(Throwable(result.second))
            }
        }
    }
}