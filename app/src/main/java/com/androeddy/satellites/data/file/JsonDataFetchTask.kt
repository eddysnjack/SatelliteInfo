package com.androeddy.satellites.data.file

import android.content.Context
import android.content.res.AssetManager
import com.leylapps.anandroidhistory.common.helpers.ExceptionHelper
import java.io.IOException
import java.io.InputStream
import java.lang.StringBuilder


object JsonDataFetchTask {
    fun getJsonData(jsonFileName: String, context: Context): Pair<Boolean, String> {
        return try {
            val assetManager: AssetManager = context.assets
            val inputStream: InputStream = assetManager.open(jsonFileName)
            val strBuilder = StringBuilder()
            strBuilder.append(inputStream.readBytes().toString(Charsets.UTF_8))
            Pair(true, strBuilder.toString())
        } catch (e: IOException) {
            Pair(false, ExceptionHelper.shortenedStackTrace(e, 2))
        }
    }
}