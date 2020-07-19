package com.dvidal.samplecurrencies.di

import androidx.lifecycle.ViewModelProvider
import com.dvidal.samplecurrencies.core.common.BaseAppComponent
import com.dvidal.samplecurrencies.core.di.component.ActivityComponent
import dagger.Component
import javax.inject.Singleton

/**
 * @author diegovidal on 2020-07-19.
 */
@Singleton
@Component(modules = [
    ViewModelTestModule::class
])
interface AppTestComponent: BaseAppComponent {

    override fun activityComponent(): ActivityComponent.Builder
    override val viewModelFactor: ViewModelProvider.Factory
}