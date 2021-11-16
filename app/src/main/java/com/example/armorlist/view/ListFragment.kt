package com.example.armorlist.view

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
        if (activity is MainActivity) {
            (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
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

        // observe loading state to show/hide progress bar
        viewModel.loading.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
                binding.rv.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.rv.visibility = View.VISIBLE
            }
        })

        // observe API response and populate recycler view
        viewModel.armorList.observe(viewLifecycleOwner, { list ->
            // populate recycler view with data
            rvAdapter.setArmorList(list)
        })

        // real time query search
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let {
                    if (it.isEmpty()) {
                        viewModel.resetFilterArmorList()
                    } else {
                        viewModel.filterArmorList(p0)
                    }
                }
                return true
            }
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
                outRect.top =
                    requireContext().resources.getDimensionPixelSize(R.dimen.item_space)
                outRect.right =
                    requireContext().resources.getDimensionPixelSize(R.dimen.item_space)
                outRect.left =
                    requireContext().resources.getDimensionPixelSize(R.dimen.item_space)
            }
        })

        // navigate to details fragment
        rvAdapter.setOnItemClickListener { armorItem ->
            viewModel.setSelectedArmorItem(armorItem)
            findNavController().navigate(R.id.action_listFragment_to_itemDetailsFragment)
        }
        binding.rv.adapter = rvAdapter
    }

}