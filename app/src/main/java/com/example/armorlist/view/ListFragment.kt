package com.example.armorlist.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.armorlist.LOG_TAG
import com.example.armorlist.R
import com.example.armorlist.databinding.ListFragmentBinding
import com.example.armorlist.viewmodel.MainViewModel

class ListFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ListFragmentBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        
        viewModel.updateActionBarTitle(requireContext().getString(R.string.list_fragment_label))

        //fetch data from API only for the first time
        if (viewModel.armorList.value == null) {
            viewModel.getArmorListFromAPI()
        }

        // observe API response and populate recycler view
        viewModel.armorList.observe(viewLifecycleOwner, { list ->
            Log.i(LOG_TAG, list.toString())
        })
    }

}