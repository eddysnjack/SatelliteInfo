package com.androeddy.satellites.domain.usecases

import android.content.Context
import com.androeddy.satellites.data.db.models.SatelliteDao
import com.androeddy.satellites.data.file.EndPoints
import com.androeddy.satellites.data.file.JsonDataServiceSkeleton
import com.androeddy.satellites.domain.base.BaseRequest
import com.androeddy.satellites.domain.base.BaseUseCase
import com.androeddy.satellites.domain.models.SatelliteBasicItemEntity
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.internal.operators.single.SingleJust
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetSatelliteListUseCase @Inject constructor(
    private val jsonService: JsonDataServiceSkeleton,
    @ApplicationContext private val context: Context
) : BaseUseCase<GetSatelliteListUseCase.Params, ArrayList<SatelliteBasicItemEntity>>() {
    data class Params(val searchQuery: String)

    override fun buildUseCaseSingle(req: GetSatelliteListUseCase.Params?): Single<ArrayList<SatelliteBasicItemEntity>> {
        if (req != null) {
            return jsonService.getData(EndPoints.SATELLITES_BASIC, context, object : TypeToken<ArrayList<SatelliteBasicItemEntity>>() {})
                .map {
                    val result = arrayListOf<SatelliteBasicItemEntity>()
                    for (item in it) {
                        if (item.stringifyForSearch().contains(req.searchQuery, ignoreCase = true)) {
                            result.add(item)
                        }
                    }

                    return@map result
                }
        } else {
            return jsonService.getData(EndPoints.SATELLITES_BASIC, context, object : TypeToken<ArrayList<SatelliteBasicItemEntity>>() {})
        }
    }
}