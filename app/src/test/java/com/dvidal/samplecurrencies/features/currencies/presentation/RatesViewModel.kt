package com.dvidal.samplecurrencies.features.currencies.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dvidal.samplecurrencies.core.common.BaseCoroutineDispatcher
import com.dvidal.samplecurrencies.core.common.BaseViewModel
import com.dvidal.samplecurrencies.core.common.SingleLiveEvent
import com.dvidal.samplecurrencies.core.common.UseCase
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import com.dvidal.samplecurrencies.features.currencies.domain.usecases.ChangeRateUseCase
import com.dvidal.samplecurrencies.features.currencies.domain.usecases.FetchRatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author diegovidal on 18/07/20.
 */
class RatesViewModel @Inject constructor(
    private val dispatcher: BaseCoroutineDispatcher,
    private val fetchRatesUseCase: FetchRatesUseCase,
    private val changeRateUseCase: ChangeRateUseCase,
    private val refreshRatesUseCase: FetchRatesUseCase
): BaseViewModel(), RatesViewContract.ViewModel {

    @VisibleForTesting
    val action = SingleLiveEvent<RatesViewContract.Action>()

    private val _requestFetchRates = MutableLiveData<List<RateDto?>>()

    override val states = MediatorLiveData<RatesViewContract.ViewState.State>().apply {

        addSource(_requestFetchRates) { list ->
            val listConverted = list.map { rateDto -> rateDto?.mapperToRatePresentation() }
            if (listConverted.isNotEmpty()) {
                postValue(RatesViewContract.ViewState.State.RatesSuccessState(RatesPresentationResponse(listConverted)))
            }
        }
    }
    override val events = SingleLiveEvent<RatesViewContract.ViewState.Event>().apply {

        addSource(action) {
            handleAction(it)
        }
    }

    override fun invokeAction(actionToInvoke: RatesViewContract.Action) {
        action.postValue(actionToInvoke)
    }

    private fun handleAction(action: RatesViewContract.Action) {

        viewModelScope.launch(dispatcher.IO()) {
            when (action) {
                is RatesViewContract.Action.InitPageAction -> {

                    fetchRates()
                    // TODO: PUT THIS ON WORKMANAGER
                    refreshRatesUseCase.invoke(UseCase.None()).also {
                        it.either(::handleFailure) {}
                    }
                }
                is RatesViewContract.Action.ChangeRateAction -> {
                    changeRateUseCase.invoke(action.rate).also {
                        it.either(::handleFailure) {}
                    }
                }
            }
        }
    }

    private suspend fun fetchRates() {

        states.postValue(RatesViewContract.ViewState.State.RatesLoadingState)
        fetchRatesUseCase.invoke(UseCase.None()).also {

            it.either(::handleFailure) { result ->

                states.apply {
                    viewModelScope.launch(Dispatchers.Main) {
                        addSource(result.asLiveData()) { list ->
                            _requestFetchRates.postValue(list)
                        }
                    }
                }
            }
        }
    }
}