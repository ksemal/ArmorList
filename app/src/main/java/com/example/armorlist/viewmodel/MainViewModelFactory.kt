package com.example.armorlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.armorlist.data.repository.Repository

class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            repository
        ) as T
    }

//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return if (modelClass.isAssignableFrom(MainViewModelFactory::class.java)) {
//            MainViewModel(this.repository) as T
//        } else {
//            throw IllegalArgumentException("ViewModel Not Found")
//        }
//    }
}
