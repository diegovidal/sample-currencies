package com.dvidal.samplecurrencies.features.currencies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * @author diegovidal on 17/07/20.
 */

@Dao
interface RatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(ratesDto: List<RateDto>)

    @Query("SELECT * FROM rateDto")
    fun fetchRates(): Flow<List<RateDto?>>

    @Query("DELETE FROM rateDto")
    suspend fun clearRates()
}