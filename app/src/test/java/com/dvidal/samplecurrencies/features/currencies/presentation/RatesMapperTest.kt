package com.dvidal.samplecurrencies.features.currencies.presentation

import com.dvidal.samplecurrencies.core.common.MyConstants
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RateDto
import org.junit.Assert.*
import org.junit.Test

/**
 * @author diegovidal on 18/07/20.
 */
class RatesMapperTest {

    private val ratesMapper = RatesMapper()

    @Test
    fun `when rates mapper should return correct value`() {

        val list = listOf(RateDto(symbol = MyConstants.BRL))
        val listConverted = listOf(RatePresentation(symbol = MyConstants.BRL))

        val actual = ratesMapper.mapperListToRatePresentation(list)
        assertEquals(listConverted.first().symbol, actual.first()?.symbol)
    }
}