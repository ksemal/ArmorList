package com.example.armorlist.data.repository

import com.example.armorlist.data.model.Armor
import com.example.armorlist.network.RetrofitService
import retrofit2.Response

class Repository(private val retrofitService: RetrofitService) {

    suspend fun getArmorList(): Response<List<Armor>> {
        return retrofitService.getArmorList()
    }
}