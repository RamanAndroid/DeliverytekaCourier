package com.example.deliverytekacourier.data.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.deliverytekacourier.data.models.*
import com.example.deliverytekacourier.data.repository.DeliverytekaCourierRepository
import kotlinx.coroutines.launch


class DeliverytekaCourierViewModel @ViewModelInject constructor(
    private val repository: DeliverytekaCourierRepository,
    application: Application,
) : AndroidViewModel(application) {


    var login: MutableLiveData<CourierShortInfo> = MutableLiveData()
    var shift: MutableLiveData<Shift> = MutableLiveData()
    var getOrders: MutableLiveData<Orders> = MutableLiveData()
    var getOrderContent: MutableLiveData<Medicine> = MutableLiveData()
    var getCourier: MutableLiveData<Courier> = MutableLiveData()

    fun login(requestAccess: RequestAccess) {
        viewModelScope.launch {
            val response = repository.login(requestAccess)
            login.value = response
        }
    }

    fun startShift(courierId: Int, hours: Int) {
        viewModelScope.launch {
            val response = repository.startShift(courierId, hours)
            shift.value = response
        }
    }

    fun checkOnline(courierId: Int) {
        viewModelScope.launch {
            val response = repository.checkOnline(courierId)
            shift.value = response
        }
    }

    fun getOrders(courierId: Int) {
        viewModelScope.launch {
            val response = repository.getOrders(courierId)
            getOrders.value = response
        }
    }

    fun getOrderContent(orderId: String) {
        viewModelScope.launch {
            val response = repository.getOrderContent(orderId)
            getOrderContent.value = response
        }
    }

    fun acceptOrder(courierId: Int, orderId: String) {
        viewModelScope.launch {
            repository.acceptOrder(courierId, orderId)
        }
    }

    fun getCourier(courierId: Int) {
        viewModelScope.launch {
            val response = repository.getCourier(courierId)
            getCourier.value = response
        }
    }


}