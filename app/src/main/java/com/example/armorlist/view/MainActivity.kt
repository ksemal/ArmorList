package com.example.armorlist.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.armorlist.data.repository.Repository
import com.example.armorlist.databinding.MainActivityBinding
import com.example.armorlist.network.RetrofitService
import com.example.armorlist.viewmodel.MainViewModel
import com.example.armorlist.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(Repository(RetrofitService.getInstance()))
        )[MainViewModel::class.java]
        viewModel.title.observe(this, {
            supportActionBar?.title = it
        })
    }
}