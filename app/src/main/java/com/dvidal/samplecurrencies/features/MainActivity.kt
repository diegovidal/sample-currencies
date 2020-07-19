package com.dvidal.samplecurrencies.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dvidal.samplecurrencies.R
import com.dvidal.samplecurrencies.features.currencies.presentation.RatesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, RatesFragment.newInstance())
                .commit()
        }
    }
}