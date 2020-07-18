package com.dvidal.samplecurrencies.features.currencies.data.remote

import com.dvidal.samplecurrencies.core.common.BaseRequester
import com.dvidal.samplecurrencies.core.common.EitherResult
import com.dvidal.samplecurrencies.core.common.MyConstants
import com.dvidal.samplecurrencies.core.datasource.remote.NetworkHandler
import com.dvidal.samplecurrencies.core.datasource.remote.RemoteApi
import javax.inject.Inject

/**
 * @author diegovidal on 17/07/20.
 */

class RatesRemoteDataSourceImpl @Inject constructor(
    private val remoteApi: RemoteApi,
    networkHandler: NetworkHandler
) : BaseRequester(networkHandler), RatesRemoteDataSource {

    override suspend fun fetchRates(): EitherResult<RatesRemoteResponse> {
        return request(
            apiCall = { remoteApi.fetchRates( MyConstants.EUR) },
            transform = { it },
            default = RatesRemoteResponse.empty()
        )
    }
}