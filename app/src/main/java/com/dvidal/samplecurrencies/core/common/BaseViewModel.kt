package com.dvidal.samplecurrencies.core.common

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.dvidal.samplecurrencies.BaseApplication

/**
 * @author diegovidal on 2020-07-18.
 */

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected val failure by lazy { SingleLiveEvent<Throwable>() }

    open fun handleFailure(failure: Throwable) {
        this.failure.postValue(failure)
    }
}