package com.youness.hotelmanagementapp.ui.activity

import android.R
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.youness.hotelmanagementapp.databinding.ActivityDetailOfferBinding
import com.youness.hotelmanagementapp.databinding.ContentDetailOfferBinding
import com.youness.hotelmanagementapp.databinding.ContentMainBinding
import com.youness.hotelmanagementapp.model.Item
import com.youness.hotelmanagementapp.network.ApiHelper
import com.youness.hotelmanagementapp.network.RetrofitBuilder
import com.youness.hotelmanagementapp.ui.viewmodel.MainViewModel
import com.youness.hotelmanagementapp.ui.viewmodel.ViewModelFactory
import com.youness.hotelmanagementapp.utils.AppConstants
import com.youness.hotelmanagementapp.utils.Status

class DetailOfferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailOfferBinding
    private lateinit var contentDetailOfferBinding: ContentDetailOfferBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var item: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpUI()
        setupViewModel()
        setupObservers(intent.getIntExtra(AppConstants.EXTRA_ID_OBJECT, 0))
    }

    private fun setUpUI() {
        binding = ActivityDetailOfferBinding.inflate(layoutInflater)
        contentDetailOfferBinding = binding.container
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(
                MainViewModel::class.java
            )
    }

    private fun setupObservers(id: Int) {
        viewModel.getHotelById(id).observe(this, Observer { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { fillData(it) }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    fun fillData(item: Item) {
        Glide.with(contentDetailOfferBinding.imageViewAvatar.context)
            .load(item.url)
            .placeholder(com.youness.hotelmanagementapp.R.drawable.logo)
            .into(contentDetailOfferBinding.imageViewAvatar)

        contentDetailOfferBinding.tvPropertyType.text = item.propertyType
        contentDetailOfferBinding.tvNbRooms.text = item.rooms.toString()
        contentDetailOfferBinding.tvCity.text = item.city
        contentDetailOfferBinding.tvArea.text = item.area.toString()
        contentDetailOfferBinding.tvPrice.text = item.price.toString()
        contentDetailOfferBinding.tvProfessional.text = item.professional
    }
}