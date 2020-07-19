package com.dvidal.samplecurrencies

import android.app.Application
import com.dvidal.samplecurrencies.core.common.BaseAppComponent
import com.dvidal.samplecurrencies.di.DaggerAppTestComponent

/**
 * @author diegovidal on 2020-07-18.
 */
class MockApplication: Application(), BaseApplication {

    override val appComponent: BaseAppComponent by lazy {
        DaggerAppTestComponent
            .builder()
            .build()
    }
}