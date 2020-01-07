package com.example.trafficlight.mvp

import com.example.trafficlight.base.ItemType

interface MainView {

    fun init()

    fun setLightsData(list: List<ItemType>)

    fun showItemInfo(info: String)
}