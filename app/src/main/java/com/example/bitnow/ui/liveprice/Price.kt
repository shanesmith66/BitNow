package com.example.bitnow.ui.liveprice

import java.util.*

// price class which will hold values to display to the user aka currency, quantity, price in $
class Price constructor(currency: String, quantity:Double, rate: Double){

    private lateinit var currency: String
    private var quantity: Double = 1.0
    private var rate: Double = 1.0
    private var price: Double = rate * quantity

    fun getPrice(): Double {
        return this.price
    }

    fun getQuantity(): Double {
        return this.quantity
    }

    fun getCurrency(): String {
        return this.currency
    }

    fun getRate(): Double {
        return this.rate
    }


    fun setQuantity(q: Double) {
        this.quantity = q
    }

    fun setCurrency(c: String) {
        this.currency = c
    }

    fun setRate(r: Double) {
        this.rate = r
    }

    fun setPrice(p: Double) {
        this.price = p
    }

    fun setPrice() {
        this.price = rate * quantity
    }
}