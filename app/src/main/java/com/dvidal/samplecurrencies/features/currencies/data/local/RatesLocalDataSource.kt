package com.dvidal.samplecurrencies.features.currencies.data.local

import com.dvidal.samplecurrencies.core.common.EitherResult
import kotlinx.coroutines.flow.Flow

/**
 * @author diegovidal on 17/07/20.
 */
interface RatesLocalDataSource {

    suspend fun insertAllRates(listRatesDto: List<RateDto>): EitherResult<Unit>
    fun fetchAllRates(): EitherResult<Flow<List<RateDto?>>>
    suspend fun clearAllRates(): EitherResult<Unit>
}