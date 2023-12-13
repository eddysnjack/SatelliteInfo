package com.androeddy.satellites.domain.usecases

import android.content.Context
import com.androeddy.satellites.data.file.JsonDataServiceSkeleton
import com.androeddy.satellites.domain.base.BaseRequest
import com.androeddy.satellites.domain.base.BaseUseCase
import com.androeddy.satellites.domain.models.SatelliteBasicItemEntity
import com.androeddy.satellites.domain.models.SatellitesBasicEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetSatelliteListUseCase @Inject constructor(
    private val jsonService: JsonDataServiceSkeleton,
    @ApplicationContext private val context: Context
) : BaseUseCase<BaseRequest, SatellitesBasicEntity>() {
    override fun buildUseCaseSingle(req: BaseRequest): Single<SatellitesBasicEntity> {
        return jsonService.getData((req.endPoint ?: ""), context, SatellitesBasicEntity::class)
    }

    override fun onFinished() {
    }

    override fun onStart() {
    }
}