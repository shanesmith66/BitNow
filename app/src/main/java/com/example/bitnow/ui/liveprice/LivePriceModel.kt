package com.example.bitnow.ui.liveprice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import retrofit2.Callback
import com.example.bitnow.data.CoinDeskApi
import com.example.bitnow.data.CoinDeskApiService
import com.example.bitnow.data.LivePrice
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.net.URL

class LivePriceModel : ViewModel() {


    private var livePrice: LivePrice? = null

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // call getLivePrice() on initialization
    init {
        getLivePrice()
    }

    private val response: LiveData<String>
        get() = _response


    // set liveprice object
    private fun setLivePrice(response: Response<LivePrice>) {
        livePrice = response.body()!!
    }

    // function to fetch the live price
    private fun getLivePrice(){


        CoinDeskApi.retrofitService.getProperties().enqueue(
            object: Callback<LivePrice> {
                override fun onFailure(call: Call<LivePrice>, t: Throwable) {
                    _response.value = "Failure: " + t.message
                    println("Response.body = " + _response.value)
                }

                override fun onResponse(call: Call<LivePrice>, response: Response<LivePrice>) {
                    _response.value = response.body()?.bpi?.USD?.rate// get price in USD from object
                    setLivePrice(response)
                    println("Response.body = " + (livePrice?.bpi?.USD?.rate))

                }
            })

    }

    private val _text = MutableLiveData<String>().apply {
        value = response.value
    }

    // function to get the price as a double
    private fun getPriceAsDouble() : Double? {
        val priceDouble = livePrice?.bpi?.USD?.rate?.replace(",", "")?.toDouble()
        return priceDouble
    }


    // set value of the text to the response, which will  be the price
    var text: LiveData<String> = response

    fun updateCurrency(currency: String) {

        // declare map of currency rates: relative to usd
        // in later versions, the rates will no longer be hardcoded but will fetch real-time currency rates
        // from a 3rd party API
        val currencyMap = mapOf("USD" to 1.0, "CAD" to 1.25,"EUR" to 0.84)

        val currencyRate = currencyMap[currency] // get rate from map
        val price = getPriceAsDouble() // get the original price as a double
//
//
        val output = price?.times(currencyRate!!)

        _response.value = output.toString()



    }


}

