package com.dvidal.samplecurrencies.features.currencies.domain.usecases

import com.dvidal.samplecurrencies.core.common.UseCase
import com.dvidal.samplecurrencies.features.currencies.domain.RatesRepository
import com.dvidal.samplecurrencies.features.currencies.presentation.RatePresentation
import dagger.Reusable
import javax.inject.Inject

/**
 * @author diegovidal on 18/07/20.
 */

@Reusable
class ChangeRateUseCase @Inject constructor(
    private val repository: RatesRepository
): UseCase<Unit, RatePresentation>() {

    override suspend fun run(params: RatePresentation) = repository.changeRate(params)
}