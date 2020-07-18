package com.dvidal.samplecurrencies.features.currencies.domain

import com.dvidal.samplecurrencies.core.common.EitherResult
import com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency.BaseCurrencyLocalDataSource
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
    private val baseCurrencyLocalDataSource: BaseCurrencyLocalDataSource,
    private val ratesRemoteDataSource: RatesRemoteDataSource,
    private val dataProducerHandler: DataProducerHandler
) : RatesRepository {

    override suspend fun refreshRates() {

        val remoteRatesResult = ratesRemoteDataSource.fetchRates()
        val localBaseCurrencyResult = baseCurrencyLocalDataSource.fetchBaseCurrency()

        if (remoteRatesResult.isRight) {

            val ratesRemoteResponse = remoteRatesResult.rightOrNull()

            if (localBaseCurrencyResult.rightOrNull() == null)
                baseCurrencyLocalDataSource.insertBaseCurrency(ratesRemoteResponse?.firstTime())

            val insertDtos = dataProducerHandler.produceData(ratesRemoteResponse, localBaseCurrencyResult.rightOrNull())
            ratesLocalDataSource.insertAllRates(insertDtos)
        }
    }

    override suspend fun changeValue(ratePresentation: RatePresentation) {

        val localRatesResult = ratesLocalDataSource.fetchAllRates().rightOrNull()
        val rateDto = localRatesResult?.first { ratePresentation.symbol == it?.symbol }

        val baseCurrency = dataProducerHandler.calculateBaseCurrency(rateDto, ratePresentation.value)
        baseCurrencyLocalDataSource.insertBaseCurrency(baseCurrency)

        val localBaseCurrencyResult = baseCurrencyLocalDataSource.fetchBaseCurrency().rightOrNull()

        val insertDtos = dataProducerHandler.calculateNewValues(localRatesResult, localBaseCurrencyResult?.euroValue)
        ratesLocalDataSource.insertAllRates(insertDtos)
    }

    override fun fetchRates(): EitherResult<Flow<List<RateDto?>>> {
        return ratesLocalDataSource.fetchAllRatesAsFlow().apply {  }
    }
}