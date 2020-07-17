package com.dvidal.samplecurrencies.core.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * @author diegovidal on 2020-02-15.
 */

val Context.networkInfo: NetworkInfo? get() =
    (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo