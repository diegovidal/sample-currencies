package com.dvidal.samplecurrencies.features.currencies.presentation

import androidx.fragment.app.Fragment
import com.dvidal.samplecurrencies.R
import com.dvidal.samplecurrencies.core.common.BaseFragment

/**
 * @author diegovidal on 17/07/20.
 */
class RatesFragment: BaseFragment() {

    override fun layoutRes() = R.layout.fragment_rates
    override fun injectDagger() = component.inject(this)

    companion object {

        fun newInstance() = RatesFragment()
    }
}