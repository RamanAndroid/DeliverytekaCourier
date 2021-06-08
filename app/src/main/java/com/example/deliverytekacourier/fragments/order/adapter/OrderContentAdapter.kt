package com.example.deliverytekacourier.fragments.order.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.deliverytekacourier.R
import com.example.deliverytekacourier.data.models.MedicineItem
import com.example.deliverytekacourier.databinding.ItemMedicineBinding

class OrderContentAdapter :
    RecyclerView.Adapter<OrderContentAdapter.CheckOrderContenViewHolder>() {

    var dataList = emptyList<MedicineItem>()


    inner class CheckOrderContenViewHolder(private val binding: ItemMedicineBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(medicine: MedicineItem) {
            binding.apply {
                Glide.with(itemView).load(medicine.attributionUrl).centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(
                        R.drawable.ic_error
                    )
                    .into(productCartImage)

                productCartTitle.text = medicine.medicine_name

                val textPrice =
                    StringBuilder("${medicine.medicine_price} бел.руб.")
                productPrice.text = textPrice

                val textCount =
                    StringBuilder("${medicine.count}шт.")
                productCount.text = textCount
            }


        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckOrderContenViewHolder {

        val binding =
            ItemMedicineBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CheckOrderContenViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CheckOrderContenViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(categoriesData: List<MedicineItem>) {
        val toDoDiffUtil = OrderContentDiffUtil(dataList, categoriesData)
        val toDiffUtilResult = DiffUtil.calculateDiff(toDoDiffUtil)
        this.dataList = categoriesData
        toDiffUtilResult.dispatchUpdatesTo(this)
    }

}