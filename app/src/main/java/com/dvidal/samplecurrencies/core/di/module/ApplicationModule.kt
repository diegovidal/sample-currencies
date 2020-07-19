package com.dvidal.samplecurrencies.core.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author diegovidal on 2020-07-18.
 */
@Module
class ApplicationModule(private val appContext: Application) {

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return appContext.applicationContext
    }

    @Singleton
    @Provides
    fun provideApplication(): Application {
        return appContext
    }
}