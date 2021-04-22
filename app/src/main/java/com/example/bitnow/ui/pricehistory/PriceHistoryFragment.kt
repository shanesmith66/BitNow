package com.example.bitnow.ui.pricehistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.bitnow.R
import com.example.bitnow.ui.liveprice.Price
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AASeries
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PriceHistoryFragment : Fragment() {

    // declare model
    private val priceHistoryViewModel: PriceHistoryViewModel by lazy {
        ViewModelProvider(this).get(PriceHistoryViewModel::class.java)
    }

    private lateinit var aaChartView: AAChartView
    private lateinit var aaChartModel: AAChartModel
    private val title = "Bitcoin (BTC) Price History"
    private lateinit var priceDates: PriceDates

    private lateinit var prices: Array<Any>


    // values for starting date and ending date
    private val startDate = "2013-09-01"
    private val endDate = "2013-09-05"


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        priceDates = priceHistoryViewModel.getPriceDatesObject()
        prices = priceDates.getPrices()

        val root = inflater.inflate(R.layout.fragment_pricehistory, container, false)


        priceHistoryViewModel.prices.observe(viewLifecycleOwner, Observer {
            prices = it
            println("prices = " + prices.contentToString())
            // define chart features

            // if the response has been received (aka first element != 0.0)
            // draw the chart
            if (prices[0] != 0.0) {
                aaChartModel = AAChartModel()
                        .chartType(AAChartType.Line)
                        .title(title)
                        .subtitle("From $startDate to $endDate")
                        .dataLabelsEnabled(true)
                        .colorsTheme(arrayOf("#2d8d98", "#24747d", "#59B699", "#4da388"))
                        .series(
                                arrayOf(
                                        AASeriesElement()
                                                .name("BTC")
                                                .data(prices)
                                )
                        )

                // draw chart
                aaChartView.aa_drawChartWithChartModel(aaChartModel)
            }
        })

        // initialize chart view
        aaChartView = root.findViewById(R.id.aa_chart_view)

        return root

    }


}

