package com.example.trafficlight.base

interface ItemType {

    fun getItemId(): String

    fun getItemType(): Int

    fun compareItem(old: ItemType): Boolean
}