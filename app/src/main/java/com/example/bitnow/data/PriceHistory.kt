package com.example.bitnow.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// sample response:
//{"bpi":
//      {"2013-09-01":128.2597,"2013-09-02":127.3648,"2013-09-03":127.5915,"2013-09-04":120.5738,"2013-09-05":120.5333},
//      "disclaimer":"This data was produced from the CoinDesk Bitcoin Price Index. BPI value data returned as USD.",
//      "time":{"updated":"Sep 6, 2013 00:03:00 UTC","updatedISO":"2013-09-06T00:03:00+00:00"}}

// class to be generated upon parsing the pricehistory json
@JsonClass(generateAdapter = true)
data class PriceHistory (
    @field:Json(name="bpi")val bpi: Map<String, Double> = mapOf("None" to 0.0)// create a map of respective dates and prices

)
