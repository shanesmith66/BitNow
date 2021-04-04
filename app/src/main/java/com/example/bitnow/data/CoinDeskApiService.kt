package com.example.bitnow.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.net.URL

// declare base URL
private const val BASE_URL = "https://api.coindesk.com"
//private const val BASE_URL = "https://api.coindesk.com/v1/bpi/currentprice.json/"


// create retrofit object
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

//create moshi object
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// define interface
// defines how to talk to web server using HTTP requests
interface CoinDeskApiService{
    @GET("v1/bpi/currentprice.json")
    fun getProperties():
            Call<String>
}

// CoinDesk Api object
object CoinDeskApi {
    val retrofitService : CoinDeskApiService by lazy {
        retrofit.create(CoinDeskApiService::class.java)
    }
}