package com.example.bitnow.ui.pricehistory

import android.os.NetworkOnMainThreadException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bitnow.data.LivePrice
import com.example.bitnow.data.PriceHistory
import com.example.bitnow.data.PriceHistoryApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PriceHistoryViewModel : ViewModel() {


    // declare pricehistory object
    private var priceHistory: PriceHistory = PriceHistory()

    // declare priceDates object
    private var priceDates: PriceDates = PriceDates()

    private val _response = MutableLiveData<String>()


    // call getPriceHistory on initialization
    init {
        getPriceHistory()
    }

    // declare response value
    private val response: LiveData<String>
        get() = _response


    // set priceHistory object
    private fun setPriceHistory(response: Response<PriceHistory>) {
        priceHistory = response.body()!!
    }


    // function to get the price history from the api
     private fun getPriceHistory() {


        PriceHistoryApi.retrofitService.getProperties().enqueue(
                object: Callback<PriceHistory> {

                    override fun onFailure(call: Call<PriceHistory>, t: Throwable) {
                        _response.value = "Failure: " + t.message
                        println("pricehistory Response = " + _response.value)
                    }

                    override fun onResponse(call: Call<PriceHistory>, response: Response<PriceHistory>) {
                        _response.value = response.body()?.bpi?.values.toString()
                        setPriceHistory(response)
                        priceDates.setPrices(priceHistory.bpi.values.toTypedArray())
                        priceDates.setDates(priceHistory.bpi.keys.toTypedArray())
                        updatePriceHistory()
                        println("priceDates from model = " + priceDates.getPrices().contentToString())
                        println("pricehistory.bpi = " + (priceHistory.bpi))
                        println("pricehistory _response.value = " + _response.value)
                    }


                })




    }


    private val _text = MutableLiveData<String>().apply {
        value = response.value // x start y end (dates)
    }

    private val _prices = MutableLiveData<Array<Any>>().apply {
        value = priceDates.getPrices()
    }

    val prices: LiveData<Array<Any>> = _prices

    val text: LiveData<String> = _text

    fun getPriceDatesObject(): PriceDates {
        return priceDates
    }

    private fun updatePriceHistory() {
        _prices.value = priceDates.getPrices()
    }


}