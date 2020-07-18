package com.dvidal.samplecurrencies.features.currencies.presentation

import androidx.room.PrimaryKey

/**
 * @author diegovidal on 17/07/20.
 */
data class RatesPresentationResponse(
    val baseCurrency: String,
    val rates: List<RatePresentation>
)

data class RatePresentation(
    val symbol: String,
    val rateName: Int? = null,
    val value: Double? = null,
    val image: Int? = null,
    val isDefault: Boolean = false
)