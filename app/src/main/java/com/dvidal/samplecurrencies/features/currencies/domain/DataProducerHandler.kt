package com.dvidal.samplecurrencies.features.currencies.domain

import com.dvidal.samplecurrencies.R
import com.dvidal.samplecurrencies.core.datasource.remote.NetworkConstants
import com.dvidal.samplecurrencies.features.currencies.data.local.RateDto
import com.dvidal.samplecurrencies.features.currencies.data.remote.RatesRemoteResponse
import com.dvidal.samplecurrencies.features.currencies.presentation.RatePresentation

/**
 * @author diegovidal on 18/07/20.
 */
class DataProducerHandler {

    private var baseCurrencyValue = 1.0
    private var currentRate = RatePresentation(symbol = NetworkConstants.EUR)
    private var currentList = emptyList<RateDto>()

    fun produceData(ratesRemoteResponse: RatesRemoteResponse?): List<RateDto> {

        val usd = RateDto(
            symbol = NetworkConstants.USD,
            name = R.string.symbol_usd,
            value = ratesRemoteResponse?.rates?.usd?.div(baseCurrencyValue),
            image = R.drawable.ic_launcher_background,
            isDefault = NetworkConstants.USD == currentRate.symbol
        )

        val brl = RateDto(
            symbol = NetworkConstants.BRL,
            name = R.string.symbol_brl,
            value = ratesRemoteResponse?.rates?.brl?.div(baseCurrencyValue),
            image = R.drawable.ic_launcher_background,
            isDefault = NetworkConstants.BRL == currentRate.symbol
        )

        val eur = RateDto(
            symbol = NetworkConstants.EUR,
            name = R.string.symbol_eur,
            value = 1.0,
            image = R.drawable.ic_launcher_background,
            isDefault = NetworkConstants.EUR == currentRate.symbol
        )

        val jpy = RateDto(
            symbol = NetworkConstants.JPY,
            name = R.string.symbol_jpy,
            value = ratesRemoteResponse?.rates?.jpy?.div(baseCurrencyValue),
            image = R.drawable.ic_launcher_background,
            isDefault = NetworkConstants.JPY == currentRate.symbol
        )

        val dkk = RateDto(
            symbol = NetworkConstants.DKK,
            name = R.string.symbol_dkk,
            value = ratesRemoteResponse?.rates?.dkk?.div(baseCurrencyValue),
            image = R.drawable.ic_launcher_background,
            isDefault = NetworkConstants.DKK == currentRate.symbol
        )

        currentList = listOf(usd, brl, eur, jpy, dkk)
        return currentList
    }

    fun calculateNewValues(ratePresentation: RatePresentation): List<RateDto>  {
        baseCurrencyValue = ratePresentation.value?.div(baseCurrencyValue) ?: 1.0
        currentRate = ratePresentation

        currentList.forEach { it.value = it.value?.div(baseCurrencyValue) }
        return currentList
    }
}