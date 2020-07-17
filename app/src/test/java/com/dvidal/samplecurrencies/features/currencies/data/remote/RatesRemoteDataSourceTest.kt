package com.dvidal.samplecurrencies.features.currencies.data.remote

import com.dvidal.samplecurrencies.core.datasource.remote.NetworkHandler
import com.dvidal.samplecurrencies.core.datasource.remote.RemoteApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * @author diegovidal on 17/07/20.
 */
class RatesRemoteDataSourceTest {

    private val remoteApi = mockk<RemoteApi>()
    private val networkHandler = mockk<NetworkHandler>()

    private lateinit var remoteDataSource: RatesRemoteDataSource

    @Before
    fun setup() {

        remoteDataSource = RatesRemoteDataSourceImpl(remoteApi, networkHandler)
        every { networkHandler.isConnected } returns true
    }

    @Test
    fun `when fetch rates should call remoteApi fetch rates and return a RatesRemoteResponse`() = runBlocking {

        val symbol = "brl"
        val expectedRemoteResponse = RatesRemoteResponse.empty()
        coEvery { remoteApi.fetchRates(symbol) } returns expectedRemoteResponse

        val remoteResponse = remoteDataSource.fetchRates(symbol).rightOrNull()
        coVerify(exactly = 1) {remoteApi.fetchRates(symbol)}
        assertEquals(expectedRemoteResponse, remoteResponse)
    }
}