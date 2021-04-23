package com.example.bitnow.ui.liveprice

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Callback
import com.example.bitnow.data.CoinDeskApi
import com.example.bitnow.data.LivePrice
import retrofit2.Call
import retrofit2.Response
import java.math.RoundingMode

class LivePriceModel : ViewModel() {

    private var price: Price = Price("None", 0.0, 0.0)// create price object
    private var livePrice: LivePrice? = null // create object to be parsed by response

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    private var responseReceived = false // variable to indicate whether a response has been received

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

    // function to fetch the live price per bitcoin from the coindesk api
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
                    price.setCurrency("USD")
                    price.setQuantity(1.0)
                    getPriceAsDouble()?.let { price.setRate(it) }
                    price.setPrice()
                    updatePriceDisplay()
                    responseReceived = true

                }
            })

    }

    private val _text = MutableLiveData<String>().apply {
        value = response.value
    }

    // function to get the price as a double
    private fun getPriceAsDouble() : Double? {
        return livePrice?.bpi?.USD?.rate?.replace(",", "")?.toDouble()
    }

    // getEditableAsDouble: returns the object of type editable as a double
    fun getEditableAsDouble(e: Editable?) : Double?{

//        e.toString().replace(",", "")
//        val a: Double? = try {
//            e.toString().toDouble()
//        } catch
//            (e: NumberFormatException)
//            { null }

        return e.toString().replace(",", "").toDoubleOrNull()
    }

    // function to update the price after changing the quantity of bitcoin
    fun updateBitcoinQuantity(x: Double) {
        price.setQuantity(x)

        if (price.getPrice() != x * price.getRate())
            updatePriceDisplay()
    }


    // gets current quantity as a string rounded to 6 decimal places
    fun getQuantityAsString(): String {
        return price.getQuantity().toBigDecimal().setScale(6, RoundingMode.CEILING).toString() // keep 6 decimal places only
    }

    // function to retrieve the current rate of bitcoin
    fun getRate(): Double {
        return price.getRate()
    }


    // set value of the text to the response, which will  be the price
    var text: LiveData<String> = response


    fun updateCurrency(currency: String) {
        price.setCurrency(currency)
        // declare map of currency rates: relative to usd
        // in later versions, the rates will no longer be hardcoded but will fetch real-time currency rates
        // from a 3rd party API
        val currencyMap = mapOf("USD" to 1.0, "CAD" to 1.25,"EUR" to 0.84)

        val currencyRate = currencyMap[currency] // get rate from map
        getPriceAsDouble() // get the original price as a double

        getPriceAsDouble()?.times(currencyRate!!)?.let { price.setRate(it) }

        updatePriceDisplay()

    }

    // function to update the price
    private fun updatePriceDisplay() {
        // set price
        price.setPrice()

        // update price to 2 decimals
        val priceText = price.getPrice().toBigDecimal().setScale(4, RoundingMode.CEILING).toString()

        // set response = priceText
        _response.value = priceText

    }


}

