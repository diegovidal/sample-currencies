package com.dvidal.samplecurrencies.features.currencies.data.local

import com.dvidal.samplecurrencies.core.datasource.local.AppDatabase
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RatesLocalDataSource
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RatesLocalDataSourceImpl
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
class RatesRemoteLocalDataSourceTest {

    private val appDatabase = mockk<AppDatabase>()
    lateinit var dataSource: RatesLocalDataSource

    @Before
    fun setup() {
        dataSource =
            RatesLocalDataSourceImpl(
                appDatabase
            )
    }

    @Test
    fun `when insert rates should call ratesDao insert rates`() = runBlocking {

        val dummyRates = listOf(
            RateDto(
                symbol = "brl"
            ),
            RateDto(
                symbol = "eur"
            )
        )
        coEvery { appDatabase.ratesDao().insertRates(dummyRates) } returns Unit
        dataSource.insertAllRates(dummyRates)

        coVerify(exactly = 1) {appDatabase.ratesDao().insertRates(dummyRates)}
    }

    @Test
    fun `when fetch all rates should call ratesDao fetch rates`() = runBlocking {

        val dummyRates = listOf(
            RateDto(
                symbol = "brl"
            ),
            RateDto(
                symbol = "eur"
            )
        )
        val dummyFlow = flow { emit(dummyRates) }
        coEvery { appDatabase.ratesDao().fetchRates() } returns dummyFlow

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