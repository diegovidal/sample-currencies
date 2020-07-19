package com.dvidal.samplecurrencies.core.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author diegovidal on 2020-07-18.
 */
@Module
class ApplicationModule(private val appContext: Context) {

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return appContext
    }
}