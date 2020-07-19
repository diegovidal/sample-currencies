package com.dvidal.samplecurrencies.features.currencies.domain

import com.dvidal.samplecurrencies.core.common.EitherResult
import com.dvidal.samplecurrencies.core.common.catching
import com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency.BaseCurrencyDto
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

    private var listRates: List<RateDto?> = emptyList()

    override suspend fun refreshRates(): EitherResult<Unit> {

        return catching {

            val remoteRatesResult = ratesRemoteDataSource.fetchRates()
            var localBaseCurrencyResult = baseCurrencyLocalDataSource.fetchBaseCurrency()

            if (localBaseCurrencyResult.rightOrNull() == null) {
                baseCurrencyLocalDataSource.insertBaseCurrency(BaseCurrencyDto.firstTime())
                localBaseCurrencyResult = baseCurrencyLocalDataSource.fetchBaseCurrency()
            }

            if (remoteRatesResult.isRight) {

                val ratesRemoteResponse = remoteRatesResult.rightOrNull()
                val baseCurrencyResponse = localBaseCurrencyResult.rightOrNull()

                val insertDtos = dataProducerHandler.produceData(
                    ratesRemoteResponse,
                    baseCurrencyResponse
                )

                listRates = insertDtos
                ratesLocalDataSource.insertAllRates(insertDtos)
            }
        }
    }

    override suspend fun changeRate(ratePresentation: RatePresentation): EitherResult<Unit> {

        return catching {

            val rateDto = listRates.first { ratePresentation.symbol == it?.symbol }

            val baseCurrency = dataProducerHandler.calculateBaseCurrency(rateDto, ratePresentation.value)
            baseCurrencyLocalDataSource.insertBaseCurrency(baseCurrency)

            val localBaseCurrencyResult = baseCurrencyLocalDataSource.fetchBaseCurrency().rightOrNull()

            val insertDtos = dataProducerHandler.calculateNewValues(
                listRates,
                localBaseCurrencyResult
            )

            insertDtos?.let { listRates = it }
            return ratesLocalDataSource.insertAllRates(insertDtos)
        }
    }

    override fun fetchRates(): EitherResult<Flow<List<RateDto?>>> {
        return ratesLocalDataSource.fetchAllRatesAsFlow()
    }
}