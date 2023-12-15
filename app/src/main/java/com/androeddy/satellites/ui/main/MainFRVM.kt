package com.androeddy.satellites.ui.main

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.androeddy.satellites.R
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

    override fun onViewCreated(savedInstanceState: Bundle?) {
        super.onViewCreated(savedInstanceState)
        getList()
    }

    fun getList(query: String? = null) {
        getSatellites.execute(
            params = query?.let { GetSatelliteListUseCase.Params(it) },
            onSuccess = { list ->
                val transformedList: ArrayList<SatelliteUIModel> = ArrayList(
                    list.map { item -> SatelliteUIModel().mapFromBasic(item) }
                )
                onSatelliteListFetched.postValue(transformedList)
            },
            onError = {
                onError.postValue(Unit)
            },
            uiActionsMessenger,
            stringResources?.getStringResource(R.string.general_loading)
        )
    }
}