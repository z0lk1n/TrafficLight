package com.example.trafficlight.base

import androidx.recyclerview.widget.DiffUtil

class BaseDiffCallback(
    private val oldList: List<ItemType>,
    private val newList: List<ItemType>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newList[newItemPosition].getItemId() == oldList[oldItemPosition].getItemId()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newList[newItemPosition].compareItem(oldList[oldItemPosition])
}