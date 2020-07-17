package com.dvidal.samplecurrencies.features.currencies.data.remote

import com.squareup.moshi.Json

data class RatesRemoteResponse(
    @Json(name = "baseCurrency") val baseCurrency: String?,
    @Json(name = "rates") val rates: RatesRemote?
) {

    companion object {

        fun empty() = RatesRemoteResponse(null, null)
    }
}

data class RatesRemote(
    @Json(name = "AUD") val AUD: Double,
    @Json(name = "BGN") val BGN: Double,
    @Json(name = "BRL") val BRL: Double,
    @Json(name = "CAD") val CAD: Double,
    @Json(name = "CHF") val CHF: Double,
    @Json(name = "CNY") val CNY: Double,
    @Json(name = "CZK") val CZK: Double,
    @Json(name = "DKK") val DKK: Double,
    @Json(name = "GBP") val GBP: Double,
    @Json(name = "HKD") val HKD: Double,
    @Json(name = "HRK") val HRK: Double,
    @Json(name = "HUF") val HUF: Double,
    @Json(name = "IDR") val IDR: Double,
    @Json(name = "ILS") val ILS: Double,
    @Json(name = "INR") val INR: Double,
    @Json(name = "ISK") val ISK: Double,
    @Json(name = "JPY") val JPY: Double,
    @Json(name = "KRW") val KRW: Double,
    @Json(name = "MXN") val MXN: Double,
    @Json(name = "MYR") val MYR: Double,
    @Json(name = "NOK") val NOK: Double,
    @Json(name = "NZD") val NZD: Double,
    @Json(name = "PHP") val PHP: Double,
    @Json(name = "PLN") val PLN: Double,
    @Json(name = "RON") val RON: Double,
    @Json(name = "RUB") val RUB: Double,
    @Json(name = "SEK") val SEK: Double,
    @Json(name = "SGD") val SGD: Double,
    @Json(name = "THB") val THB: Double,
    @Json(name = "USD") val USD: Double,
    @Json(name = "ZAR") val ZAR: Double
)