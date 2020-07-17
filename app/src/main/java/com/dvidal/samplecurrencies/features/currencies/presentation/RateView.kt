package com.dvidal.samplecurrencies.features.currencies.presentation

import androidx.room.PrimaryKey

/**
 * @author diegovidal on 17/07/20.
 */
data class RatePresentationResponse(
    val baseCurrency: String,
    val rates: List<RatePresentation>
)

data class RatePresentation(
    val symbol: String = "",
    val rateName: Int = -1,
    val value: Float = -1f,
    val image: Int = -1,
    val isDefault: Boolean = false
)