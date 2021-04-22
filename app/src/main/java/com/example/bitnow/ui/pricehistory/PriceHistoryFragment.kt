package com.example.bitnow.ui.pricehistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bitnow.R
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PriceHistoryFragment : Fragment() {

    private lateinit var priceHistoryViewModel: PriceHistoryViewModel
    private lateinit var datePrices: Array<Any>
    private lateinit var aaChartView: AAChartView
    private lateinit var aaChartModel: AAChartModel


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        priceHistoryViewModel =
                ViewModelProvider(this).get(PriceHistoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_pricehistory, container, false)

        datePrices = priceHistoryViewModel.getPricesAsArray()


        // initialize chart view
        aaChartView = root.findViewById(R.id.aa_chart_view)

        // define chart featuers
        aaChartModel = AAChartModel()
            .chartType(AAChartType.Line)
            .title("BTC Price History")
            .subtitle("[x] to [y]") // where x and y are start and end dates
            .dataLabelsEnabled(true)
            .colorsTheme(arrayOf("#2d8d98", "#24747d", "#59B699", "#4da388"))
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("BTC")
                        .data(datePrices)
                )
            )


        // draw chart
        aaChartView.aa_drawChartWithChartModel(aaChartModel)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}