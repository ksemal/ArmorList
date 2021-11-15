package com.example.armorlist.data.model




data class Armor(
    val armorSet: ArmorSet?,
    val assets: Assets?,
    val attributes: Attributes?,
    val crafting: Crafting?,
    val defense: Defense?,
    val id: Int,
    val name: String?,
    val rank: String?,
    val rarity: Int?,
    val resistances: Resistances?,
    val skills: List<Skill>?,
    val slots: List<Slot>?,
    val type: String?
)