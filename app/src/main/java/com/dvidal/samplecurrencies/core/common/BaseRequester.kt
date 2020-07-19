package com.dvidal.samplecurrencies.core.common

import com.dvidal.samplecurrencies.core.datasource.remote.NetworkHandler
import com.dvidal.samplecurrencies.core.datasource.remote.RemoteFailure
import timber.log.Timber

/**
 * @author diegovidal on 2020-07-18.
 */

abstract class BaseRequester(
    private val networkHandler: NetworkHandler
) {

    suspend fun <T, R> request(apiCall: suspend() -> T, transform: (T) -> R, default: T): EitherResult<R> {
        return when (networkHandler.isConnected) {
            true -> requestHttp(apiCall, transform, default)
            false, null -> EitherResult.left(RemoteFailure.NetworkConnection())
        }
    }

    private suspend fun <T, R> requestHttp(apiCall: suspend() -> T, transform: (T) -> R, default: T): EitherResult<R> {
        return try {
            val response = apiCall.invoke()
            Timber.e("Response: $response")
            EitherResult.right(transform((response ?: default)))
        } catch (exception: Throwable) {
            Timber.e("Error: $exception")
            EitherResult.left(RemoteFailure.ServerError())
        }
    }
}