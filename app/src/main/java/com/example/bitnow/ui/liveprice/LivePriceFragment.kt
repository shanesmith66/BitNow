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

class LivePriceFragment : Fragment(), AdapterView.OnItemSelectedListener {

    // declare model
    private val viewModel: LivePriceModel by lazy {
        ViewModelProvider(this).get(LivePriceModel::class.java)
    }

    var ignoreChange: Boolean = false // boolean to track whether or not to ignore text changes


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
        bitcoinQuantityText.setText("1.0") // set text to 1 by default

        viewModel.text.observe(viewLifecycleOwner, Observer {
            if (!ignoreChange) {
                ignoreChange = true
                val position = livePriceEditText.selectionStart
                livePriceEditText.setText("$$it")
                livePriceEditText.setSelection(position)
                ignoreChange = false
            }
        })


        // add listener for when the bitcoin quantity text is edited by the user
        bitcoinQuantityText.addTextChangedListener(object: TextWatcher {

            var currentText: String = bitcoinQuantityText.text.toString()

            override fun afterTextChanged(s: Editable?) {
                if (!ignoreChange) {
                    val x: Double? =
                        if (bitcoinQuantityText.text.toString().isNotEmpty()) bitcoinQuantityText.text.toString().toDoubleOrNull()
                        else 0.0

                    if (x != null) {
                        viewModel.updateBitcoinQuantity(x)
                        currentText = bitcoinQuantityText.text.toString()

                    }
                    else {  // ignore input of invalid characters (non-numeric)
                        val position = bitcoinQuantityText.selectionStart
                        bitcoinQuantityText.setText(currentText)
                        bitcoinQuantityText.setSelection(position)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
//
//      add listener for when the livePriceEditText is edited by the user
        livePriceEditText.addTextChangedListener(object: TextWatcher {

            var currentText: String = livePriceEditText.text.toString()


            override fun afterTextChanged(s: Editable?) {

                if (!ignoreChange && livePriceEditText.text.toString() != "Infinity") {
                    ignoreChange = true
                    val x = viewModel.getEditableAsDouble(livePriceEditText.text)
                    if (x != null) {
                        if (x.isFinite())
                        viewModel.updateBitcoinQuantity(x/viewModel.getRate())
                    }
                    else {  // ignore input of invalid characters (non-numeric)
                        if (livePriceEditText.text.isEmpty()) { // if its empty set as nothing
                            livePriceEditText.setText("")
                        }
                        else {
                            livePriceEditText.setText(currentText)
                        }
                    }
                    val position = livePriceEditText.selectionStart
                    bitcoinQuantityText.setText(viewModel.getQuantityAsString())
                    livePriceEditText.setSelection(position)
                    currentText = livePriceEditText.text.toString()
                    ignoreChange = false

                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


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