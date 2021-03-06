package com.dvidal.samplecurrencies.features.currencies.domain

import com.dvidal.samplecurrencies.R
import com.dvidal.samplecurrencies.core.common.MyConstants
import com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency.BaseCurrencyDto
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import com.dvidal.samplecurrencies.features.currencies.data.remote.RatesRemoteResponse

/**
 * @author diegovidal on 18/07/20.
 */
class DataProducerHandler {

    private var listRates: List<RateDto?> = emptyList()

    fun fetchListRates() = listRates

    fun updateListRates(list: List<RateDto?>) {
        listRates = list
    }

    fun produceData(
        ratesRemoteResponse: RatesRemoteResponse?,
        baseCurrencyDto: BaseCurrencyDto?
    ): List<RateDto> {

        val usd =
            RateDto(
                symbol = MyConstants.USD,
                name = R.string.symbol_usd,
                value = baseCurrencyDto?.euroValue?.times(ratesRemoteResponse?.rates?.usd ?: 0.0) ?: 0.0,
                image = R.drawable.ic_usd,
                isDefault = MyConstants.USD == baseCurrencyDto?.currencySymbol
            )

        val brl =
            RateDto(
                symbol = MyConstants.BRL,
                name = R.string.symbol_brl,
                value = baseCurrencyDto?.euroValue?.times(ratesRemoteResponse?.rates?.brl ?: 0.0) ?: 0.0,
                image = R.drawable.ic_brl,
                isDefault = MyConstants.BRL == baseCurrencyDto?.currencySymbol
            )

        val eur =
            RateDto(
                symbol = MyConstants.EUR,
                name = R.string.symbol_eur,
                value = baseCurrencyDto?.euroValue ?: 0.0,
                image = R.drawable.ic_eur,
                isDefault = MyConstants.EUR == baseCurrencyDto?.currencySymbol
            )

        val jpy =
            RateDto(
                symbol = MyConstants.JPY,
                name = R.string.symbol_jpy,
                value = baseCurrencyDto?.euroValue?.times(ratesRemoteResponse?.rates?.jpy ?: 0.0) ?: 0.0,
                image = R.drawable.ic_jpy,
                isDefault = MyConstants.JPY == baseCurrencyDto?.currencySymbol
            )

        val dkk =
            RateDto(
                symbol = MyConstants.DKK,
                name = R.string.symbol_dkk,
                value = baseCurrencyDto?.euroValue?.times(ratesRemoteResponse?.rates?.dkk ?: 0.0) ?: 0.0,
                image = R.drawable.ic_dkk,
                isDefault = MyConstants.DKK == baseCurrencyDto?.currencySymbol
            )

        return listOf(usd, brl, eur, jpy, dkk)
    }

    fun calculateNewValues(ratesDto: List<RateDto?>?, baseCurrency: BaseCurrencyDto?): List<RateDto?>? {
        ratesDto?.forEach {
            it?.value = baseCurrency?.euroValue?.times(it?.value ?: 0.0) ?: 0.0
            it?.isDefault = it?.symbol == baseCurrency?.currencySymbol
        }
        return ratesDto
    }

    fun calculateBaseCurrency(rateDto: RateDto?, newValue: Double?): BaseCurrencyDto {
        return BaseCurrencyDto(
            currencySymbol = rateDto?.symbol,
            euroValue = newValue?.div(rateDto?.value ?: 1.0) ?: 1.0
        )
    }

    fun calculateNewEuroValue(list: List<RateDto?>, baseCurrency: BaseCurrencyDto?, ratesRemoteResponse: RatesRemoteResponse?): BaseCurrencyDto? {

        val simpleValue = list.first { it?.symbol == MyConstants.DKK }?.value
        val newValue = (baseCurrency?.euroValue?.times(ratesRemoteResponse?.rates?.dkk ?: 1.0))?.div(simpleValue ?: 1.0)
        val currentValue = baseCurrency?.euroValue?.times(newValue ?: 1.0)
        return baseCurrency.also {
           it?. euroValue = currentValue ?: 1.0
        }
    }
}