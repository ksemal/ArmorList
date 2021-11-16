package com.example.armorlist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.armorlist.R


class ItemDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (activity is MainActivity) {
            (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        return inflater.inflate(R.layout.fragment_item_details, container, false)
    }
}
