package com.dvidal.samplecurrencies.features.currencies.data.local.rates

import com.dvidal.samplecurrencies.core.datasource.local.AppDatabase
import com.dvidal.samplecurrencies.core.common.MyConstants
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Before

/**
 * @author diegovidal on 17/07/20.
 */
class RatesLocalDataSourceTest {

    private val appDatabase = mockk<AppDatabase>()
    lateinit var dataSource: RatesLocalDataSource

    @Before
    fun setup() {

        dataSource = RatesLocalDataSourceImpl(appDatabase)
    }

    @Test
    fun `when insert rates should call ratesDao insert rates`() = runBlocking {

        val dummyRates = listOf(
            RateDto(symbol = MyConstants.BRL),
            RateDto(symbol = MyConstants.EUR)
        )
        coEvery { appDatabase.ratesDao().insertRates(dummyRates) } returns Unit
        dataSource.insertAllRates(dummyRates)

        coVerify(exactly = 1) {appDatabase.ratesDao().insertRates(dummyRates)}
    }

    @Test
    fun `when fetch all rates should call ratesDao fetch rates as flow`() = runBlocking {

        val dummyRates = listOf(
            RateDto(symbol = MyConstants.BRL),
            RateDto(symbol = MyConstants.EUR)
        )
        val dummyFlow = flow { emit(dummyRates) }
        coEvery { appDatabase.ratesDao().fetchRatesAsFlow() } returns dummyFlow

        dataSource.fetchAllRatesAsFlow()
        coVerify(exactly = 1) { appDatabase.ratesDao().fetchRatesAsFlow()  }
    }

    @Test
    fun `when fetch all rates should call ratesDao fetch rates`() = runBlocking {

        val dummyRates = listOf(
            RateDto(symbol = MyConstants.BRL),
            RateDto(symbol = MyConstants.EUR)
        )
        val dummyFlow = flow { emit(dummyRates) }
        coEvery { appDatabase.ratesDao().fetchRatesAsFlow() } returns dummyFlow

        dataSource.fetchAllRates()
        coVerify(exactly = 1) { appDatabase.ratesDao().fetchRates()  }
    }

    @Test
    fun `when clear all rates should call ratesDao clear rates`() = runBlocking {

        coEvery { appDatabase.ratesDao().clearRates() } returns Unit

        dataSource.clearAllRates()
        coVerify(exactly = 1) { appDatabase.ratesDao().clearRates()  }
    }
}