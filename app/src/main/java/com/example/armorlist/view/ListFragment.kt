package com.example.armorlist.view

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.armorlist.R
import com.example.armorlist.databinding.ListFragmentBinding
import com.example.armorlist.viewmodel.MainViewModel

class ListFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ListFragmentBinding
    private val rvAdapter: Adapter = Adapter()

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

        // set up recycler view adapter
        setUpAdapter()

        //fetch data from API only for the first time
        if (viewModel.armorList.value == null) {
            viewModel.getArmorListFromAPI()
        }

        // observe API response and populate recycler view
        viewModel.armorList.observe(viewLifecycleOwner, { list ->
            // populate recycler view with data
            rvAdapter.setArmorList(list)
        })
    }

    private fun setUpAdapter() {
        binding.rv.layoutManager = LinearLayoutManager(context)
        binding.rv.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.bottom =
                    requireContext().resources.getDimensionPixelSize(R.dimen.item_space)
            }
        })
        binding.rv.adapter = rvAdapter
    }

}