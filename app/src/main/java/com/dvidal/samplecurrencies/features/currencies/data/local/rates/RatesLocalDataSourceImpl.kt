package com.dvidal.samplecurrencies.features.currencies.data.local.rates

import com.dvidal.samplecurrencies.core.common.EitherResult
import com.dvidal.samplecurrencies.core.common.catching
import com.dvidal.samplecurrencies.core.datasource.local.AppDatabase
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RatesLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author diegovidal on 17/07/20.
 */
class RatesLocalDataSourceImpl @Inject constructor(
    private val appDatabase: AppDatabase
): RatesLocalDataSource {

    override suspend fun insertAllRates(listRatesDto: List<RateDto>): EitherResult<Unit> {
        return catching { appDatabase.ratesDao().insertRates(listRatesDto) }
    }

    override fun fetchAllRates(): EitherResult<Flow<List<RateDto?>>> {
        return catching { appDatabase.ratesDao().fetchRates().map { emptyList<RateDto>() } }
    }

    override suspend fun clearAllRates(): EitherResult<Unit> {
        return catching { appDatabase.ratesDao().clearRates() }
    }
}