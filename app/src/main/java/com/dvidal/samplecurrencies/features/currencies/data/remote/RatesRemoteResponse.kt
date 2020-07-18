package com.dvidal.samplecurrencies.features.currencies.data.remote

import com.dvidal.samplecurrencies.core.datasource.remote.MyConstants
import com.dvidal.samplecurrencies.core.datasource.remote.MyConstants.BASE_CURRENCY
import com.dvidal.samplecurrencies.core.datasource.remote.MyConstants.RATES
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
    @Json(name = MyConstants.EUR) val eur: Double?,
    @Json(name = MyConstants.AUD) val aud: Double?,
    @Json(name = MyConstants.BGN) val bgn: Double?,
    @Json(name = MyConstants.BRL) val brl: Double?,
    @Json(name = MyConstants.CAD) val cad: Double?,
    @Json(name = MyConstants.CHF) val chf: Double?,
    @Json(name = MyConstants.CNY) val cny: Double?,
    @Json(name = MyConstants.CZK) val czk: Double?,
    @Json(name = MyConstants.DKK) val dkk: Double?,
    @Json(name = MyConstants.GBP) val gbp: Double?,
    @Json(name = MyConstants.HKD) val hkd: Double?,
    @Json(name = MyConstants.HRK) val hrk: Double?,
    @Json(name = MyConstants.HUF) val huf: Double?,
    @Json(name = MyConstants.IDR) val idr: Double?,
    @Json(name = MyConstants.ILS) val ils: Double?,
    @Json(name = MyConstants.INR) val inr: Double?,
    @Json(name = MyConstants.ISK) val isk: Double?,
    @Json(name = MyConstants.JPY) val jpy: Double?,
    @Json(name = MyConstants.KRW) val krw: Double?,
    @Json(name = MyConstants.MXN) val mxn: Double?,
    @Json(name = MyConstants.MYR) val myr: Double?,
    @Json(name = MyConstants.NOK) val nok: Double?,
    @Json(name = MyConstants.NZD) val nzd: Double?,
    @Json(name = MyConstants.PHP) val php: Double?,
    @Json(name = MyConstants.PLN) val pln: Double?,
    @Json(name = MyConstants.RON) val ron: Double?,
    @Json(name = MyConstants.RUB) val rub: Double?,
    @Json(name = MyConstants.SEK) val sek: Double?,
    @Json(name = MyConstants.SGD) val sgd: Double?,
    @Json(name = MyConstants.THB) val thb: Double?,
    @Json(name = MyConstants.USD) val usd: Double?,
    @Json(name = MyConstants.ZAR) val zar: Double?
)