package com.youness.hotelmanagementapp.network

class ApiHelper(private val apiService: ApiService) {
    suspend fun getHotelList() = apiService.getHotelList()

    suspend fun getHotelById(id: Int) = apiService.getHotelById(id)
}