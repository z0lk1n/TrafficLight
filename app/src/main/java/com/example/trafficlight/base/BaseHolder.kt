package com.example.trafficlight.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseHolder(view: View) : RecyclerView.ViewHolder(view) {

    lateinit var item: ItemType

    open fun bind(item: ItemType) {
        this.item = item
    }

    fun setOnItemClickListener(listener: ItemClickListener) {
        itemView.setOnClickListener { listener.onItemClicked(item) }
    }
}