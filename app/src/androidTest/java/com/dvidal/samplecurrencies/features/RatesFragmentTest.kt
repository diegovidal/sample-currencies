package com.dvidal.samplecurrencies.features

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.dvidal.samplecurrencies.BaseEspressoTest
import com.dvidal.samplecurrencies.R
import com.dvidal.samplecurrencies.core.common.MyConstants
import com.dvidal.samplecurrencies.features.currencies.presentation.RatePresentation
import com.dvidal.samplecurrencies.features.currencies.presentation.RatesPresentationResponse
import com.dvidal.samplecurrencies.features.currencies.presentation.RatesViewContract
import com.dvidal.samplecurrencies.features.currencies.presentation.RatesViewModel
import com.dvidal.samplecurrencies.utils.RecyclerViewMatcher
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.android.synthetic.main.fragment_rates.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author diegovidal on 2020-02-21.
 */

@RunWith(AndroidJUnit4::class)
class RatesFragmentTest : BaseEspressoTest() {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    private val ratesViewModel = mockk<RatesViewModel>(relaxUnitFun = true)
    private val states = MutableLiveData<RatesViewContract.ViewState.State>()
    private val events = MutableLiveData<RatesViewContract.ViewState.Event>()

    @Before
    fun setup() {

        val viewModelFactory = application.appComponent.viewModelFactor
        every { viewModelFactory.create(RatesViewModel::class.java) } returns ratesViewModel

        every { ratesViewModel.states } returns states
        every { ratesViewModel.events } returns events
    }

    @Test
    fun whenShowRatesFragment_shouldCallInitPageAction() {

        activityRule.launchActivity(Intent())
        verify(exactly = 1) { ratesViewModel.invokeAction(RatesViewContract.Action.InitPageAction) }
    }

    @Test
    fun whenLoadingState_shouldShowProgressBar() {

        activityRule.launchActivity(Intent())
        runBlocking(Dispatchers.Main) {
            states.postValue(RatesViewContract.ViewState.State.RatesLoadingState)
        }

        onView(withId(R.id.pb_rates)).check(matches(isDisplayed()))
    }

    @Test
    fun whenSuccessState_shouldShowRecyclerView() {

        activityRule.launchActivity(Intent())
        runBlocking(Dispatchers.Main) {
            states.postValue(RatesViewContract.ViewState.State.RatesSuccessState(
                RatesPresentationResponse(rates = emptyList())
            ))
        }

        onView(withId(R.id.rv_rates)).check(matches(isDisplayed()))
    }

    @Test
    fun whenSuccessState_andWithRates_shouldShowViewHolder() {

        activityRule.launchActivity(Intent())
        val response = RatesPresentationResponse(
            listOf(RatePresentation(symbol = MyConstants.EUR, isDefault = true), RatePresentation(symbol = MyConstants.BRL))
        )

        runBlocking(Dispatchers.Main) {
            states.postValue(RatesViewContract.ViewState.State.RatesSuccessState(
                response
            ))
        }

        onView(RecyclerViewMatcher().atPositionOnView(1, R.id.tv_rate_symbol, R.id.rv_rates))
            .check(matches(ViewMatchers.withText(MyConstants.BRL)))
    }

    @Test
    fun whenSuccessState_andClickRateNotDefault_shouldCallChangeRateAction() {

        activityRule.launchActivity(Intent())

        val rateBrl = RatePresentation(symbol = MyConstants.BRL)
        val response = RatesPresentationResponse(
            listOf(RatePresentation(symbol = MyConstants.EUR, isDefault = true), rateBrl)
        )

        runBlocking(Dispatchers.Main) {
            states.postValue(RatesViewContract.ViewState.State.RatesSuccessState(
                response
            ))
        }

        onView(RecyclerViewMatcher().atPositionOnView(1, R.id.view_overlay_content, R.id.rv_rates))
            .perform(click())

        verify(exactly = 1) { ratesViewModel.invokeAction(RatesViewContract.Action.ChangeRateAction(rateBrl)) }
    }

    @Test
    fun whenSuccessState_andEditRateDefault_shouldCallChangeRateAction() {

        activityRule.launchActivity(Intent())

        val rateBrl = RatePresentation(symbol = MyConstants.BRL, isDefault = true)
        val response = RatesPresentationResponse(
            listOf(rateBrl, RatePresentation(symbol = MyConstants.EUR))
        )

        runBlocking(Dispatchers.Main) {
            states.postValue(RatesViewContract.ViewState.State.RatesSuccessState(
                response
            ))
        }

        onView(RecyclerViewMatcher().atPositionOnView(0, R.id.et_rate_value, R.id.rv_rates))
            .perform(replaceText("1.0"))
        rateBrl.value = 1.0

        verify(exactly = 1) { ratesViewModel.invokeAction(RatesViewContract.Action.ChangeRateAction(rateBrl)) }
    }
}