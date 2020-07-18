package com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * @author diegovidal on 18/07/20.
 */

@Dao
interface BaseCurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBaseCurrency(currencyDto: BaseCurrencyDto)

    @Query("SELECT * FROM baseCurrencyDto WHERE -1 LIMIT 1")
    suspend fun fetchBaseCurrency(): BaseCurrencyDto?
}