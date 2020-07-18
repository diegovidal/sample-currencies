package com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency

import com.dvidal.samplecurrencies.core.common.EitherResult
import com.dvidal.samplecurrencies.core.common.catching
import com.dvidal.samplecurrencies.core.datasource.local.AppDatabase

/**
 * @author diegovidal on 18/07/20.
 */
class BaseCurrencyLocalDataSourceImpl(
    private val appDatabase: AppDatabase
): BaseCurrencyLocalDataSource {

    override suspend fun fetchBaseCurrency(): EitherResult<BaseCurrencyDto?> {
        return catching { appDatabase.baseCurrencyDao().fetchBaseCurrency()  }
    }

    override suspend fun insertBaseCurrency(baseCurrency: BaseCurrencyDto): EitherResult<Unit> {
        return catching { appDatabase.baseCurrencyDao().insertBaseCurrency(baseCurrency) }
    }
}