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

class PriceHistoryFragment : Fragment() {

    private lateinit var priceHistoryViewModel: PriceHistoryViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        priceHistoryViewModel =
                ViewModelProvider(this).get(PriceHistoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_pricehistory, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        priceHistoryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}