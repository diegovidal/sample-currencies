package com.dvidal.samplecurrencies.features.currencies.domain

import com.dvidal.samplecurrencies.core.common.EitherResult
import com.dvidal.samplecurrencies.core.common.MyConstants
import com.dvidal.samplecurrencies.core.common.catching
import com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency.BaseCurrencyDto
import com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency.BaseCurrencyLocalDataSource
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RatesLocalDataSource
import com.dvidal.samplecurrencies.features.currencies.data.remote.RatesRemoteDataSource
import com.dvidal.samplecurrencies.features.currencies.presentation.RatePresentation
import kotlinx.coroutines.flow.Flow
import kotlin.time.times

/**
 * @author diegovidal on 18/07/20.
 */
class RatesRepositoryImpl(
    private val ratesLocalDataSource: RatesLocalDataSource,
    private val baseCurrencyLocalDataSource: BaseCurrencyLocalDataSource,
    private val ratesRemoteDataSource: RatesRemoteDataSource,
    private val dataProducerHandler: DataProducerHandler
) : RatesRepository {

    override suspend fun refreshRates(): EitherResult<Unit> {

        return catching {

            val remoteRatesResult = ratesRemoteDataSource.fetchRates()
            var localBaseCurrencyResult = baseCurrencyLocalDataSource.fetchBaseCurrency()

            if (localBaseCurrencyResult.rightOrNull() == null) {
                baseCurrencyLocalDataSource.insertBaseCurrency(BaseCurrencyDto.firstTime())
            } else if (dataProducerHandler.fetchListRates().isNotEmpty()) {

                val newBaseCurrency = dataProducerHandler.calculateNewEuroValue(dataProducerHandler.fetchListRates(), localBaseCurrencyResult.rightOrNull(),
                    remoteRatesResult.rightOrNull())
                baseCurrencyLocalDataSource.insertBaseCurrency(newBaseCurrency)
            }

            localBaseCurrencyResult = baseCurrencyLocalDataSource.fetchBaseCurrency()

            if (remoteRatesResult.isRight) {

                val ratesRemoteResponse = remoteRatesResult.rightOrNull()
                val baseCurrencyResponse = localBaseCurrencyResult.rightOrNull()

                val insertDtos = dataProducerHandler.produceData(
                    ratesRemoteResponse,
                    baseCurrencyResponse
                )

                dataProducerHandler.updateListRates(insertDtos)
                ratesLocalDataSource.insertAllRates(insertDtos)
            }
        }
    }

    override suspend fun changeRate(ratePresentation: RatePresentation): EitherResult<Unit> {

        return catching {

            val rateDto = dataProducerHandler.fetchListRates().first { ratePresentation.symbol == it?.symbol }
            val baseCurrency = dataProducerHandler.calculateBaseCurrency(rateDto, ratePresentation.value)

            val insertDtos = dataProducerHandler.calculateNewValues(dataProducerHandler.fetchListRates(), baseCurrency)

            val euro = insertDtos?.first { it?.symbol == MyConstants.EUR }?.value
            baseCurrencyLocalDataSource.insertBaseCurrency(BaseCurrencyDto(currencySymbol = ratePresentation.symbol, euroValue = euro ?: 1.0))

            insertDtos?.let { dataProducerHandler.updateListRates(it) }
            return ratesLocalDataSource.insertAllRates(insertDtos)
        }
    }

    override fun fetchRates(): EitherResult<Flow<List<RateDto?>>> {
        return ratesLocalDataSource.fetchAllRatesAsFlow()
    }
}