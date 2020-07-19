package com.dvidal.samplecurrencies.features.currencies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dvidal.samplecurrencies.R
import com.dvidal.samplecurrencies.features.currencies.presentation.RatePresentation
import javax.inject.Inject

/**
 * @author diegovidal on 19/07/20.
 */
class RatesAdapter @Inject constructor(): RecyclerView.Adapter<RateViewHolder>() {

    private var dataSet = emptyList<RatePresentation?>()
    private var listener: RateViewHolderListener? = null

    fun configureListener(listener: RateViewHolderListener) {
        this.listener = listener
    }

    fun updateDataSet(list: List<RatePresentation?>) {

        if (dataSet.isNotEmpty() && verifyIfChangeDefaultRate(list)){
            notifyItemRangeChanged(1, dataSet.size)
        } else {
            val diffResultComplete = DiffUtil.calculateDiff(RatesDiffCallback(this.dataSet, list))
            diffResultComplete.dispatchUpdatesTo(this)
        }.also { this.dataSet = list }
    }

    private fun verifyIfChangeDefaultRate(list: List<RatePresentation?>): Boolean {

        val oldRate = dataSet.first {it?.isDefault == true}
        val newRate = list.first {it?.isDefault == true}

        return oldRate?.symbol == newRate?.symbol
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_rate, parent, false)

        return RateViewHolder(itemView, listener)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {

        val ratePresentation = dataSet[position]
        holder.onBind(ratePresentation)
    }
}