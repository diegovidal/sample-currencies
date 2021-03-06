package com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.dvidal.samplecurrencies.core.datasource.local.AppDatabase
import com.dvidal.samplecurrencies.core.common.MyConstants
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * @author diegovidal on 17/07/20.
 */

@RunWith(RobolectricTestRunner::class)
class BaseCurrencyDaoTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val appDatabase = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.systemContext,
        AppDatabase::class.java
    )
        .allowMainThreadQueries()
        .build()

    @After
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun `when add base currency should return base currency`() = runBlocking {

        val dummyBaseCurrency = BaseCurrencyDto()

        appDatabase.baseCurrencyDao().insertBaseCurrency(dummyBaseCurrency)
        val baseCurrencyDto = appDatabase.baseCurrencyDao().fetchBaseCurrency()
        assertEquals(dummyBaseCurrency, baseCurrencyDto)
    }

    @Test
    fun `when try to modify base currency should return the value updated using on conflict strategy`() = runBlocking {

        val firstDummyBaseCurrency = BaseCurrencyDto(
            currencySymbol = MyConstants.EUR,
            euroValue = 2.0
        )

        val secondDummyBaseCurrency = BaseCurrencyDto(
            currencySymbol = MyConstants.BRL,
            euroValue = 2.0
        )

        // First time
        appDatabase.baseCurrencyDao().insertBaseCurrency(firstDummyBaseCurrency)
        var baseCurrencyDto = appDatabase.baseCurrencyDao().fetchBaseCurrency()
        assertEquals(firstDummyBaseCurrency.currencySymbol, baseCurrencyDto?.currencySymbol)

        // Trying to change
        appDatabase.baseCurrencyDao().insertBaseCurrency(secondDummyBaseCurrency)
        baseCurrencyDto = appDatabase.baseCurrencyDao().fetchBaseCurrency()
        assertEquals(secondDummyBaseCurrency.currencySymbol, baseCurrencyDto?.currencySymbol)
    }
}