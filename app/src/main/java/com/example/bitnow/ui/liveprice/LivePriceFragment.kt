package com.example.bitnow.ui.liveprice

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bitnow.R

class HomeFragment : Fragment() {

    private val viewModel: LivePriceModel by lazy {
        ViewModelProvider(this).get(LivePriceModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_liveprice, container, false)
        val livePriceEditText: EditText = root.findViewById(R.id.fiat_price_text)
        viewModel.text.observe(viewLifecycleOwner, Observer {
            livePriceEditText.setText("$$it")
        })

        // define spinner
        val spinner: Spinner = root.findViewById(R.id.currency_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        this.context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.currency_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner.adapter = adapter
            }
        }


        // set up hyperlink
        val disclaimerHyperLink = root.findViewById<TextView>(R.id.disclaimer_text)
        disclaimerHyperLink.movementMethod = LinkMovementMethod.getInstance();
        return root
    }






}