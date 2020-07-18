package com.dvidal.samplecurrencies.core.common

import androidx.lifecycle.ViewModelProvider

/**
 * @author diegovidal on 2020-02-18.
 */
interface BaseAppComponent {

    fun activityComponent(): ActivityComponent.Builder
    val viewModelFactor: ViewModelProvider.Factory
}