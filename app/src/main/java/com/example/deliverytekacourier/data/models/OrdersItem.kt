package com.example.deliverytekacourier.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrdersItem(
    val order_datetime: String,
    val order_id: String,
    val order_status_id: String,
    val order_total: String,
    val pay_method: String,
    val user_address: String,
    val user_comment: String,
    val user_name: String,
    val user_phone: String
):Parcelable