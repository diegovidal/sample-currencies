package com.dvidal.samplecurrencies.features.currencies.domain.usecases

import com.dvidal.samplecurrencies.core.common.UseCase
import com.dvidal.samplecurrencies.features.currencies.domain.RatesRepository
import dagger.Reusable
import javax.inject.Inject

/**
 * @author diegovidal on 18/07/20.
 */

@Reusable
class RefreshRatesUseCase @Inject constructor(
    private val repository: RatesRepository
): UseCase<Unit, UseCase.None>() {

    override suspend fun run(params: None) = repository.refreshRates()
}