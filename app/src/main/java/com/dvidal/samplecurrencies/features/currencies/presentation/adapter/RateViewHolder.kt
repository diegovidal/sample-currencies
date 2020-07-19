package com.dvidal.samplecurrencies.features.currencies.presentation.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dvidal.samplecurrencies.core.extension.hideKeyboard
import com.dvidal.samplecurrencies.features.currencies.presentation.RatePresentation
import kotlinx.android.synthetic.main.recycler_item_rate.view.*

/**
 * @author diegovidal on 19/07/20.
 */

class RateViewHolder(
    private val view: View,
    private var listener: RateViewHolderListener?
) : RecyclerView.ViewHolder(view) {

    private var rate: RatePresentation? = null

    fun onBind(ratePresentation: RatePresentation?) {

        this.rate = ratePresentation

        val context = view.context

        Glide.with(context)
            .load(ratePresentation?.image)
            .apply(RequestOptions.circleCropTransform())
            .into(view.iv_rate_flag)

        view.tv_rate_symbol?.text = ratePresentation?.symbol
        view.tv_rate_name?.text = context.getString(ratePresentation?.name ?: -1)

        view.et_rate_value?.setText(ratePresentation?.value.toString())

        if (ratePresentation?.isDefault == true) {
            view.et_rate_value?.addTextChangedListener(textWatcher)
            view.et_rate_value?.isEnabled = true
        }
        else{
            view.et_rate_value?.removeTextChangedListener(textWatcher)
            view.et_rate_value?.isEnabled = false
        }

        view.view_overlay_content?.visibility = if (rate?.isDefault == false) View.VISIBLE else View.GONE
        view.view_overlay_content?.setOnClickListener {

            if (rate?.isDefault == false) {
                view.et_rate_value?.hideKeyboard()
                listener?.onChangeRateValue(rate)
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

            try {
                val newValue = p0.toString().toDoubleOrNull()

                if (newValue != null && newValue > 0.0 && rate?.isDefault == true && rate?.value != newValue) {
                    rate?.value = newValue
                    listener?.onChangeRateValue(rate)
                }
            } catch (e: Exception) { }
        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }
}