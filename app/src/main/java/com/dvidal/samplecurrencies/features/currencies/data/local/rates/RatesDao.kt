package com.dvidal.samplecurrencies.features.currencies.data.local.rates

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import kotlinx.coroutines.flow.Flow

/**
 * @author diegovidal on 17/07/20.
 */

@Dao
interface RatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(ratesDto: List<RateDto?>?)

    @Query("SELECT * FROM rateDto ORDER BY isDefault DESC ")
    fun fetchRates(): List<RateDto?>

    @Query("SELECT * FROM rateDto ORDER BY isDefault DESC ")
    fun fetchRatesAsFlow(): Flow<List<RateDto?>>

    @Query("DELETE FROM rateDto")
    suspend fun clearRates()
}