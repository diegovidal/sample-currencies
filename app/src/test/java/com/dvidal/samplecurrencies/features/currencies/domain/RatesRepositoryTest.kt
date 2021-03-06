package com.dvidal.samplecurrencies.features.currencies.domain

import com.dvidal.samplecurrencies.core.common.Either
import com.dvidal.samplecurrencies.core.common.MyConstants
import com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency.BaseCurrencyDto
import com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency.BaseCurrencyLocalDataSource
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RatesLocalDataSource
import com.dvidal.samplecurrencies.features.currencies.data.remote.RatesRemoteDataSource
import com.dvidal.samplecurrencies.features.currencies.data.remote.RatesRemoteResponse
import com.dvidal.samplecurrencies.features.currencies.presentation.RatePresentation
import io.mockk.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

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
    fun `when refresh rates its not first time should call for calculate new euro value`() = runBlocking {

        val list = listOf(
            RateDto(symbol = MyConstants.BRL, value = 3.0),
            RateDto(symbol = MyConstants.EUR, value = 3.0)
        )

        coEvery { ratesRemoteDataSource.fetchRates() } returns Either.right(RatesRemoteResponse.empty())
        coEvery { baseCurrencyLocalDataSource.fetchBaseCurrency() } returns Either.right(BaseCurrencyDto())

        every { dataProducerHandler.fetchListRates() } returns list
        every { dataProducerHandler.updateListRates(any()) } just Runs

        repository.refreshRates()
        verify(exactly = 1) { dataProducerHandler.calculateNewEuroValue(any(), any(), any())  }
    }

    @Test
    fun `when refresh rates and remote is right should call for insert all rates`() = runBlocking {

        coEvery { ratesRemoteDataSource.fetchRates() } returns Either.right(RatesRemoteResponse.empty())
        coEvery { baseCurrencyLocalDataSource.fetchBaseCurrency() } returns Either.right(BaseCurrencyDto())

        every { dataProducerHandler.produceData(any(), any()) } returns emptyList()
        every { dataProducerHandler.fetchListRates() } returns emptyList()
        every { dataProducerHandler.updateListRates(any()) } just Runs

        repository.refreshRates()
        coVerify(exactly = 1) { ratesLocalDataSource.insertAllRates(any())  }
    }

    @Test
    fun `when refresh rates and remote is left should not call for insert all rates and update list rates`() = runBlocking {

        coEvery { ratesRemoteDataSource.fetchRates() } returns Either.left(Throwable())
        coEvery { baseCurrencyLocalDataSource.fetchBaseCurrency() } returns Either.right(BaseCurrencyDto())
        coEvery { dataProducerHandler.produceData(any(), any()) } returns emptyList()

        repository.refreshRates()
        coVerify(exactly = 0) { ratesLocalDataSource.insertAllRates(any())  }
        verify(exactly = 0) { dataProducerHandler.updateListRates(any())  }
    }

    @Test
    fun `when change value should call for calculate base currency, insert base currency and insert all rates`() = runBlocking {

        val list = listOf(
            RateDto(symbol = MyConstants.BRL, value = 3.0),
            RateDto(symbol = MyConstants.EUR, value = 3.0)
        )

        coEvery { baseCurrencyLocalDataSource.fetchBaseCurrency() } returns Either.right(BaseCurrencyDto())
        coEvery { baseCurrencyLocalDataSource.insertBaseCurrency(any()) } returns Either.right(Unit)
        coEvery { ratesLocalDataSource.insertAllRates(any()) } returns Either.right(Unit)

        every { dataProducerHandler.calculateBaseCurrency(any(), any()) } returns BaseCurrencyDto()
        every { dataProducerHandler.calculateNewValues(any(), any()) } returns list
        every { dataProducerHandler.fetchListRates() } returns list
        every { dataProducerHandler.updateListRates(any()) } just Runs

        repository.changeRate(RatePresentation(symbol = MyConstants.BRL, value = 3.0))
        verify(exactly = 1) { dataProducerHandler.calculateBaseCurrency(any(), any())  }
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