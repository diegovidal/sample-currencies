package com.dvidal.samplecurrencies.features.currencies.domain

import com.dvidal.samplecurrencies.core.common.Either
import com.dvidal.samplecurrencies.core.datasource.remote.MyConstants
import com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency.BaseCurrencyDto
import com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency.BaseCurrencyLocalDataSource
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RatesLocalDataSource
import com.dvidal.samplecurrencies.features.currencies.data.remote.RatesRemoteDataSource
import com.dvidal.samplecurrencies.features.currencies.data.remote.RatesRemoteResponse
import com.dvidal.samplecurrencies.features.currencies.presentation.RatePresentation
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any

/**
 * @author diegovidal on 17/07/20.
 */
class RatesRepositoryTest {

    private val ratesLocalDataSource = mockk<RatesLocalDataSource>()
    private val baseCurrencyLocalDataSource = mockk<BaseCurrencyLocalDataSource>()
    private val ratesRemoteDataSource = mockk<RatesRemoteDataSource>()
    private val dataProducerHandler = mockk<DataProducerHandler>()

    lateinit var repository: RatesRepository

    @Before
    fun setup() {

        repository = RatesRepositoryImpl(ratesLocalDataSource, baseCurrencyLocalDataSource, ratesRemoteDataSource, dataProducerHandler)
    }

    @Test
    fun `when refresh rates for the first time should call for insert base currency`() = runBlocking {

        coEvery { ratesRemoteDataSource.fetchRates() } returns Either.right(RatesRemoteResponse.empty())
        coEvery { baseCurrencyLocalDataSource.fetchBaseCurrency() } returns Either.right(null)

        repository.refreshRates()
        coVerify(exactly = 1) { baseCurrencyLocalDataSource.insertBaseCurrency(any())  }
    }

    @Test
    fun `when refresh rates and remote is right should call for insert all rates`() = runBlocking {

        coEvery { ratesRemoteDataSource.fetchRates() } returns Either.right(RatesRemoteResponse.empty())
        coEvery { baseCurrencyLocalDataSource.fetchBaseCurrency() } returns Either.right(BaseCurrencyDto())
        coEvery { dataProducerHandler.produceData(any(), any()) } returns emptyList()

        repository.refreshRates()
        coVerify(exactly = 1) { ratesLocalDataSource.insertAllRates(any())  }
    }

    @Test
    fun `when refresh rates and remote is left should not call for insert all rates`() = runBlocking {

        coEvery { ratesRemoteDataSource.fetchRates() } returns Either.left(Throwable())
        coEvery { baseCurrencyLocalDataSource.fetchBaseCurrency() } returns Either.right(BaseCurrencyDto())
        coEvery { dataProducerHandler.produceData(any(), any()) } returns emptyList()

        repository.refreshRates()
        coVerify(exactly = 0) { ratesLocalDataSource.insertAllRates(any())  }
    }

    @Test
    fun `when change value should call for calculate base currency, insert base currency and insert all rates`() = runBlocking {

        val list = listOf(RateDto().apply {
            symbol = MyConstants.BRL
            value = 3.0
        })
        coEvery { baseCurrencyLocalDataSource.fetchBaseCurrency() } returns Either.right(BaseCurrencyDto())
        coEvery { baseCurrencyLocalDataSource.insertBaseCurrency(any()) } returns Either.right(Unit)
        coEvery { ratesLocalDataSource.fetchAllRates() } returns Either.right(list)
        coEvery { ratesLocalDataSource.insertAllRates(any()) } returns Either.right(Unit)
        coEvery { dataProducerHandler.calculateBaseCurrency(any(), any()) } returns BaseCurrencyDto()
        coEvery { dataProducerHandler.calculateNewValues(any(), any()) } returns list

        repository.changeValue(RatePresentation(symbol = MyConstants.BRL, value = 3.0))
        coVerify(exactly = 1) { dataProducerHandler.calculateBaseCurrency(any(), any())  }
        coVerify(exactly = 1) { baseCurrencyLocalDataSource.insertBaseCurrency(any())  }
        coVerify(exactly = 1) { ratesLocalDataSource.insertAllRates(any()) }
    }

    @Test
    fun `when fetch rates should call for rates data source fetch all rates as flow`() = runBlocking {

        coEvery { ratesLocalDataSource.fetchAllRatesAsFlow() } returns  Either.right(flow { emit(listOf()) })

        repository.fetchRates()
        coVerify(exactly = 1) { ratesLocalDataSource.fetchAllRatesAsFlow()  }
    }
}