package com.example.bitnow.ui.liveprice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import retrofit2.Callback
import com.example.bitnow.data.CoinDeskApi
import com.example.bitnow.data.CoinDeskApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.net.URL

class LivePriceModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()


    init {
        getLivePrice()
    }

    private val response: LiveData<String>
        get() = _response

    // function to fetch the live price
    private fun getLivePrice(){


        CoinDeskApi.retrofitService.getProperties().enqueue(
            object: Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    _response.value = "Failure: " + t.message
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    _response.value = response.body()
                    println("Response.body = " + _response.value)
                }
            })

    }

    private val _text = MutableLiveData<String>().apply {
        value = response.value
    }

    // set value of the text to the response
    val text: LiveData<String> = response


}

