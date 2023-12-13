package com.androeddy.satellites.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.androeddy.satellites.domain.base.BaseRequest
import com.androeddy.satellites.domain.models.SatellitesBasicEntity
import com.androeddy.satellites.domain.usecases.GetSatelliteListUseCase
import com.androeddy.satellites.ui.base.BaseViewModel
import com.androeddy.satellites.ui.main.models.SatelliteUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainFRVM @Inject constructor(
    private val getSatellites: GetSatelliteListUseCase
) : BaseViewModel() {

    val onSatelliteListFetched = MutableLiveData<ArrayList<SatelliteUIModel>>()
    val onError = MutableLiveData<Unit>()

    companion object {
        const val TAG = "TAGEDDY"
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        super.onViewCreated(savedInstanceState)
        getList()
    }

    fun getList() {
        getSatellites.execute(
            params = BaseRequest().apply { endPoint = "satellite-list.json" },
            onSuccess = { list ->
                val transformedList: ArrayList<SatelliteUIModel> = ArrayList(
                    list.map { item -> SatelliteUIModel().mapFromBasic(item) }
                )
                onSatelliteListFetched.postValue(transformedList)
                Log.d(TAG, "onViewCreated: onSatelliteListFetched called")
            },
            onError = {
                onError.postValue(Unit)
            })
    }
}