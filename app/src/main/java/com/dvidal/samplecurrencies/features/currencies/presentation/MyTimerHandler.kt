package com.dvidal.samplecurrencies.features.currencies.presentation

import java.util.*
import javax.inject.Inject

/**
 * @author diegovidal on 20/07/20.
 */
class MyTimerHandler @Inject constructor() {

    private var timer: Timer? = null

    fun cancelTimer() {
        timer?.cancel()
    }

    fun startTimer(block: (Unit) -> Unit) {

        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                block.invoke(Unit)
            }
        }, 1000, 1000)
    }
}