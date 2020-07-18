package com.dvidal.samplecurrencies.features.currencies.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author diegovidal on 17/07/20.
 */
@Entity
data class RateDto(
    @PrimaryKey val symbol: String = "",
    val name: Int = -1,
    var value: Double? = -1.0,
    val image: Int = -1,
    val isDefault: Boolean = false
)