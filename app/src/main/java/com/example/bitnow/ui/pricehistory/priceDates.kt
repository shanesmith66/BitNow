package com.example.bitnow.ui.pricehistory

// priceDates class used to store the information recieved from Coindesk's price history API
class PriceDates {

    private var priceDatesMap = mapOf("N/A" to 0.0)
    private var prices: Array<Any> = priceDatesMap.values.toTypedArray()
    private var dates: Array<Any> = priceDatesMap.keys.toTypedArray()

    fun getPriceDatesMap(): Map<String, Double> {
        return priceDatesMap
    }

    fun setPriceDatesMap(map: Map<String, Double>) {
        this.priceDatesMap = map
    }

    fun getPrices(): Array<Any> {
        return prices
    }

    fun setPrices(arr: Array<Any>) {
        prices = arr
    }

    fun getDates(): Array<Any> {
        return dates
    }

    fun setDates(arr: Array<Any>) {
        dates = arr
    }

}