package com.dvidal.samplecurrencies.features.currencies.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dvidal.samplecurrencies.R
import com.dvidal.samplecurrencies.core.common.BaseFragment
import com.dvidal.samplecurrencies.features.currencies.presentation.adapter.RateViewHolderListener
import com.dvidal.samplecurrencies.features.currencies.presentation.adapter.RatesAdapter
import kotlinx.android.synthetic.main.fragment_rates.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * @author diegovidal on 17/07/20.
 */
class RatesFragment : BaseFragment(), RateViewHolderListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var ratesAdapter: RatesAdapter

    private var timer: Timer? = null

    private val viewModel: RatesViewContract.ViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(RatesViewModel::class.java)
    }

    override fun layoutRes() = R.layout.fragment_rates
    override fun injectDagger() = component.inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()

        viewModel.states.observe(viewLifecycleOwner, Observer(::renderStates))
        viewModel.events.observe(viewLifecycleOwner, Observer(::renderEvents))
        viewModel.invokeAction(RatesViewContract.Action.InitPageAction)
    }

    override fun onResume() {
        super.onResume()
        startTimer()
    }

    override fun onPause() {
        timer?.cancel()
        super.onPause()
    }

    private fun renderStates(state: RatesViewContract.ViewState.State) {

        when (state) {
            is RatesViewContract.ViewState.State.RatesSuccessState -> {
                ratesAdapter.updateDataSet(state.response.rates)
            }
            RatesViewContract.ViewState.State.RatesLoadingState -> {
                Timber.d("RatesLoadingState")
            }
        }.also { renderContentVisibility(state) }

    }

    private fun renderEvents(state: RatesViewContract.ViewState.Event) {}

    private fun renderContentVisibility(state: RatesViewContract.ViewState.State) {

        rv_rates.visibility =
            if (state is RatesViewContract.ViewState.State.RatesSuccessState) View.VISIBLE else View.GONE
        pb_rates.visibility =
            if (state is RatesViewContract.ViewState.State.RatesLoadingState) View.VISIBLE else View.GONE
    }

    private fun configureRecyclerView() {

        ratesAdapter.configureListener(this)
        rv_rates.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_rates?.setHasFixedSize(true)
        rv_rates.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rv_rates.adapter = ratesAdapter
    }

    private fun startTimer() {

        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                viewModel.invokeAction(RatesViewContract.Action.RefreshRates)
            }
        }, 1000, 1000)
    }

    override fun onChangeRateValue(rate: RatePresentation?) {
        rate?.let { viewModel.invokeAction(RatesViewContract.Action.ChangeRateAction(it)) }
    }

    companion object {

        fun newInstance() = RatesFragment()
    }
}