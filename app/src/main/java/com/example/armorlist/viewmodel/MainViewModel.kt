package com.example.armorlist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.armorlist.LOG_TAG
import com.example.armorlist.data.model.Armor
import com.example.armorlist.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    // manage title for the app bar from fragments
    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    fun updateActionBarTitle(title: String) = _title.postValue(title)

    // keep original fetched list to be able to restore on clear filters
    private val _originalArmorList = MutableLiveData<List<Armor>>()
    private val _armorList = MutableLiveData<List<Armor>>()

    // observe list of armor
    val armorList: LiveData<List<Armor>> = _armorList
    fun getArmorListFromAPI() {
        Log.i(LOG_TAG, "Fetching from API...")
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val apiResult = repository.getArmorList()
            if (apiResult.isSuccessful) {
                _armorList.postValue(apiResult.body())
                _originalArmorList.postValue(apiResult.body())
                _loading.postValue(false)
            }
        }
    }

    fun filterArmorList(query: String) {
        _loading.value = true
        // hold filtered items
        val filteredList = arrayListOf<Armor>()

        // hold id of added filtered items in set to avoid duplicates
        val listOfAddedItemsId = mutableSetOf<Int>()

        // run in background in case the list is too long
        viewModelScope.launch(Dispatchers.Default) {
            _originalArmorList.value?.forEach { armorItem ->

                // filter by name and check if this item is already added to filtered list
                armorItem.name?.let { name ->
                    if (name.contains(query, ignoreCase = true) &&
                        !listOfAddedItemsId.contains(armorItem.id)
                    ) {
                        filteredList.add(armorItem)
                        listOfAddedItemsId.add(armorItem.id)
                    }
                }

                // filter by rank and check if this item is already added to filtered list
                armorItem.rank?.let { rank ->
                    if (rank.contains(query, ignoreCase = true) &&
                        !listOfAddedItemsId.contains(armorItem.id)
                    ) {
                        filteredList.add(armorItem)
                        listOfAddedItemsId.add(armorItem.id)
                    }
                }

                // filter by base defense value and check if this item is already added to filtered list
                armorItem.defense?.base?.let { defense ->
                    if (defense.toString().contains(query, ignoreCase = true) &&
                        !listOfAddedItemsId.contains(armorItem.id)
                    ) {
                        filteredList.add(armorItem)
                        listOfAddedItemsId.add(armorItem.id)
                    }
                }
            }
            _armorList.postValue(filteredList)
            _loading.postValue(false)
        }
    }

    // restore with the original list
    fun resetFilterArmorList() {
        _armorList.value = _originalArmorList.value
    }


    // observe loading state to show/hide recycler view
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

}