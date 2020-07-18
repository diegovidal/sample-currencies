package com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dvidal.samplecurrencies.core.datasource.remote.MyConstants

/**
 * @author diegovidal on 18/07/20.
 */

@Entity
data class BaseCurrencyDto(
    @PrimaryKey val id: Int = -1,
    val currencySymbol: String? = MyConstants.EUR,
    val euroValue: Double? = 1.0
) {

    companion object {

        fun firstTime() = BaseCurrencyDto(
            currencySymbol = MyConstants.EUR,
            euroValue = 1.0
        )
    }
}