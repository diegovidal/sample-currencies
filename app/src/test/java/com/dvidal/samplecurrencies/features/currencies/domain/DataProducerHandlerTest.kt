package com.dvidal.samplecurrencies.features.currencies.domain

import com.dvidal.samplecurrencies.core.common.MyConstants
import com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency.BaseCurrencyDto
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import com.dvidal.samplecurrencies.features.currencies.data.remote.RatesRemote
import com.dvidal.samplecurrencies.features.currencies.data.remote.RatesRemoteResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * @author diegovidal on 18/07/20.
 */
class DataProducerHandlerTest {

    private val handler = DataProducerHandler()

    @Test
    fun `when product data should return a list with usd, brl, eur, jpy and dkk`() {

        val result = handler.produceData(RatesRemoteResponse.empty(), BaseCurrencyDto())

        assertTrue(result.any { it.symbol == MyConstants.USD })
        assertTrue(result.any { it.symbol == MyConstants.BRL })
        assertTrue(result.any { it.symbol == MyConstants.EUR })
        assertTrue(result.any { it.symbol == MyConstants.JPY })
        assertTrue(result.any { it.symbol == MyConstants.DKK })
    }

    @Test
    fun `when produce data should return as euro default`() {

        val result = handler.produceData(RatesRemoteResponse.empty(), BaseCurrencyDto())
        assertTrue(result.find { it.symbol == MyConstants.EUR }?.isDefault ?: false)
    }

    @Test
    fun `when produce data and change currencySymbol should return a correct default result`() {

        val result = handler.produceData(RatesRemoteResponse.empty(), BaseCurrencyDto().apply {
            currencySymbol = MyConstants.BRL
        })
        assertTrue(result.find { it.symbol == MyConstants.BRL }?.isDefault ?: false)
    }

    @Test
    fun `when produce data and change euroValue should return a correct value result`() {

        val baseCurrencyValue = 4.0
        val baseCurrency = BaseCurrencyDto().apply {
            euroValue = baseCurrencyValue
        }

        // Euro value
        var result = handler.produceData(RatesRemoteResponse.empty(), baseCurrency)
        var actual = result.find { it.symbol == MyConstants.EUR }?.value
        assertEquals(baseCurrencyValue, actual)

        // Other rate value
        result = handler.produceData(RatesRemoteResponse.empty().apply { 
            rates = RatesRemote().apply {
                brl = 3.0
            }
        }, baseCurrency)

        val expected = 12.0
        actual = result.find { it.symbol == MyConstants.BRL }?.value
        assertEquals(expected, actual)
    }

    @Test
    fun `when calculate new values should return correct value result`() {

        val listDto = listOf(RateDto().apply {
            symbol = MyConstants.BRL
            value = 3.0
        })

        val expected = 12.0
        val result = handler.calculateNewValues(listDto, BaseCurrencyDto(euroValue = 4.0))
        val actual = result?.find { it?.symbol == MyConstants.BRL }?.value
        assertEquals(expected, actual)
    }

    @Test
    fun `when calculate base currency should return correct base currency dto`() {

        val rateDto = RateDto().apply {
            symbol = MyConstants.BRL
            value = 4.0
        }
        val expected = 3.0
        val result = handler.calculateBaseCurrency(rateDto, 12.0)
        val actual = result.euroValue

        assertEquals(expected, actual)
        assertEquals(result.currencySymbol, MyConstants.BRL)
    }
}