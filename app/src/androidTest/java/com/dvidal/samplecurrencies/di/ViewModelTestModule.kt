package com.dvidal.samplecurrencies.di

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

/**
 * @author diegovidal on 2020-07-19.
 */
@Module
class ViewModelTestModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(): ViewModelProvider.Factory = mockk()
}
