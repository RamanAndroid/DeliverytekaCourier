package com.example.deliverytekacourier.data.models

data class Orders(
    val is_active: Boolean,
    val result: List<OrdersItem>
)