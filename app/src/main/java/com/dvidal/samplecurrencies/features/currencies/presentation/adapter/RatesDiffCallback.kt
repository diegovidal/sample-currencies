package com.dvidal.samplecurrencies.features.currencies.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dvidal.samplecurrencies.features.currencies.presentation.RatePresentation

/**
 * @author diegovidal on 2020-07-18.
 */
class RatesDiffCallback(private val oldList: List<RatePresentation?>,
                        private val newList: List<RatePresentation?>)
    : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition]?.symbol == newList[newItemPosition]?.symbol
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}