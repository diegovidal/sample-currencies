package com.dvidal.samplecurrencies.features.currencies.domain.usecases

import com.dvidal.samplecurrencies.core.common.Either
import com.dvidal.samplecurrencies.core.common.MyConstants
import com.dvidal.samplecurrencies.features.currencies.domain.RatesRepository
import com.dvidal.samplecurrencies.features.currencies.presentation.RatePresentation
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @author diegovidal on 18/07/20.
 */
class ChangeRateUseCaseTest {

    private val repository = mockk<RatesRepository>()
    private lateinit var useCase: ChangeRateUseCase

    @Before
    fun setup() {
        useCase = ChangeRateUseCase(repository)
    }

    @Test
    fun `when run use case should call repository fetch reviews`() = runBlocking {

        val ratePresentation = RatePresentation(symbol = MyConstants.BRL)
        coEvery { repository.changeRate(ratePresentation) } returns Either.right(Unit)

        useCase.run(ratePresentation)
        coVerify(exactly = 1) { repository.changeRate(ratePresentation) }
    }
}