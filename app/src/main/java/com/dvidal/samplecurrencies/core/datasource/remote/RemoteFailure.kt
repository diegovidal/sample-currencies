package com.dvidal.samplecurrencies.core.datasource.remote

/**
 * @author diegovidal on 2020-07-18.
 */

sealed class RemoteFailure(errorMsg: String) : Throwable(errorMsg) {

    class NetworkConnection : RemoteFailure("Network Connection Error")
    class ServerError : RemoteFailure("Server Error")

    class ErrorLoadingData : RemoteFailure("Error Loading Data")
}