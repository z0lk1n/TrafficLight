package com.example.trafficlight.base

import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter : RecyclerView.Adapter<BaseHolder>() {

    protected val delegateAdapters: SparseArray<DelegateAdapter> = SparseArray()
    var itemClickListener: ItemClickListener? = null
    var itemList: ArrayList<ItemType> = ArrayList()

    abstract fun itemRange(newList: List<ItemType>)

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int = itemList[position].getItemType()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val holder = delegateAdapters.get(viewType).onCreateViewHolder(parent)

        itemClickListener?.let { holder.setOnItemClickListener(it) }

        return holder
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) =
        holder.bind(itemList[position])
}