package com.androeddy.satellites.ui.main

import android.os.Bundle
import android.util.Log
import com.androeddy.satellites.domain.usecases.ClearCacheRecordsUseCase
import com.androeddy.satellites.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainACVM @Inject constructor(
    private val clearCacheRecordsUseCase: ClearCacheRecordsUseCase
) : BaseViewModel() {
    override fun onViewCreated(savedInstanceState: Bundle?) {
        super.onViewCreated(savedInstanceState)
        clearCacheRecordsUseCase.execute(
            params = null,
            onSuccess = {
                Log.d(MainACVM::class.java.simpleName, "clearCache: onSuccess")
            },
            onError = {
                Log.d(MainACVM::class.java.simpleName, "clearCache: onError")
            })
    }
}