package com.dvidal.samplecurrencies.features.currencies.domain.usecases

import com.dvidal.samplecurrencies.core.common.Either
import com.dvidal.samplecurrencies.core.common.UseCase
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import com.dvidal.samplecurrencies.features.currencies.domain.RatesRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @author diegovidal on 18/07/20.
 */
class FetchRatesUseCaseTest {

    private val repository = mockk<RatesRepository>()
    private lateinit var useCase: FetchRatesUseCase

    @Before
    fun setup() {
        useCase = FetchRatesUseCase(repository)
    }

    @Test
    fun `when run use case should call repository fetch rates`() = runBlocking {

        val list = listOf(RateDto())
        val flowSource = flow { emit(list) }
        every { repository.fetchRates() } returns Either.right(flowSource)

        useCase.run(UseCase.None())
        coVerify(exactly = 1) { repository.fetchRates() }
    }
}