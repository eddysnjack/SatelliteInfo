package com.androeddy.satellites.domain.usecases

import android.content.Context
import android.util.Log
import com.androeddy.satellites.BuildConfig
import com.androeddy.satellites.data.db.models.SatelliteDao
import com.androeddy.satellites.data.file.JsonDataServiceSkeleton
import com.androeddy.satellites.domain.base.BaseUseCase
import com.androeddy.satellites.domain.models.SatelliteItemComposite
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SaveSatelliteDetailsLocalUseCase @Inject constructor(
    private val satelliteDao: SatelliteDao
) : BaseUseCase<SatelliteItemComposite, Long>() {
    override fun buildUseCaseSingle(req: SatelliteItemComposite?): Single<Long> {
        return if (req != null) {
            return satelliteDao.insertSatellite(req.toSatelliteDao())
        } else {
            Single.fromObservable(Observable.empty())
        }
    }

    override fun onFinished() {

    }

    override fun onStart() {
    }
}