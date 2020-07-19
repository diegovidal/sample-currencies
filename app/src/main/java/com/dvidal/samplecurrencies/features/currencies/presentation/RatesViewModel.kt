package com.dvidal.samplecurrencies.features.currencies.presentation

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.dvidal.samplecurrencies.core.common.BaseCoroutineDispatcher
import com.dvidal.samplecurrencies.core.common.BaseViewModel
import com.dvidal.samplecurrencies.core.common.SingleLiveEvent
import com.dvidal.samplecurrencies.core.common.UseCase
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import com.dvidal.samplecurrencies.features.currencies.domain.usecases.ChangeRateUseCase
import com.dvidal.samplecurrencies.features.currencies.domain.usecases.FetchRatesUseCase
import com.dvidal.samplecurrencies.features.currencies.domain.usecases.RefreshRatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author diegovidal on 18/07/20.
 */
class RatesViewModel @Inject constructor(
    private val dispatcher: BaseCoroutineDispatcher,
    private val ratesMapper: RatesMapper,
    private val fetchRatesUseCase: FetchRatesUseCase,
    private val changeRateUseCase: ChangeRateUseCase,
    private val refreshRatesUseCase: RefreshRatesUseCase
): BaseViewModel(), RatesViewContract.ViewModel {

    private val action = SingleLiveEvent<RatesViewContract.Action>()

    @VisibleForTesting
    val requestFetchRates = MutableLiveData<List<RateDto?>>()

    private val _states = MediatorLiveData<RatesViewContract.ViewState.State>().apply {

        addSource(requestFetchRates) { list ->
            ratesMapper.mapperListToRatePresentation(list).also { listConverted ->
                if (listConverted.isNotEmpty()) {
                    postValue(RatesViewContract.ViewState.State.RatesSuccessState(RatesPresentationResponse(listConverted)))
                }
            }
        }
    }

    private val _events = SingleLiveEvent<RatesViewContract.ViewState.Event>().apply {

        addSource(action) {
            handleAction(it)
        }
    }

    override val states: LiveData<RatesViewContract.ViewState.State> = _states
    override val events: LiveData<RatesViewContract.ViewState.Event> = _events

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

        _states.postValue(RatesViewContract.ViewState.State.RatesLoadingState)
        fetchRatesUseCase.invoke(UseCase.None()).also {

            it.either(::handleFailure) { result ->

                _states.apply {
                    viewModelScope.launch(Dispatchers.Main) {
                        addSource(result.asLiveData()) { list ->
                            requestFetchRates.postValue(list)
                        }
                    }
                }
            }
        }
    }
}