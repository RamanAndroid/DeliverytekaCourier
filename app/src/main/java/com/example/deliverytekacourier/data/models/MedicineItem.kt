package com.example.deliverytekacourier.data.models

import com.example.deliverytekacourier.utility.Constants

data class MedicineItem(
    val count: String,
    val medicine_country: String,
    val medicine_description: String,
    val medicine_dosage: String,
    val medicine_form: String,
    val medicine_id: String,
    val medicine_img: String,
    val medicine_name: String,
    val medicine_pack: String,
    val medicine_price: String,
    val sum: String
){
    val attributionUrl get() = Constants.BASE_URL + medicine_img
}