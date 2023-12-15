package com.androeddy.satellites.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface StringResources {
    fun getStringResource(strId: Int): String
    fun getStringResourceWithParams(strId: Int, vararg formatArgs: Any): String
}