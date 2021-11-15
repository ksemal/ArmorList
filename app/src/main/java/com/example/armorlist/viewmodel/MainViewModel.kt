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

    // observe list of armor
    private val _armorList = MutableLiveData<List<Armor>>()
    val armorList: LiveData<List<Armor>> = _armorList
    fun getArmorListFromAPI() {
        Log.i(LOG_TAG, "Fetching from API...")
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val apiResult = repository.getArmorList()
            if (apiResult.isSuccessful) {
                _armorList.postValue(apiResult.body())
                _loading.postValue(false)
            }
        }
    }


    // observe loading state to show/hide recycler view
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

}