package com.example.deliverytekacourier.data.repository


import com.example.deliverytekacourier.api.DeliverytekaCourierApi
import com.example.deliverytekacourier.data.models.*

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeliverytekaCourierRepository @Inject constructor(private val deliverytekaApi: DeliverytekaCourierApi) {

    suspend fun login(requestAccess: RequestAccess): CourierShortInfo {
        return deliverytekaApi.login(requestAccess)
    }

    suspend fun startShift(courierId: Int, hours: Int): Shift {
        return deliverytekaApi.startShift(courierId, hours)
    }

    suspend fun checkOnline(courierId: Int): Shift {
        return deliverytekaApi.checkOnline(courierId)
    }

    suspend fun getOrders(courierId: Int): Orders {
        return deliverytekaApi.getOrders(courierId)
    }

    suspend fun getOrderContent(orderId: String): Medicine {
        return deliverytekaApi.getOrderContent(orderId)
    }

    suspend fun acceptOrder(courierId: Int,orderId: String) {
        return deliverytekaApi.acceptOrder(courierId,orderId)
    }

    suspend fun getCourier(courierId: Int): Courier {
        return deliverytekaApi.getCourier(courierId)
    }
}