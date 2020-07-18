package com.dvidal.samplecurrencies.features.currencies.presentation

import androidx.lifecycle.MediatorLiveData
import com.dvidal.samplecurrencies.core.common.SingleLiveEvent

/**
 * @author diegovidal on 18/07/20.
 */
sealed class RatesViewContract {

    interface ViewModel {

        val states: MediatorLiveData<ViewState.State>
        val events: SingleLiveEvent<ViewState.Event>

        fun invokeAction(actionToInvoke: Action)
    }

    sealed class Action: RatesViewContract() {

        object InitPageAction : Action()
        data class ChangeRateAction(val rate: RatePresentation) : Action()
    }

    sealed class ViewState: RatesViewContract() {

        sealed class State: ViewState() {

            data class RatesSuccessState(val ratesPresentationResponse: RatesPresentationResponse): State()
            object RatesLoadingState: State()
        }

        sealed class Event: ViewState() {}
    }
}