package com.example.armorlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.armorlist.data.model.Armor

class MainViewModel : ViewModel() {

    // manage title for the app bar from fragments
    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title
    fun updateActionBarTitle(title: String) = _title.postValue(title)

    // observe list of armor
    val armorList = MutableLiveData<List<Armor>>()

}