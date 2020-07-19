package com.dvidal.samplecurrencies

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/**
 * @author diegovidal on 2020-07-18.
 */
class EspressoTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, MockApplication::class.java.name, context)
    }
}