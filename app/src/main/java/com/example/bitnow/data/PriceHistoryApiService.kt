package com.example.bitnow.data

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.net.URL

// declare base URL
private const val BASE_URL = "https://api.coindesk.com"


//create moshi object
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


// create retrofit object
private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()


// define interface
// defines how to talk to web server using HTTP requests
interface PriceHistoryApiService{
    @GET("v1/bpi/historical/close.json?start=2013-09-01&end=2013-09-05")
    fun getProperties():
            Call<PriceHistory>

}

// CoinDesk Api object
object PriceHistoryApi {
    val retrofitService : PriceHistoryApiService by lazy {
        retrofit.create(PriceHistoryApiService::class.java)
    }
}