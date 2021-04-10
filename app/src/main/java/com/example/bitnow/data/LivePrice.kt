package com.example.bitnow.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


//{"time":
//  {"updated":"Apr 4, 2021 18:37:00 UTC","updatedISO":"2021-04-04T18:37:00+00:00","updateduk":"Apr 4, 2021 at 19:37 BST"},
//  "disclaimer":"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org",
//  "bpi":{"USD":{"code":"USD","rate":"58,117.0217","description":"United States Dollar","rate_float":58117.0217}}}

// create LivePrice data class to handle the JSON
@JsonClass(generateAdapter = true)
data class LivePrice (
    @field:Json(name="time") val time: Time,
    val disclaimer: String,
    @field:Json(name="bpi") val bpi: BPI
)

@JsonClass(generateAdapter = true)
data class Time(
    val updated: String
)

@JsonClass(generateAdapter = true)
data class BPI (
    @field:Json(name="USD") val USD: USD
)

@JsonClass(generateAdapter = true)
data class USD (
    val code: String,
    val rate: String,
    val description: String,
    val rate_float: String
)