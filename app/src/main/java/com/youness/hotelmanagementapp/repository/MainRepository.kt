package com.youness.hotelmanagementapp.repository

import com.youness.hotelmanagementapp.network.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getHotelList() = apiHelper.getHotelList()

    suspend fun getHotelById(id: Int) = apiHelper.getHotelById(id)
}