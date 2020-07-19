package com.dvidal.samplecurrencies.core.common

import androidx.lifecycle.ViewModelProvider
import com.dvidal.samplecurrencies.core.di.component.ActivityComponent

/**
 * @author diegovidal on 2020-07-18.
 */
interface BaseAppComponent {

    fun activityComponent(): ActivityComponent.Builder
    val viewModelFactor: ViewModelProvider.Factory
}