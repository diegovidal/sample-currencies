package com.dvidal.samplecurrencies.features.currencies.domain

import com.dvidal.samplecurrencies.features.currencies.data.local.RatesLocalDataSource
import com.dvidal.samplecurrencies.features.currencies.data.remote.RatesRemoteDataSource

/**
 * @author diegovidal on 18/07/20.
 */
class RatesRepositoryImpl(
    private val ratesLocalDataSource: RatesLocalDataSource,
    private val ratesRemoteDataSource: RatesRemoteDataSource
): RatesRepository {


}