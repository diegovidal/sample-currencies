package com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency

import androidx.room.Entity
import com.dvidal.samplecurrencies.core.datasource.remote.NetworkConstants

/**
 * @author diegovidal on 18/07/20.
 */

@Entity
data class BaseCurrencyDto(
    val symbol: String = NetworkConstants.EUR,
    val value: Double = 1.0
)