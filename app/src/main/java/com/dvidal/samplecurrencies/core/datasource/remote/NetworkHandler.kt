package com.dvidal.samplecurrencies.core.datasource.remote

import android.content.Context
import com.dvidal.samplecurrencies.core.extension.networkInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author diegovidal on 2020-07-18.
 */

@Singleton
class NetworkHandler @Inject constructor(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnectedOrConnecting
}
