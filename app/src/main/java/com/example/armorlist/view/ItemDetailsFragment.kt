package com.example.armorlist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
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
            binding.name.text = resources.getString(R.string.item_details_name, armor.name)
            binding.type.text = resources.getString(R.string.item_details_type, armor.type)
            binding.rank.text = resources.getString(R.string.item_details_rank, armor.rank)
            binding.rarity.text =
                resources.getString(R.string.item_details_rarity, armor.rarity.toString())
            binding.baseDefense.text =
                resources.getString(
                    R.string.item_details_base_defense,
                    armor.defense?.base.toString()
                )
            binding.maxDefense.text =
                resources.getString(
                    R.string.item_details_max_defense,
                    armor.defense?.max.toString()
                )
            binding.augmentedDefense.text = resources.getString(
                R.string.item_details_augmented_defense,
                armor.defense?.augmented.toString()
            )
            binding.resistanceFire.text = resources.getString(
                R.string.item_details_resistance_fire,
                armor.resistances?.fire.toString()
            )
            binding.resistanceWater.text = resources.getString(
                R.string.item_details_resistance_water,
                armor.resistances?.water.toString()
            )
            binding.resistanceIce.text = resources.getString(
                R.string.item_details_resistance_ice,
                armor.resistances?.ice.toString()
            )
            binding.resistanceThunder.text = resources.getString(
                R.string.item_details_resistance_thunder,
                armor.resistances?.thunder.toString()
            )
            binding.resistanceDragon.text = resources.getString(
                R.string.item_details_resistance_dragon,
                armor.resistances?.dragon.toString()
            )
            binding.armorSet.text = resources.getString(
                R.string.item_details_armor_set,
                armor.armorSet?.name.toString()
            )
            Glide.with(requireActivity())
                .load(armor.assets?.imageMale)
                .into(binding.image)
        })
    }
}
