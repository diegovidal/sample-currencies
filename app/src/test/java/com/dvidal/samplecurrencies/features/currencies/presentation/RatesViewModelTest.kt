package com.dvidal.samplecurrencies.features.currencies.presentation

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dvidal.samplecurrencies.core.common.*
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import com.dvidal.samplecurrencies.features.currencies.domain.usecases.ChangeRateUseCase
import com.dvidal.samplecurrencies.features.currencies.domain.usecases.FetchRatesUseCase
import com.dvidal.samplecurrencies.features.currencies.domain.usecases.RefreshRatesUseCase
import com.dvidal.samplecurrencies.utils.MainCoroutineRule
import com.dvidal.samplecurrencies.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author diegovidal on 18/07/20.
 */

@ExperimentalCoroutinesApi
class RatesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val dispatcher = mockk<BaseCoroutineDispatcher>()
    private val ratesMapper = mockk<RatesMapper>(relaxUnitFun = true)
    private val fetchRatesUseCase = mockk<FetchRatesUseCase>(relaxUnitFun = true)
    private val changeRateUseCase = mockk<ChangeRateUseCase>(relaxUnitFun = true)
    private val refreshRatesUseCase = mockk<RefreshRatesUseCase>(relaxUnitFun = true)

    private lateinit var viewModel: RatesViewModel

    @Before
    fun setup() {

        viewModel = RatesViewModel(dispatcher, ratesMapper, fetchRatesUseCase, changeRateUseCase, refreshRatesUseCase)
        mockDispatcher()
        viewModel.events.observeForever {  }
    }

    private fun mockDispatcher() {

        every { dispatcher.IO() } returns coroutineRule.testDispatcher
        every { dispatcher.Main() } returns Dispatchers.Main
    }

    @Test
    fun `when invoke action InitPageAction should return RatesLoadingState`() = coroutineRule.runBlockingTest {

        viewModel.invokeAction(RatesViewContract.Action.InitPageAction)

        val expected = RatesViewContract.ViewState.State.RatesLoadingState
        assertEquals(expected, viewModel.states.getOrAwaitValue())
    }

    @Test
    fun `when invoke action InitPageAction should call for fetch rates use case`() = coroutineRule.runBlockingTest {

        viewModel.invokeAction(RatesViewContract.Action.InitPageAction)
        coVerify(exactly = 1) { fetchRatesUseCase.invoke(any())  }
    }

    @Test
    fun `when invoke action ChangeRateAction should call for change rate use case`() = coroutineRule.runBlockingTest {

        val ratePresentation = RatePresentation(symbol = MyConstants.BRL)
        viewModel.invokeAction(RatesViewContract.Action.ChangeRateAction(ratePresentation))

        coVerify(exactly = 1) { changeRateUseCase.invoke(any()) }
    }

    @Test
    fun `when request fetch rates should return RatesSuccessState`() = coroutineRule.runBlockingTest {

        val list = listOf(RateDto())
        val listConverted = listOf(RatePresentation(symbol = MyConstants.BRL))
        viewModel.requestFetchRates.value = list

        every { ratesMapper.mapperListToRatePresentation(any()) } returns listConverted

        val expected = RatesViewContract.ViewState.State.RatesSuccessState(RatesPresentationResponse(listConverted))
        assertEquals(expected, viewModel.states.getOrAwaitValue())
    }
}