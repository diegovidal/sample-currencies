package com.dvidal.samplecurrencies.features.currencies.data.remote

import com.dvidal.samplecurrencies.core.datasource.remote.NetworkConstants
import com.dvidal.samplecurrencies.core.datasource.remote.NetworkConstants.BASE_CURRENCY
import com.dvidal.samplecurrencies.core.datasource.remote.NetworkConstants.RATES
import com.squareup.moshi.Json

data class RatesRemoteResponse(
    @Json(name = BASE_CURRENCY) val baseCurrency: String?,
    @Json(name = RATES) val rates: RatesRemote?
) {

    companion object {

        fun empty() = RatesRemoteResponse(null, null)
    }
}

data class RatesRemote(
    @Json(name = NetworkConstants.AUD) val aud: Double,
    @Json(name = NetworkConstants.BGN) val bgn: Double,
    @Json(name = NetworkConstants.BRL) val brl: Double,
    @Json(name = NetworkConstants.CAD) val cad: Double,
    @Json(name = NetworkConstants.CHF) val chf: Double,
    @Json(name = NetworkConstants.CNY) val cny: Double,
    @Json(name = NetworkConstants.CZK) val czk: Double,
    @Json(name = NetworkConstants.DKK) val dkk: Double,
    @Json(name = NetworkConstants.GBP) val gbp: Double,
    @Json(name = NetworkConstants.HKD) val hkd: Double,
    @Json(name = NetworkConstants.HRK) val hrk: Double,
    @Json(name = NetworkConstants.HUF) val huf: Double,
    @Json(name = NetworkConstants.IDR) val idr: Double,
    @Json(name = NetworkConstants.ILS) val ils: Double,
    @Json(name = NetworkConstants.INR) val inr: Double,
    @Json(name = NetworkConstants.ISK) val isk: Double,
    @Json(name = NetworkConstants.JPY) val jpy: Double,
    @Json(name = NetworkConstants.KRW) val krw: Double,
    @Json(name = NetworkConstants.MXN) val mxn: Double,
    @Json(name = NetworkConstants.MYR) val myr: Double,
    @Json(name = NetworkConstants.NOK) val nok: Double,
    @Json(name = NetworkConstants.NZD) val nzd: Double,
    @Json(name = NetworkConstants.PHP) val php: Double,
    @Json(name = NetworkConstants.PLN) val pln: Double,
    @Json(name = NetworkConstants.RON) val ron: Double,
    @Json(name = NetworkConstants.RUB) val rub: Double,
    @Json(name = NetworkConstants.SEK) val sek: Double,
    @Json(name = NetworkConstants.SGD) val sgd: Double,
    @Json(name = NetworkConstants.THB) val thb: Double,
    @Json(name = NetworkConstants.USD) val usd: Double,
    @Json(name = NetworkConstants.ZAR) val zar: Double
)