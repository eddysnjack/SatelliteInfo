package com.androeddy.satellites.domain.usecases

import android.content.Context
import android.util.Log
import com.androeddy.satellites.BuildConfig
import com.androeddy.satellites.data.db.models.SatelliteDao
import com.androeddy.satellites.data.file.EndPoints
import com.androeddy.satellites.data.file.JsonDataServiceSkeleton
import com.androeddy.satellites.domain.base.BaseRequest
import com.androeddy.satellites.domain.base.BaseUseCase
import com.androeddy.satellites.domain.models.SatelliteBasicItemEntity
import com.androeddy.satellites.domain.models.SatelliteDetailItemEntity
import com.androeddy.satellites.domain.models.SatelliteItemComposite
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GetSatelliteDetailsUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val jsonService: JsonDataServiceSkeleton,
    private val satelliteDao: SatelliteDao,
    private val saveSatelliteDetailsLocalUseCase: SaveSatelliteDetailsLocalUseCase
) : BaseUseCase<GetSatelliteDetailsUseCase.Params, SatelliteItemComposite>() {
    data class Params(val satelliteId: Int) : BaseRequest()

    override fun buildUseCaseSingle(req: Params?): Single<SatelliteItemComposite> {
        val nonNullReq = req ?: Params(satelliteId = -1)
        return getDatabaseDataSource(nonNullReq).onErrorResumeNext {
            getJsonFileDataSource(nonNullReq).doOnSuccess {
                saveToDatabase(it)
            }
        }
    }

    private fun saveToDatabase(satellite: SatelliteItemComposite) {
        val TAG = "ROOM SAVE CACHE"
        saveSatelliteDetailsLocalUseCase.execute(
            params = satellite,
            {
                if (BuildConfig.DEBUG) Log.d(TAG, "success: id:$it")
            },
            {
                if (BuildConfig.DEBUG) Log.d(TAG, "error: id:$it")
            }
        )
    }


    private fun getDatabaseDataSource(params: Params): Single<SatelliteItemComposite> {
        val localData = satelliteDao.getSatelliteByd(params.satelliteId).map {
            SatelliteItemComposite().updateFromLocalDao(it)
        }

        return localData
    }

    private fun getJsonFileDataSource(params: Params): Single<SatelliteItemComposite> {
        val basicJsonData = jsonService.getData(EndPoints.SATELLITES_BASIC, context, object : TypeToken<ArrayList<SatelliteBasicItemEntity>>() {})
        val detailJsonData = jsonService.getData(EndPoints.SATELLITES_DETAIL, context, object : TypeToken<ArrayList<SatelliteDetailItemEntity>>() {})

        return Single.zip(
            basicJsonData,
            detailJsonData,
            // This syntax is quite complex, so I am intentionally avoiding converting it to a lambda for the sake of clarity.
            object : BiFunction<ArrayList<SatelliteBasicItemEntity>, ArrayList<SatelliteDetailItemEntity>, SatelliteItemComposite> {
                override fun apply(basic: ArrayList<SatelliteBasicItemEntity>, detail: ArrayList<SatelliteDetailItemEntity>): SatelliteItemComposite {
                    val item = SatelliteItemComposite()
                    basic.forEachIndexed { index, satelliteBasicItemEntity ->
                        if (params.satelliteId == satelliteBasicItemEntity.id) {
                            item.updateFromBasic(satelliteBasicItemEntity)
                            item.updateFromDetail(detail[index])
                        }
                    }

                    return item
                }
            }
        )
    }
}