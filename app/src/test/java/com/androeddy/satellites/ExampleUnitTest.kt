package com.androeddy.satellites

import com.androeddy.satellites.util.StringHelper
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun numberFormatTest() {
        val number = 1250856
        println(StringHelper.formatNumberWithSeparator(number.toLong()))
    }
}