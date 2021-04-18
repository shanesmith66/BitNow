package com.example.bitnow.ui.liveprice

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bitnow.R

class HomeFragment : Fragment(), AdapterView.OnItemSelectedListener {

    // declare model
    private val viewModel: LivePriceModel by lazy {
        ViewModelProvider(this).get(LivePriceModel::class.java)
    }

    // declare variable to track whether first api request has been recieved
    var firstReqReceived: Boolean = false;




    @SuppressLint("SetTextI18n")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_liveprice, container, false)

        val livePriceEditText: EditText = root.findViewById(R.id.fiat_price_text)

        // define bitcoin_quantity_text
        val bitcoinQuantityText: EditText = root.findViewById(R.id.bitcoin_quantity_text)

        viewModel.text.observe(viewLifecycleOwner, Observer {
            livePriceEditText.setText(it)
            firstReqReceived = true
//            bitcoinQuantityText.setText(viewModel.getQuantityAsString())
        })


        // set text to 1


        // add listener for when the bitcoin quantity text is edited by the user
        bitcoinQuantityText.addTextChangedListener(object: TextWatcher {


            override fun afterTextChanged(s: Editable?) {
                val x: Double =
                    if (bitcoinQuantityText.text.toString().isNotEmpty()) bitcoinQuantityText.text.toString().toDouble()
                    else 0.0
                viewModel.updateBitcoinQuantity(x)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val x: Double =
                    if (bitcoinQuantityText.text.toString().isNotEmpty()) bitcoinQuantityText.text.toString().toDouble()
                    else 0.0
                viewModel.updateBitcoinQuantity(x)
            }
        })
//
//      add listener for when the livePriceEditText is edited by the user
        livePriceEditText.addTextChangedListener(object: TextWatcher {


            override fun afterTextChanged(s: Editable?) {
//                if (viewModel.responseIsReceived()) {
//                    viewModel.updateBitcoinQuantity(viewModel.getPriceDisplayed()/viewModel.getRate())
//                }

//                if (firstReqReceived) {
//                    viewModel.updateBitcoinQuantity(viewModel.getPriceDisplayed()/viewModel.getRate())
//                }

//                val x = viewModel.getPriceDisplayed()/viewModel.getRate()
//                viewModel.updateBitcoinQuantity(x)
//                bitcoinQuantityText.setText(viewModel.getQuantityAsString())

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if (viewModel.responseIsReceived()) {
//                    viewModel.updateBitcoinQuantity(viewModel.getPriceDisplayed()/viewModel.getRate())
//                }

//                if (firstReqReceived) {
//                    viewModel.updateBitcoinQuantity(viewModel.getPriceDisplayed()/viewModel.getRate())
//                }
                if (firstReqReceived && livePriceEditText.text.toString() != "Infinity") {
                    val x = viewModel.getEditableAsDouble(livePriceEditText.text)

                    if (x != null) {
                        if (x.isFinite())
                            println("x = " + x)
                            viewModel.updateBitcoinQuantity(x/viewModel.getRate())
                    }

                    bitcoinQuantityText.setText(viewModel.getQuantityAsString())


                }
            }

        })




        // define spinner
        val spinner: Spinner = root.findViewById(R.id.currency_spinner)
        spinner.onItemSelectedListener = this
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

    // override for spinner
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val selection = parent.getItemAtPosition(pos).toString()
        viewModel.updateCurrency(selection)
    }

    // override for spinner
    override fun onNothingSelected(parent: AdapterView<*>) {
    }

}