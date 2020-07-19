package com.dvidal.samplecurrencies.core.di.component

import androidx.lifecycle.ViewModelProvider
import com.dvidal.samplecurrencies.core.common.BaseAppComponent
import com.dvidal.samplecurrencies.core.di.module.ApplicationModule
import com.dvidal.samplecurrencies.core.di.module.DatabaseModule
import com.dvidal.samplecurrencies.core.di.module.RemoteModule
import com.dvidal.samplecurrencies.core.di.module.RepositoryModule
import com.dvidal.samplecurrencies.core.di.module.viewmodel.ViewModelModule

import dagger.Component
import javax.inject.Singleton

/**
 * @author diegovidal on 2020-07-18.
 */
@Singleton
@Component(modules = [
    ApplicationModule::class,
    DatabaseModule::class,
    RemoteModule::class,
    RepositoryModule::class,
    ViewModelModule::class
])
interface AppComponent: BaseAppComponent {

    override fun activityComponent(): ActivityComponent.Builder
    override val viewModelFactor: ViewModelProvider.Factory
}

