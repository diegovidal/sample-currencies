package com.dvidal.samplecurrencies.features.currencies.domain.usecases

import com.dvidal.samplecurrencies.core.common.Either
import com.dvidal.samplecurrencies.core.common.UseCase
import com.dvidal.samplecurrencies.features.currencies.domain.RatesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


/**
 * @author diegovidal on 18/07/20.
 */
class RefreshRatesUseCaseTest {

    private val repository = mockk<RatesRepository>()
    private lateinit var useCase: RefreshRatesUseCase

    @Before
    fun setup() {
        useCase = RefreshRatesUseCase(repository)
    }

    @Test
    fun `when run use case should call repository fetch reviews`() = runBlocking {

        coEvery { repository.refreshRates() } returns Either.right(Unit)

        useCase.run(UseCase.None())
        coVerify(exactly = 1) { repository.refreshRates() }
    }
}