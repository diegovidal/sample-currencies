package com.dvidal.samplecurrencies.core.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RatesDao

/**
 * @author diegovidal on 17/07/20.
 */
@Database(entities = [
    RateDto::class
], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun ratesDao(): RatesDao
}