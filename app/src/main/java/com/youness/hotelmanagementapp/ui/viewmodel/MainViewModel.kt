package com.youness.hotelmanagementapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.youness.hotelmanagementapp.repository.MainRepository
import androidx.lifecycle.liveData
import com.youness.hotelmanagementapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainViewModel(private val mainRepository: MainRepository): ViewModel() {

    fun getHotelList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getHotelList()))
        } catch (exception: Exception){
            emit(Resource.error(null, message = exception.message ?: "Erreur au moment de chargement de données!"))
        }
    }

    fun getHotelById(id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getHotelById(id)))
        } catch (exception: Exception) {
            emit(Resource.error(null, message = exception.message ?: "Erreur au moment de chargement de données!"))
        }
    }
}