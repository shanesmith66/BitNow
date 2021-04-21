package com.example.bitnow.ui.pricehistory

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
    private var priceHistory: PriceHistory? = null

    private val _response = MutableLiveData<String>()

    // call getPriceHistory on initialization
    init {
        getPriceHistory()
    }

    // declare response value
    private val response: LiveData<String>
        get() = _response

    private fun getPriceHistory() {

        PriceHistoryApi.retrofitService.getProperties().enqueue(
                object: Callback<PriceHistory> {

                    override fun onFailure(call: Call<PriceHistory>, t: Throwable) {
                        _response.value = "Failure: " + t.message
                        println("pricehistory Response = " + _response.value)
                    }

                    override fun onResponse(call: Call<PriceHistory>, response: Response<PriceHistory>) {
                        _response.value = response.body().toString()
                        priceHistory = response.body()
                        println("pricehistory.bpi = " + (priceHistory?.bpi))
                        println("pricehistory _response.value = " + _response.value)
                    }


                }
        )

    }

    private val _text = MutableLiveData<String>().apply {
        value = response.value // x start y end (dates)
    }

    val text: LiveData<String> = _text


}