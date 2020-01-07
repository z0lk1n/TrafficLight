package com.example.trafficlight.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.trafficlight.R
import com.example.trafficlight.base.BaseAdapter
import com.example.trafficlight.base.BaseDiffCallback
import com.example.trafficlight.base.ItemClickListener
import com.example.trafficlight.base.ItemType
import com.example.trafficlight.model.Light

class LightsRvAdapter(listener: ItemClickListener) : BaseAdapter() {

    init {
        itemClickListener = listener
        delegateAdapters.put(Light.LIGHT_TYPE, LightDelegateAdapter(R.layout.item_rv_light))
    }

    override fun itemRange(newList: List<ItemType>) {
        val diffResult = DiffUtil.calculateDiff(BaseDiffCallback(itemList, newList))
        itemList.clear()
        itemList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
//        notifyDataSetChanged()
    }
}