package com.dvidal.samplecurrencies.core.common

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-16.
 */
class BaseCoroutineDispatcher @Inject constructor() {

    fun Main() = Dispatchers.Main
    fun IO() = Dispatchers.IO
}