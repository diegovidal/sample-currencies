package com.dvidal.samplecurrencies.features.currencies.data.remote

import com.dvidal.samplecurrencies.core.common.EitherResult

/**
 * @author diegovidal on 17/07/20.
 */
interface RatesRemoteDataSource {

    suspend fun fetchRates(symbol: String): EitherResult<RatesRemoteResponse>
}