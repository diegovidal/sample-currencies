package com.dvidal.samplecurrencies.features.currencies.presentation

import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import javax.inject.Inject

class RatesMapper @Inject constructor() {

    fun mapperListToRatePresentation(list: List<RateDto?>): List<RatePresentation?> {
        return list.map { rateDto -> rateDto?.mapperToRatePresentation() }
    }
}
