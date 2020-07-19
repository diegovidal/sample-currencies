package com.dvidal.samplecurrencies.features.currencies.data.local.rates

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dvidal.samplecurrencies.features.currencies.presentation.RatePresentation

/**
 * @author diegovidal on 17/07/20.
 */
@Entity
data class RateDto(
    @PrimaryKey var symbol: String = "",
    val name: Int = -1,
    var value: Double? = -1.0,
    val image: Int = -1,
    var isDefault: Boolean = false
) {

    fun mapperToRatePresentation() = RatePresentation(
        symbol = this.symbol,
        name = this.name,
        value = this.value,
        image = this.image,
        isDefault = this.isDefault
    )
}