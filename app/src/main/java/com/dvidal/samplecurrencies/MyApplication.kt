package com.dvidal.samplecurrencies

import android.app.Application
import com.dvidal.samplecurrencies.core.common.BaseAppComponent
import com.dvidal.samplecurrencies.core.di.component.DaggerAppComponent
import com.dvidal.samplecurrencies.core.di.module.ApplicationModule
import timber.log.Timber

/**
 * @author diegovidal on 10/07/20.
 */
class MyApplication: Application(), BaseApplication {

    override val appComponent: BaseAppComponent by lazy {
        DaggerAppComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}

