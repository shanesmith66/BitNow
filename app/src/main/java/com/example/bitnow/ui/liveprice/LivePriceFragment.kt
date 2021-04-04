package com.example.bitnow.ui.liveprice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bitnow.R
open class URL

class HomeFragment : Fragment() {

    private val viewModel: LivePriceModel by lazy {
        ViewModelProvider(this).get(LivePriceModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val livePriceEditText: EditText = root.findViewById(R.id.text_home)
        viewModel.text.observe(viewLifecycleOwner, Observer {
            livePriceEditText.setText(it)
        })
        return root
    }
}