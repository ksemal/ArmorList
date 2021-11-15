package com.example.armorlist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.armorlist.data.model.Armor
import com.example.armorlist.databinding.ListItemBinding

class Adapter : RecyclerView.Adapter<ViewHolder>() {
    private val list = ArrayList<Armor>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setArmorList(listOfArmor: List<Armor>) {
        list.clear()
        list.addAll(listOfArmor)
        notifyDataSetChanged()
    }
}

class ViewHolder(view: ListItemBinding) : RecyclerView.ViewHolder(view.root) {
    fun bind(armorItem: Armor) {

    }
}