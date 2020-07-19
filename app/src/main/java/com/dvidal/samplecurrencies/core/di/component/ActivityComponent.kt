package com.dvidal.samplecurrencies.core.di.component

import com.dvidal.samplecurrencies.features.currencies.presentation.RatesFragment
import dagger.Subcomponent

/**
 * @author diegovidal on 2020-07-18.
 */

@Subcomponent
interface ActivityComponent {

    fun inject(ratesFragment: RatesFragment)

    @Subcomponent.Builder
    interface Builder {

        fun build(): ActivityComponent
    }
}