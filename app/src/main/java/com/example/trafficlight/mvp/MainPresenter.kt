package com.example.trafficlight.mvp

import com.example.trafficlight.base.ItemType

interface MainPresenter {

    fun onCreateView()

    fun onDestroyView()

    fun onItemClicked(item: ItemType)
}