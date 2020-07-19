package com.dvidal.samplecurrencies.features.currencies.presentation

import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto

/**
 * @author diegovidal on 17/07/20.
 */
data class RatesPresentationResponse(
    val rates: List<RatePresentation?>
)

data class RatePresentation(
    val symbol: String,
    val name: Int? = null,
    var value: Double = 0.0,
    val image: Int? = null,
    val isDefault: Boolean = false
) {

    fun mapperToRatePresentation(rateDto: RateDto) = RatePresentation(
        symbol = rateDto.symbol,
        name = rateDto.name,
        value = rateDto.value,
        image = rateDto.image,
        isDefault = rateDto.isDefault
    )
}