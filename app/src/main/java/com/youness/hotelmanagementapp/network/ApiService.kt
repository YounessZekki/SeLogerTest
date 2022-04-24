package com.youness.hotelmanagementapp.network

import com.youness.hotelmanagementapp.model.HotelList
import com.youness.hotelmanagementapp.model.Item
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("listings.json")
    suspend fun getHotelList(): HotelList

    @GET("listings/{id}.json")
    suspend fun getHotelById(@Path("id") id: Int): Item
}