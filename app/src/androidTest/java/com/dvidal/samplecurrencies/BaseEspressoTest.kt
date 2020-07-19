package com.dvidal.samplecurrencies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.runner.RunWith

/**
 * @author diegovidal on 2020-07-18.
 */

@RunWith(AndroidJUnit4::class)
abstract class BaseEspressoTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    val application: MockApplication
        get() = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as MockApplication
}