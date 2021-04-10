package com.example.bitnow.ui.liveprice

import android.app.Activity
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import com.example.bitnow.R

class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

    // set default currency
    var currency = "USD"


    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val spinner: Spinner = findViewById(R.id.currency_spinner)
        spinner.onItemSelectedListener = this
        val selection = parent.getItemAtPosition(pos)
        currency = selection as String
//        LivePriceModel.updateCurrency(currency)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

}