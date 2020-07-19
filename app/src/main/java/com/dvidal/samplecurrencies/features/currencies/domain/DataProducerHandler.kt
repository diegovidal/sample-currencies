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

    fun produceData(
        ratesRemoteResponse: RatesRemoteResponse?,
        baseCurrencyDto: BaseCurrencyDto?
    ): List<RateDto> {

        val usd =
            RateDto(
                symbol = MyConstants.USD,
                name = R.string.symbol_usd,
                value = baseCurrencyDto?.euroValue?.times(ratesRemoteResponse?.rates?.usd ?: 1.0),
                image = R.drawable.ic_launcher_background,
                isDefault = MyConstants.USD == baseCurrencyDto?.currencySymbol
            )

        val brl =
            RateDto(
                symbol = MyConstants.BRL,
                name = R.string.symbol_brl,
                value = baseCurrencyDto?.euroValue?.times(ratesRemoteResponse?.rates?.brl ?: 1.0),
                image = R.drawable.ic_launcher_background,
                isDefault = MyConstants.BRL == baseCurrencyDto?.currencySymbol
            )

        val eur =
            RateDto(
                symbol = MyConstants.EUR,
                name = R.string.symbol_eur,
                value = baseCurrencyDto?.euroValue,
                image = R.drawable.ic_launcher_background,
                isDefault = MyConstants.EUR == baseCurrencyDto?.currencySymbol
            )

        val jpy =
            RateDto(
                symbol = MyConstants.JPY,
                name = R.string.symbol_jpy,
                value = baseCurrencyDto?.euroValue?.times(ratesRemoteResponse?.rates?.jpy ?: 1.0),
                image = R.drawable.ic_launcher_background,
                isDefault = MyConstants.JPY == baseCurrencyDto?.currencySymbol
            )

        val dkk =
            RateDto(
                symbol = MyConstants.DKK,
                name = R.string.symbol_dkk,
                value = baseCurrencyDto?.euroValue?.times(ratesRemoteResponse?.rates?.dkk ?: 1.0),
                image = R.drawable.ic_launcher_background,
                isDefault = MyConstants.DKK == baseCurrencyDto?.currencySymbol
            )

        return listOf(usd, brl, eur, jpy, dkk)
    }

    fun calculateNewValues(ratesDto: List<RateDto?>?, baseCurrency: BaseCurrencyDto?): List<RateDto?>? {
        ratesDto?.forEach {
            it?.value = baseCurrency?.euroValue?.times(it?.value ?: 1.0)
            it?.isDefault = it?.symbol == baseCurrency?.currencySymbol
        }
        return ratesDto
    }

    fun calculateBaseCurrency(rateDto: RateDto?, newValue: Double?): BaseCurrencyDto {
        return BaseCurrencyDto(
            currencySymbol = rateDto?.symbol,
            euroValue = newValue?.div(rateDto?.value ?: 1.0)
        )
    }
}