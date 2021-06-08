package com.example.deliverytekacourier.fragments.order.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.deliverytekacourier.data.models.MedicineItem

class OrderContentDiffUtil(
    private val oldList: List<MedicineItem>,
    private val newList: List<MedicineItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].medicine_id == newList[newItemPosition].medicine_id
                && oldList[oldItemPosition].medicine_name == newList[newItemPosition].medicine_name
    }
}