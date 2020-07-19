package com.dvidal.samplecurrencies.core.datasource.remote

import com.dvidal.samplecurrencies.features.currencies.data.remote.RatesRemoteResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author diegovidal on 2020-07-18.
 */
interface RemoteApi {

    @GET("latest")
    suspend fun fetchRates(
        @Query("base") baseCurrency: String
    ): RatesRemoteResponse
}