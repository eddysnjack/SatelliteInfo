package com.androeddy.satellites.domain.usecases

import android.content.Context
import com.androeddy.satellites.data.file.EndPoints
import com.androeddy.satellites.data.file.JsonDataServiceSkeleton
import com.androeddy.satellites.domain.base.BaseRequest
import com.androeddy.satellites.domain.base.BaseUseCase
import com.androeddy.satellites.domain.models.AllSatellitePositionsList
import com.androeddy.satellites.domain.models.SatellitePositions
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetSatellitePositionsUseCase @Inject constructor(
    private val jsonService: JsonDataServiceSkeleton,
    @ApplicationContext private val context: Context
) : BaseUseCase<GetSatellitePositionsUseCase.Params, SatellitePositions>() {
    data class Params(val satelliteId: Int) : BaseRequest()

    override fun buildUseCaseSingle(req: Params?): Single<SatellitePositions> {
        return jsonService.getData(EndPoints.POSITIONS, context, object : TypeToken<AllSatellitePositionsList>() {}).map { allPositions ->
            (allPositions.list?.find { it.id == req?.satelliteId }) ?: SatellitePositions(id = -1, positions = null)
        }
    }
}