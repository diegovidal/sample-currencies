package com.dvidal.samplecurrencies.core.datasource.remote

/**
 * @author diegovidal on 2020-02-15.
 */

sealed class RemoteFailure(errorMsg: String) : Throwable(errorMsg) {

    class NetworkConnection : RemoteFailure("Network Connection Error")
    class ServerError : RemoteFailure("Server Error")

    class ErrorLoadingData : RemoteFailure("Error Loading Data")
}