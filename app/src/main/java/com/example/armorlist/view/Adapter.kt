package com.example.armorlist.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.armorlist.*
import com.example.armorlist.data.model.Armor
import com.example.armorlist.databinding.ListItemBinding

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {
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

    private var onItemClickListener: ((Armor) -> Unit)? = null
    fun setOnItemClickListener(listener: (Armor) -> Unit) {
        onItemClickListener = listener
    }

    inner class ViewHolder(private val view: ListItemBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(armorItem: Armor) {
            view.name.text = armorItem.name
            view.rankValue.text = armorItem.rank
            view.defenseValue.text = armorItem.defense?.base.toString()
            armorItem.slots?.forEachIndexed {i, slot ->
                when (i) {
                    0 -> updateSlotView(view.slot1, slot.rank.toString())
                    1 -> updateSlotView(view.slot2, slot.rank.toString())
                    2 -> updateSlotView(view.slot3, slot.rank.toString())
                    3 -> updateSlotView(view.slotMore, "+")
                }
            }

            view.typeIcon.setImageResource(
                when(armorItem.type) {
                    TYPE_CHEST -> R.drawable.ic_chest
                    TYPE_HEAD -> R.drawable.ic_head
                    TYPE_GLOVES -> R.drawable.ic_gloves
                    TYPE_LEGS -> R.drawable.ic_legs
                    TYPE_SHIELD -> R.drawable.ic_shield
                    TYPE_WAIST -> R.drawable.ic_waist
                    else -> R.drawable.ic_deco
                }
            )

            view.root.setOnClickListener{
                onItemClickListener?.let {
                    it(armorItem)
                }
            }
        }

        private fun updateSlotView(view: TextView, text: String) {
            view.text = text
            view.visibility = View.VISIBLE
        }
    }
}
