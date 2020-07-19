package com.dvidal.samplecurrencies.features

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.dvidal.samplecurrencies.BaseEspressoTest
import com.dvidal.samplecurrencies.features.currencies.presentation.RatesViewModel
import io.mockk.every
import io.mockk.mockk
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

    @Before
    fun setup() {

        val viewModelFactory = application.appComponent.viewModelFactor
        every { viewModelFactory.create(RatesViewModel::class.java) } returns ratesViewModel
    }

    @Test
    fun whenShowReviewDetailsFragment_shouldCallInitPageEvent() {

        activityRule.launchActivity(Intent())
    }
}