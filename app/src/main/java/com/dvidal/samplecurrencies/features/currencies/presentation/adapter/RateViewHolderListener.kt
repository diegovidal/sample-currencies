package com.dvidal.samplecurrencies.features.currencies.presentation.adapter

import com.dvidal.samplecurrencies.features.currencies.presentation.RatePresentation

/**
 * @author diegovidal on 19/07/20.
 */
interface RateViewHolderListener {

    fun onChangeRateValue(rate: RatePresentation?)
}