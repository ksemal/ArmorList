package com.example.armorlist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.armorlist.R
import com.example.armorlist.databinding.FragmentItemDetailsBinding
import com.example.armorlist.viewmodel.MainViewModel


class ItemDetailsFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentItemDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (activity is MainActivity) {
            (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        return inflater.inflate(R.layout.fragment_item_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentItemDetailsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        viewModel.updateActionBarTitle(requireContext().getString(R.string.item_details_fragment_label))

        viewModel.selectedArmor.observe(viewLifecycleOwner, { armor ->
            binding.name.text = armor.name
        })
    }
}
