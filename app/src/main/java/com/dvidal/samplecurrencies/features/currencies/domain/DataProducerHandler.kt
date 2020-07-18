package com.dvidal.samplecurrencies.features.currencies.domain

import com.dvidal.samplecurrencies.R
import com.dvidal.samplecurrencies.core.datasource.remote.NetworkConstants
import com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency.BaseCurrencyDto
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import com.dvidal.samplecurrencies.features.currencies.data.remote.RatesRemoteResponse

/**
 * @author diegovidal on 18/07/20.
 */
class DataProducerHandler {

    fun produceData(
        ratesRemoteResponse: RatesRemoteResponse?,
        baseCurrencyDto: BaseCurrencyDto
    ): List<RateDto> {

        val usd =
            RateDto(
                symbol = NetworkConstants.USD,
                name = R.string.symbol_usd,
                value = baseCurrencyDto.value.times(ratesRemoteResponse?.rates?.usd ?: 1.0),
                image = R.drawable.ic_launcher_background,
                isDefault = NetworkConstants.DKK == baseCurrencyDto.symbol
            )

        val brl =
            RateDto(
                symbol = NetworkConstants.BRL,
                name = R.string.symbol_brl,
                value = baseCurrencyDto.value.times(ratesRemoteResponse?.rates?.brl ?: 1.0),
                image = R.drawable.ic_launcher_background,
                isDefault = NetworkConstants.DKK == baseCurrencyDto.symbol
            )

        val eur =
            RateDto(
                symbol = NetworkConstants.EUR,
                name = R.string.symbol_eur,
                value = baseCurrencyDto.value,
                image = R.drawable.ic_launcher_background,
                isDefault = NetworkConstants.DKK == baseCurrencyDto.symbol
            )

        val jpy =
            RateDto(
                symbol = NetworkConstants.JPY,
                name = R.string.symbol_jpy,
                value = baseCurrencyDto.value.times(ratesRemoteResponse?.rates?.jpy ?: 1.0),
                image = R.drawable.ic_launcher_background,
                isDefault = NetworkConstants.DKK == baseCurrencyDto.symbol
            )

        val dkk =
            RateDto(
                symbol = NetworkConstants.DKK,
                name = R.string.symbol_dkk,
                value = baseCurrencyDto.value.times(ratesRemoteResponse?.rates?.dkk ?: 1.0),
                image = R.drawable.ic_launcher_background,
                isDefault = NetworkConstants.DKK == baseCurrencyDto.symbol
            )

        return listOf(usd, brl, eur, jpy, dkk)
    }

    fun calculateNewValues(ratesDto: List<RateDto>, baseCurrencyValue: Double): List<RateDto> {
        ratesDto.forEach { it.value = baseCurrencyValue.div(it.value ?: 1.0) }
        return ratesDto
    }

    fun calculateBaseCurrency(rateDto: RateDto, newValue: Double): BaseCurrencyDto {
        return BaseCurrencyDto(
            symbol = rateDto.symbol,
            value = newValue.div(rateDto.value ?: 1.0)
        )
    }
}