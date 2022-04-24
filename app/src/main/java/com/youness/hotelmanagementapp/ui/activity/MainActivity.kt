package com.youness.hotelmanagementapp.ui.activity

import android.R
import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.lifecycle.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.youness.hotelmanagementapp.databinding.ActivityMainBinding
import com.youness.hotelmanagementapp.databinding.ContentMainBinding
import com.youness.hotelmanagementapp.model.Item
import com.youness.hotelmanagementapp.network.ApiHelper
import com.youness.hotelmanagementapp.network.RetrofitBuilder
import com.youness.hotelmanagementapp.ui.adapter.MainAdapter
import com.youness.hotelmanagementapp.ui.viewmodel.MainViewModel
import com.youness.hotelmanagementapp.ui.viewmodel.ViewModelFactory
import com.youness.hotelmanagementapp.utils.Status.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var container: ContentMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(
                MainViewModel::class.java
            )
    }

    private fun setupUI() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        container = binding.container
        container.recyclerView.layoutManager = LinearLayoutManager(this)
        mainAdapter = MainAdapter(listOf())
        container.recyclerView.addItemDecoration(
            DividerItemDecoration(
                container.recyclerView.context,
                (container.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        container.recyclerView.adapter = mainAdapter
    }

    private fun setupObservers() {
        viewModel.getHotelList().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        container.recyclerView.visibility = View.VISIBLE
                        container.progressBar.visibility = View.GONE
                        resource.data?.let { users -> setAdapter(users.items) }
                    }
                    ERROR -> {
                        container.recyclerView.visibility = View.VISIBLE
                        container.progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    LOADING -> {
                        container.progressBar.visibility = View.VISIBLE
                        container.recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun setAdapter(users: List<Item>) {
        mainAdapter.setHotelList(users)
    }
}