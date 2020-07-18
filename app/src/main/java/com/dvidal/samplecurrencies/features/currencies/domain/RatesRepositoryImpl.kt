package com.dvidal.samplecurrencies.features.currencies.domain

import com.dvidal.samplecurrencies.core.common.EitherResult
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RatesLocalDataSource
import com.dvidal.samplecurrencies.features.currencies.data.remote.RatesRemoteDataSource
import com.dvidal.samplecurrencies.features.currencies.presentation.RatePresentation
import kotlinx.coroutines.flow.Flow

/**
 * @author diegovidal on 18/07/20.
 */
class RatesRepositoryImpl(
    private val ratesLocalDataSource: RatesLocalDataSource,
    private val ratesRemoteDataSource: RatesRemoteDataSource,
    private val dataProducerHandler: DataProducerHandler
) : RatesRepository {

    override suspend fun refreshRates() {

        val remoteResult = ratesRemoteDataSource.fetchRates()

        if (remoteResult.isRight) {
//            val insertDtos = dataProducerHandler.produceData(remoteResult.rightOrNull())
//            ratesLocalDataSource.insertAllRates(insertDtos)
        }
    }

    override suspend fun changeValue(ratePresentation: RatePresentation) {
//        val insertDtos = dataProducerHandler.calculateNewValues(ratePresentation)
//        ratesLocalDataSource.insertAllRates(insertDtos)
    }

    override fun fetchRates(): EitherResult<Flow<List<RateDto?>>> {
        return ratesLocalDataSource.fetchAllRates()
    }
}