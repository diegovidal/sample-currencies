package com.dvidal.samplecurrencies.features.currencies.data.remote

import com.dvidal.samplecurrencies.core.datasource.remote.MyConstants
import com.dvidal.samplecurrencies.core.datasource.remote.MyConstants.BASE_CURRENCY
import com.dvidal.samplecurrencies.core.datasource.remote.MyConstants.RATES
import com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency.BaseCurrencyDto
import com.squareup.moshi.Json

data class RatesRemoteResponse(
    @Json(name = BASE_CURRENCY) val baseCurrency: String?,
    @Json(name = RATES) var rates: RatesRemote?
) {

    companion object {
        fun empty() = RatesRemoteResponse(null, null)
    }
}

data class RatesRemote(
    @Json(name = MyConstants.EUR) val eur: Double? = null,
    @Json(name = MyConstants.AUD) val aud: Double? = null,
    @Json(name = MyConstants.BGN) val bgn: Double? = null,
    @Json(name = MyConstants.BRL) var brl: Double? = null,
    @Json(name = MyConstants.CAD) val cad: Double? = null,
    @Json(name = MyConstants.CHF) val chf: Double? = null,
    @Json(name = MyConstants.CNY) val cny: Double? = null,
    @Json(name = MyConstants.CZK) val czk: Double? = null,
    @Json(name = MyConstants.DKK) val dkk: Double? = null,
    @Json(name = MyConstants.GBP) val gbp: Double? = null,
    @Json(name = MyConstants.HKD) val hkd: Double? = null,
    @Json(name = MyConstants.HRK) val hrk: Double? = null,
    @Json(name = MyConstants.HUF) val huf: Double? = null,
    @Json(name = MyConstants.IDR) val idr: Double? = null,
    @Json(name = MyConstants.ILS) val ils: Double? = null,
    @Json(name = MyConstants.INR) val inr: Double? = null,
    @Json(name = MyConstants.ISK) val isk: Double? = null,
    @Json(name = MyConstants.JPY) val jpy: Double? = null,
    @Json(name = MyConstants.KRW) val krw: Double? = null,
    @Json(name = MyConstants.MXN) val mxn: Double? = null,
    @Json(name = MyConstants.MYR) val myr: Double? = null,
    @Json(name = MyConstants.NOK) val nok: Double? = null,
    @Json(name = MyConstants.NZD) val nzd: Double? = null,
    @Json(name = MyConstants.PHP) val php: Double? = null,
    @Json(name = MyConstants.PLN) val pln: Double? = null,
    @Json(name = MyConstants.RON) val ron: Double? = null,
    @Json(name = MyConstants.RUB) val rub: Double? = null,
    @Json(name = MyConstants.SEK) val sek: Double? = null,
    @Json(name = MyConstants.SGD) val sgd: Double? = null,
    @Json(name = MyConstants.THB) val thb: Double? = null,
    @Json(name = MyConstants.USD) val usd: Double? = null,
    @Json(name = MyConstants.ZAR) val zar: Double? = null
)