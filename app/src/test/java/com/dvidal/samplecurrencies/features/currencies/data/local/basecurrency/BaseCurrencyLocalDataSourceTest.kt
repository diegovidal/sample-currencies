package com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency

import com.dvidal.samplecurrencies.core.datasource.local.AppDatabase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @author diegovidal on 18/07/20.
 */
class BaseCurrencyLocalDataSourceTest {

    private val appDatabase = mockk<AppDatabase>()
    lateinit var dataSource: BaseCurrencyLocalDataSource

    @Before
    fun setup() {
        dataSource = BaseCurrencyLocalDataSourceImpl(appDatabase)
    }

    @Test
    fun `when insert base currency should call for dao insert base currency`() = runBlocking {

        val dummyBaseCurrency = BaseCurrencyDto()
        coEvery { appDatabase.baseCurrencyDao().insertBaseCurrency(dummyBaseCurrency) } returns Unit

        dataSource.insertBaseCurrency(dummyBaseCurrency)
        coVerify(exactly = 1) {
            appDatabase.baseCurrencyDao().insertBaseCurrency(dummyBaseCurrency)
        }
    }

    @Test
    fun `when fetch base currency should call for dao fetch base currency`() = runBlocking {

        val dummyBaseCurrency = BaseCurrencyDto()
        coEvery { appDatabase.baseCurrencyDao().fetchBaseCurrency() } returns dummyBaseCurrency

        dataSource.fetchBaseCurrency()
        coVerify(exactly = 1) { appDatabase.baseCurrencyDao().fetchBaseCurrency() }
    }
}