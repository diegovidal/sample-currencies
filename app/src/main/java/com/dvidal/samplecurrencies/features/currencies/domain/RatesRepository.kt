package com.dvidal.samplecurrencies.features.currencies.domain

import com.dvidal.samplecurrencies.core.common.EitherResult
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import com.dvidal.samplecurrencies.features.currencies.presentation.RatePresentation
import kotlinx.coroutines.flow.Flow

/**
 * @author diegovidal on 18/07/20.
 */
interface RatesRepository {

    suspend fun refreshRates()
    suspend fun changeValue(ratePresentation: RatePresentation)
    fun fetchRates(): EitherResult<Flow<List<RateDto?>>>
}