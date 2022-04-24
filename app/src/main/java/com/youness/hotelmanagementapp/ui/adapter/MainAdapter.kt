package com.youness.hotelmanagementapp.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youness.hotelmanagementapp.R
import com.youness.hotelmanagementapp.databinding.HotelItemBinding
import com.youness.hotelmanagementapp.model.Item
import com.youness.hotelmanagementapp.ui.activity.DetailOfferActivity
import com.youness.hotelmanagementapp.utils.AppConstants

class MainAdapter(private var hotelList: List<Item>) :
    RecyclerView.Adapter<MainAdapter.DataViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemBinding = HotelItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DataViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val itemBean: Item = hotelList[position]
        holder.bind(itemBean)
    }

    override fun getItemCount(): Int = hotelList.size

    class DataViewHolder(private val itemBinding: HotelItemBinding, context: Context) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private var context: Context = context
        fun bind(item: Item) {
            itemBinding.propertyType.text = item.propertyType
            itemBinding.city.text = item.city
            itemBinding.professional.text = item.professional
            (item.price.toString() + " Euros").also { itemBinding.price.text = it }
            Glide.with(itemBinding.imageViewAvatar.context)
                .load(item.url)
                .placeholder(R.drawable.logo)
                .into(itemBinding.imageViewAvatar)
            itemBinding.root.setOnClickListener {
                val intent = Intent(context, DetailOfferActivity::class.java)
                intent.putExtra(AppConstants.EXTRA_ID_OBJECT, item.id)
                context.startActivity(intent)
//                (context as Activity).finish()
            }
        }
    }

    fun setHotelList(hotelList: List<Item>) {
        this.hotelList = hotelList
        notifyDataSetChanged()
    }
}