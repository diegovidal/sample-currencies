package com.dvidal.samplecurrencies.features.currencies.domain.usecases

import com.dvidal.samplecurrencies.core.common.UseCase
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import com.dvidal.samplecurrencies.features.currencies.domain.RatesRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author diegovidal on 18/07/20.
 */

@Reusable
class FetchRatesUseCase @Inject constructor(
    private val repository: RatesRepository
): UseCase<Flow<List<RateDto?>>, UseCase.None>() {

    override suspend fun run(params: None) = repository.fetchRates()
}