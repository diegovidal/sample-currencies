package com.dvidal.samplecurrencies.features.currencies.data.local.rates

import com.dvidal.samplecurrencies.core.common.EitherResult
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import kotlinx.coroutines.flow.Flow

/**
 * @author diegovidal on 17/07/20.
 */
interface RatesLocalDataSource {

    suspend fun insertAllRates(listRatesDto: List<RateDto?>?): EitherResult<Unit>
    fun fetchAllRatesAsFlow(): EitherResult<Flow<List<RateDto?>>>
    suspend fun fetchAllRates(): EitherResult<List<RateDto?>>
    suspend fun clearAllRates(): EitherResult<Unit>
}