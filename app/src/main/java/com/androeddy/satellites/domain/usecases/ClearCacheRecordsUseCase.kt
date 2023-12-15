package com.androeddy.satellites.domain.usecases

import com.androeddy.satellites.data.db.models.SatelliteDao
import com.androeddy.satellites.domain.base.BaseRequest
import com.androeddy.satellites.domain.base.BaseUseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ClearCacheRecordsUseCase @Inject constructor(
    private val satelliteDao: SatelliteDao
) : BaseUseCase<BaseRequest, Int>() {
    override fun buildUseCaseSingle(req: BaseRequest?): Single<Int> {
        return satelliteDao.deleteAll()
    }
}