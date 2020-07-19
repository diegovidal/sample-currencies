package com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dvidal.samplecurrencies.core.common.MyConstants

/**
 * @author diegovidal on 18/07/20.
 */

@Entity
data class BaseCurrencyDto(
    @PrimaryKey val id: Int = -1,
    var currencySymbol: String? = MyConstants.EUR,
    var euroValue: Double = 1.0
) {

    companion object {

        fun firstTime() = BaseCurrencyDto(
            currencySymbol = MyConstants.EUR,
            euroValue = 1.0
        )
    }
}