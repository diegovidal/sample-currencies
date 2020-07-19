package com.dvidal.samplecurrencies.core.di.module

import com.dvidal.samplecurrencies.BuildConfig
import com.dvidal.samplecurrencies.core.datasource.remote.RemoteApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Call
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author diegovidal on 2020-07-18.
 */
@Module
class RemoteModule {


    @Provides
    @Singleton
    fun provideOkHttp(): Call.Factory {
        return OkHttpClient.Builder()
            .readTimeout(10L, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {

        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, callFactory: Call.Factory): Retrofit {

        return Retrofit.Builder()
            .callFactory(callFactory)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(API_ENDPOINT)
            .build()
    }

    @Provides
    @Singleton
    fun provideBetService(retrofit: Retrofit): RemoteApi {

        return retrofit.create(RemoteApi::class.java)
    }

    companion object {

        private const val API_ENDPOINT = BuildConfig.API_ENDPOINT
    }
}