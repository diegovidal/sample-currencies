package com.dvidal.samplecurrencies.features.currencies.data.local.rates

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.dvidal.samplecurrencies.core.datasource.local.AppDatabase
import com.dvidal.samplecurrencies.utils.getOrAwaitValue
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * @author diegovidal on 17/07/20.
 */

@RunWith(RobolectricTestRunner::class)
class RatesDaoTest {

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
    fun `when add rate should return rate`() = runBlocking {

        val dummyRates = listOf(
            RateDto(
                symbol = "brl"
            ),
            RateDto(
                symbol = "eur"
            )
        )

        appDatabase.ratesDao().insertRates(dummyRates)
        val ratesDto = appDatabase.ratesDao().fetchRates()
        assertEquals(2, ratesDto.getOrAwaitValue().size)
    }

    @Test
    fun `when add 2 rates with same symbol should return only 1 rate`() = runBlocking {

        val dummyRates = listOf(
            RateDto(
                symbol = "brl"
            ),
            RateDto(
                symbol = "brl"
            )
        )

        appDatabase.ratesDao().insertRates(dummyRates)
        val ratesDto = appDatabase.ratesDao().fetchRates()
        assertEquals(1, ratesDto.getOrAwaitValue().size)
    }

    @Test
    fun `when add rate and clear should return empty list`() = runBlocking {

        val dummyRates = listOf(
            RateDto(
                symbol = "brl"
            ),
            RateDto(
                symbol = "eur"
            )
        )

        appDatabase.ratesDao().insertRates(dummyRates)
        var ratesDto = appDatabase.ratesDao().fetchRates()
        assertEquals(2, ratesDto.getOrAwaitValue().size)

        appDatabase.ratesDao().clearRates()
        ratesDto = appDatabase.ratesDao().fetchRates()
        assertEquals(0, ratesDto.getOrAwaitValue().size)
    }
}