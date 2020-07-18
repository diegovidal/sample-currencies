package com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency

import com.dvidal.samplecurrencies.core.common.EitherResult

/**
 * @author diegovidal on 18/07/20.
 */
interface BaseCurrencyLocalDataSource {

    suspend fun fetchBaseCurrency(): EitherResult<BaseCurrencyDto?>
    suspend fun insertBaseCurrency(baseCurrency: BaseCurrencyDto): EitherResult<Unit>
}