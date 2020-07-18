package com.dvidal.samplecurrencies.core.common

import androidx.lifecycle.ViewModel

/**
 * @author diegovidal on 2020-02-16.
 */

abstract class BaseViewModel : ViewModel() {

    protected val failure by lazy { SingleLiveEvent<Throwable>() }

    open fun handleFailure(failure: Throwable) {
        this.failure.postValue(failure)
    }
}