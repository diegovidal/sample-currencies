package com.dvidal.samplecurrencies.features.currencies.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dvidal.samplecurrencies.R
import com.dvidal.samplecurrencies.core.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_rates.*
import timber.log.Timber
import javax.inject.Inject

/**
 * @author diegovidal on 17/07/20.
 */
class RatesFragment: BaseFragment() {

    private var list = listOf<RatePresentation?>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: RatesViewContract.ViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(RatesViewModel::class.java)
    }

    override fun layoutRes() = R.layout.fragment_rates
    override fun injectDagger() = component.inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.states.observe(viewLifecycleOwner, Observer(::renderStates))
        viewModel.events.observe(viewLifecycleOwner, Observer(::renderEvents))
        viewModel.invokeAction(RatesViewContract.Action.InitPageAction)

        my_button.setOnClickListener(::eventClick)
    }

    private fun renderStates(state: RatesViewContract.ViewState.State) {

        when (state) {
            is RatesViewContract.ViewState.State.RatesSuccessState -> {
                Timber.d("RatesSuccessState Aqui! -- ${state.response.rates}")
                list = state.response.rates
            }
            RatesViewContract.ViewState.State.RatesLoadingState -> Timber.d("RatesLoadingState Aqui!")
        }
    }

    private fun eventClick(view: View) {

        list[3]?.let { rate ->
            viewModel.invokeAction(RatesViewContract.Action.ChangeRateAction(rate))
        }
    }

    private fun renderEvents(state: RatesViewContract.ViewState.Event) {}

    companion object {

        fun newInstance() = RatesFragment()
    }
}