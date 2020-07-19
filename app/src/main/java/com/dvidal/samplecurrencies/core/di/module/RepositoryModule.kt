package com.dvidal.samplecurrencies.core.di.module

import com.dvidal.samplecurrencies.core.datasource.local.AppDatabase
import com.dvidal.samplecurrencies.core.datasource.remote.NetworkHandler
import com.dvidal.samplecurrencies.core.datasource.remote.RemoteApi
import com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency.BaseCurrencyLocalDataSource
import com.dvidal.samplecurrencies.features.currencies.data.local.basecurrency.BaseCurrencyLocalDataSourceImpl
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RatesLocalDataSource
import com.dvidal.samplecurrencies.features.currencies.data.local.rates.RatesLocalDataSourceImpl
import com.dvidal.samplecurrencies.features.currencies.data.remote.RatesRemoteDataSource
import com.dvidal.samplecurrencies.features.currencies.data.remote.RatesRemoteDataSourceImpl
import com.dvidal.samplecurrencies.features.currencies.domain.DataProducerHandler
import com.dvidal.samplecurrencies.features.currencies.domain.RatesRepository
import com.dvidal.samplecurrencies.features.currencies.domain.RatesRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author diegovidal on 2020-07-18.
 */

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRatesLocalDataSource(appDatabase: AppDatabase): RatesLocalDataSource {
        return RatesLocalDataSourceImpl(appDatabase)
    }

    @Singleton
    @Provides
    fun baseCurrencyLocalDataSource(appDatabase: AppDatabase): BaseCurrencyLocalDataSource {
        return BaseCurrencyLocalDataSourceImpl(appDatabase)
    }

    @Singleton
    @Provides
    fun provideRatesRemoteDataSource(
        remoteApi: RemoteApi,
        networkHandler: NetworkHandler
    ): RatesRemoteDataSource {
        return RatesRemoteDataSourceImpl(remoteApi, networkHandler)
    }

    @Singleton
    @Provides
    fun provideDataProducerHandler(): DataProducerHandler {
        return DataProducerHandler()
    }

    @Singleton
    @Provides
    fun provideRatesRepository(
        ratesLocalDataSource: RatesLocalDataSource,
        baseCurrencyLocalDataSource: BaseCurrencyLocalDataSource,
        ratesRemoteDataSource: RatesRemoteDataSource,
        dataProducerHandler: DataProducerHandler
    ): RatesRepository {
        return RatesRepositoryImpl(
            ratesLocalDataSource, baseCurrencyLocalDataSource, ratesRemoteDataSource, dataProducerHandler
        )
    }
}